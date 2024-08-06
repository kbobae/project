setwd("C:\\Users\\bobae\\Desktop\\project\\DB")
data <- read.csv("시도별_교통사고.csv", fileEncoding = "UTF-8")

#필요없는 행, 열 제거
data <- data[, -(5:6)]
data <- subset(data, !grepl('전체', 월별.1.))

#열 이름 변경
colnames(data)[1:4] <- c("지역", "년도", "월", "교통사고건수")

str(data)

#'월' -> 수치형으로 변경
data$월 <- as.numeric(gsub("\\D", "", data$월))
data$년도 <- as.numeric(data$년도)

#전국 데이터 생성
nationwide <- data %>%
  group_by(년도, 월) %>%
  summarize(교통사고건수 = sum(교통사고건수)) %>%
  mutate(지역 = "전국")

#기존 데이터와 전국 데이터를 결합
result_ta <- bind_rows(data, nationwide)


# 데이터 프레임을 엑셀 파일로 저장
library(writexl)
write_xlsx(result_ta, "accidents.xlsx")
