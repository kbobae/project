setwd("C:\\Users\\bobae\\Desktop\\project\\DB")
data <- read.csv("응급실_월별_이용현황_시도별.csv", fileEncoding = "EUC-KR")


#필요없는 행, 열 제거
names(data)
#grepl() : 특정 텍스트 검색
data <- subset(data, !grepl('계', 월별.1.))
#data <- data[, -3]

#열 이름 변경
colnames(data)[1:3] <- c("년도", "월", "전국")

#세종 -> 수치형으로 변경
str(data)
data$세종 <- as.numeric(data$세종)
data$세종[is.na(data$세종)] <- 0

#'월' -> 수치형으로 변경
data$월 <- as.numeric(gsub("\\D", "", data$월))
data$년도 <- as.numeric(data$년도)

# 데이터를 긴 형태로 변환
library(tidyverse)

long_data <- data %>%
  pivot_longer(
    cols = -c(년도, 월), #'년도'와 '월'을 제외한 나머지 열 변환
    names_to = "지역",   #변환될 열 이름을 저장할 열 이름
    values_to = "응급실 이용환자수"  #변환될 값들을 저장할 열 이름
  )

#지역>년도>월 기준으로 재정렬
result_er <- long_data[order(long_data$지역, long_data$년도, long_data$월), ]


# 데이터 프레임을 엑셀 파일로 저장
install.packages("writexl")
library(writexl)
write_xlsx(result_er, "emergency.xlsx")
