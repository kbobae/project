#응급실 월별 이용환자 수

setwd("C:/Users/admin/Desktop/DB")
er <- read.csv("응급실_월별_이용현황_시도별.csv", fileEncoding = "EUC-KR")

#필요없는 행, 열 제거
names(er)
#grepl() : 특정 텍스트 검색 -> 월별.1. 열에서 '계'가 있는 행만 제거
er <- subset(er, !grepl('계', 월별.1.))
#3열 제거
er <- er[, -3]

#열 이름 변경
colnames(er)[1:2] <- c("year", "month")

#세종 -> 수치형으로 변경
er$세종 <- as.numeric(er$세종)
#NA값 0으로 변경
er$세종[is.na(er$세종)] <- 0

#'월' 글자 제거 후 수치형으로 변경
er$month <- as.numeric(gsub("\\D", "", er$month))
#년도 수치형으로 변경
er$year <- as.numeric(er$year)


