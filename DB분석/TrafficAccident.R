#교통사고건수 파일 읽어오기
setwd("C:/Users/admin/Desktop/DB")
ta <- read.csv("TrafficAccident.csv", fileEncoding = "EUC-KR")
View(ta)

#사고년도 열에서 부상[명], 사망[명]이 포함된 행 삭제
ta <- ta[!grepl("부상\\[명\\]|사망\\[명\\]", ta$사고년도), ]

#행렬전환
ta <- t(ta)

#1열을 열 이름으로 변경
colnames(ta) <- ta[1,]

#1, 2행 삭제
ta <- ta[-c(1:2),]

#데이터프레임으로 변경
ta <- as.data.frame(ta)

#시도 열에서 '합계'가 포함된 행 삭제
ta <- subset(ta, !grepl("합계", 시도))

#필요없는 열 삭제
names(ta)
ta <- ta %>% select(-`출처) 도로교통공단.`)

#years 열 생성하여 맨 왼쪽에 결합
years <- rep(2019:2021, each = 12)
ta <- cbind(year = years, ta)

#3번째 열 삭제
ta <- ta[, -3]
str(ta)

#시도 열에서 '월' 삭제한 후 수치형으로 변경
ta$month <- as.numeric(gsub("\\D", "", ta$시도))

#열 순서 변경
ta <- ta %>% select(year, month, everything())

#3번째 열 삭제
ta <- ta[, -3]

#쉼표 삭제 후 수치형으로 변경
ta <- ta %>% mutate_all(~as.numeric(gsub(",", "", .)))

#파일 내보내기
#library(openxlsx)
#write.xlsx(ta, "TrafficAccident.xlsx")
