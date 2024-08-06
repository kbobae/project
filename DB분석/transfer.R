#119 이송환자수

library(jsonlite) #fromJSON()
library(httr) #웹 API 호출 및 데이터 수집하는 데 필요
library(tidyverse) #-> dplyr, tidyr, stringr패키지 포함
#dplyr패키지 -> #filter(), select(), mutate(), arrange(), summarise()


#인증키 전까지
URL <- "https://apis.data.go.kr/1661000/EmergencyStatisticsService/get119EmgencyActivityStats"

#일반 인증키(Encoding)
key <- "v%2FKDyZCNDkxEAijiG89TrlZdFH0yVx9O5sl6YZ%2FdOKh8UfrgWbrdLQ04OA5Q7IyRyGBvhJcG9nlSWvOJr9JkXA%3D%3D"

#url에 한글 및 특수문자는 인코딩해서 적용
parameter <- "본부"
encoded_parameter <- URLencode(parameter)

#요청변수(numOfRows) : 월마다 데이터 길이가 다름 -> 3년치 데이터 길이 벡터에 저장
numOfRows <- c(238,234,234,233,232,235,234,237,235,238,238,238,
               237,237,238,237,238,239,236,238,238,240,239,239,
               239,241,241,241,238,241,243,239,242,242,243,242)

#빈 리스트 생성 - 3년치 url 저장할 때 필요
api_data_list <- list()

#데이터 리스트에 저장
for (year in 2019:2021) {
  for (month in 1:12) {
    #sprinf() : 특정한 규칙에 맞게 문자열로 변환
    yearMonth <- sprintf("%d%02d", year, month)
    numOfRow <- numOfRows[(year - 2019) * 12 + month]
    #paste0() : 나열된 원소 사이에 공백없이 출력
    url = paste0(URL,
                 "?serviceKey=", key,
                 "&pageNo=", 1,
                 "&numOfRows=", numOfRow,
                 "&resultType=json",
                 "&sidoHqOgidNm=", encoded_parameter,
                 "&rcptYm=", yearMonth)
    # UTF-8 인코딩을 사용하여 텍스트를 읽어옴
    url_data <- readLines(url, encoding = "UTF-8")
    #fromJSON() : JSON 포맷의 데이터를 R DataFrame 으로 변환
    api_data <- fromJSON(url_data)
    api_data_list[[length(api_data_list)+1]] <- api_data
  }  
}

#각 list 요소의 길이를 추출한 후 가장 큰 길이를 저장
max_length <- max(sapply(api_data_list, length))

#api_data_list에 있는 여러 데이터에 대해 df함수를 사용하여 결측값을 추가한 후,
#그 결과를 데이터프레임으로 변환하여 저장
df2 <- lapply(api_data_list, function(api_data){
  dt2_length <- max_length - length(api_data)
  c(api_data, rep(NA, dt2_length))
  })

combined_df <- bind_rows(df2)
#filter() : 조건에 맞는 데이터 추출
combined_df <- combined_df %>% filter(row_number() %% 2 != 0)

#unnest() -> 중첩된 데이터프레임 해체
#library(tidyr)
df3 <- combined_df %>% unnest(body)

names(df3)

#열 이름 변경
colnames(df3)[c(7,8,9)] <- c("이동환자수", "시기", "지역")

#필요한 열만 추출
df4 <- df3 %>% select("이동환자수", "시기", "지역")

#시기 열을 년도, 월 열로 분할
#mutate() : 열 추가/ substr() : 문자열 일부 추출
df5 <- df4 %>% mutate(year = substr(시기, 1, 4),
                      month = substr(시기, 5, 6)) %>%
  select(-시기)

#년도, 월, 지역별로 이동환자수 합계 구하기 
#aggregate() : 그룹별 연산
result <- aggregate(이동환자수 ~ year+month+지역, data = df5, FUN = sum, na.rm = TRUE)

#년도, 월, 지역 순서대로 정렬
result <- result[order(result$지역, result$year, result$month), ]

#지역 이름 변경
#str_replace() : 매칭되는 문자열 치환 -> stringr패키지 필요
result <- result %>% 
  mutate(지역2 = str_replace(지역, "소방재난본부|소방안전본부|소방본부", ""),
         지역3 = str_replace(지역2, "도$", ""),
         지역4 = str_replace(지역3, "충청남", "충남"))

#년도, 월, 지역, 이동환자수 열만 추출
result <- result %>% select(year, month, 지역4, 이동환자수)

#지역 열 이름 변경
names(result)[3] <- c("지역")                              

result <- result %>% mutate(year = as.numeric(year),
                            month = as.numeric(as.character(month)))


#경남과 창원 데이터만 추출
gyeongnam_changwon_data <- result %>%
  filter(지역 %in% c("경남", "창원"))

#경남, 창원 - 년도와 월로 그룹화하여 이송환자수 합계 구하기
gyeongnam_changwon_data <- gyeongnam_changwon_data %>%
  group_by(year, month) %>%
  summarize(총이동환자수 = sum(이동환자수), 지역 = "경남") %>%
  subset(select = c(year, month, 지역, 총이동환자수))
#열 이름 변경
names(gyeongnam_changwon_data)[4] <- c("이동환자수")

#경남, 창원 제외한 데이터셋과 병합
result2 <- result %>% filter(!(지역 %in% c("경남", "창원")))
transferred <- bind_rows(result2, gyeongnam_changwon_data)

#파일 내보내기
#library(openxlsx)
#write.xlsx(transferred, "transferred.xlsx")
