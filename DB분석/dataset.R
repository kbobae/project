#제주 데이터셋
library(dplyr) #filter(), select() 사용하기 위해 필요한 패키지
#응급실 혼잡도 데이터셋(congestion) - year, month, 제주 열만 추출
jeju_con <- subset(congestion, select = c(year, month, 제주))
#열 이름 변경(제주 -> 응급실혼잡도)
jeju_con <- jeju_con %>% rename("응급실혼잡도" = 제주)
#이송환자수 데이터셋(transferred) - 제주 행만 추출
jeju_TP <- transferred %>% filter(지역 %in% "제주")
#응급실이용현황 데이터셋(er) - year, month, 제주 열만 추출
jeju_ER <- subset(er, select = c(year, month, 제주))
#열 이름 변경(제주 -> 응급실이용환자수)
jeju_ER <- jeju_ER %>% rename("응급실이용환자수" = 제주)
#응급실혼잡도, 이송환자수 데이터 병합
jeju_data <- left_join(jeju_con, jeju_TP, by = c("year", "month"))
#응급실이용현황 데이터 병합
jeju_data <- left_join(jeju_data, jeju_ER, by = c("year", "month"))
#지역 열 제거
jeju_data <- subset(jeju_data, select = -c(지역))
#교통사고건수 - 제주 추출
jeju_TA <- subset(ta, select = c(year, month, 제주))
jeju_TA <- jeju_TA %>% rename("교통사고건수" = 제주)
#데이터 병합
jeju_data <- left_join(jeju_data, jeju_TA, by = c("year", "month"))



model_jeju <- lm(응급실혼잡도 ~ ., data = jeju_data)
summary(model_jeju)
library(MASS)
model_jeju2 <- stepAIC(model_jeju, direction = "both")
summary(model_jeju2)

#--------------------------------------------------------------
#경남
gyeongnam_con <- subset(congestion, select = c(year, month, 경남))
gyeongnam_con <- gyeongnam_con %>% rename("응급실혼잡도" = 경남)
gyeongnam_ER <- subset(er, select = c(year, month, 경남))
gyeongnam_ER <- gyeongnam_ER %>% rename("응급실이용환자수" = 경남)
gyeongnam_TP <- transferred %>% filter(지역 %in% "경남")
gyeongnam_data <- left_join(gyeongnam_con, gyeongnam_TP, by = c("year", "month"))
gyeongnam_data <- left_join(gyeongnam_data, gyeongnam_ER, by = c("year", "month"))
gyeongnam_data <- subset(gyeongnam_data, select = -c(지역))

gyeongnam_TA <- subset(ta, select = c(year, month, 경남))
gyeongnam_TA <- gyeongnam_TA %>% rename("교통사고건수" = 경남)
gyeongnam_data <- left_join(gyeongnam_data, gyeongnam_TA, by = c("year", "month"))

cor(gyeongnam_data[, c("응급실혼잡도", "응급실이용환자수", "이동환자수", "교통사고건수")])
model_gyeongnam <- lm(응급실혼잡도 ~ ., data = gyeongnam_data)
summary(model_gyeongnam)
library(MASS)
model_gyeongnam2 <- stepAIC(model_gyeongnam, direction = "both")
summary(model_gyeongnam2)
#--------------------------------------------------------------
#경북
gyeongbuk_con <- subset(congestion, select = c(year, month, 경북))
gyeongbuk_con <- gyeongbuk_con %>% rename("응급실혼잡도" = 경북)
gyeongbuk_ER <- subset(er, select = c(year, month, 경북))
gyeongbuk_ER <- gyeongbuk_ER %>% rename("응급실이용환자수" = 경북)
gyeongbuk_TP <- transferred %>% filter(지역 %in% "경북")
gyeongbuk_data <- left_join(gyeongbuk_con, gyeongbuk_TP, by = c("year", "month"))
gyeongbuk_data <- left_join(gyeongbuk_data, gyeongbuk_ER, by = c("year", "month"))
gyeongbuk_data <- subset(gyeongbuk_data, select = -c(지역))

gyeongbuk_TA <- subset(ta, select = c(year, month, 경북))
gyeongbuk_TA <- gyeongbuk_TA %>% rename("교통사고건수" = 경북)
gyeongbuk_data <- left_join(gyeongbuk_data, gyeongbuk_TA, by = c("year", "month"))

cor(gyeongbuk_data[, c("응급실혼잡도", "응급실이용환자수", "이동환자수", "교통사고건수")])
model_gyeongbuk <- lm(응급실혼잡도 ~ ., data = gyeongbuk_data)
summary(model_gyeongbuk)
library(MASS)
model_gyeongbuk2 <- stepAIC(model_gyeongbuk, direction = "both")
summary(model_gyeongbuk2)
#--------------------------------------------------------------
#전남
jeonnam_con <- subset(congestion, select = c(year, month, 전남))
jeonnam_con <- jeonnam_con %>% rename("응급실혼잡도" = 전남)
jeonnam_ER <- subset(er, select = c(year, month, 전남))
jeonnam_ER <- jeonnam_ER %>% rename("응급실이용환자수" = 전남)
jeonnam_TP <- transferred %>% filter(지역 %in% "전남")
jeonnam_data <- left_join(jeonnam_con, jeonnam_TP, by = c("year", "month"))
jeonnam_data <- left_join(jeonnam_data, jeonnam_ER, by = c("year", "month"))
jeonnam_data <- subset(jeonnam_data, select = -c(지역))

jeonnam_TA <- subset(ta, select = c(year, month, 전남))
jeonnam_TA <- jeonnam_TA %>% rename("교통사고건수" = 전남)
jeonnam_data <- left_join(jeonnam_data, jeonnam_TA, by = c("year", "month"))

cor(jeonnam_data[, c("응급실혼잡도", "응급실이용환자수", "이동환자수", "교통사고건수")])
model_jeonnam <- lm(응급실혼잡도 ~ ., data = jeonnam_data)
summary(model_jeonnam)
library(MASS)
model_jeonnam2 <- stepAIC(model_jeonnam, direction = "both")
summary(model_jeonnam2)
#--------------------------------------------------------------
#전북
jeonbuk_con <- subset(congestion, select = c(year, month, 전북))
jeonbuk_con <- jeonbuk_con %>% rename("응급실혼잡도" = 전북)
jeonbuk_ER <- subset(er, select = c(year, month, 전북))
jeonbuk_ER <- jeonbuk_ER %>% rename("응급실이용환자수" = 전북)
jeonbuk_TP <- transferred %>% filter(지역 %in% "전북")
jeonbuk_data <- left_join(jeonbuk_con, jeonbuk_TP, by = c("year", "month"))
jeonbuk_data <- left_join(jeonbuk_data, jeonbuk_ER, by = c("year", "month"))
jeonbuk_data <- subset(jeonbuk_data, select = -c(지역))

jeonbuk_TA <- subset(ta, select = c(year, month, 전북))
jeonbuk_TA <- jeonbuk_TA %>% rename("교통사고건수" = 전북)
jeonbuk_data <- left_join(jeonbuk_data, jeonbuk_TA, by = c("year", "month"))

cor(jeonbuk_data[, c("응급실혼잡도", "응급실이용환자수", "이동환자수", "교통사고건수")])
model_jeonbuk <- lm(응급실혼잡도 ~ ., data = jeonbuk_data)
summary(model_jeonbuk)
library(MASS)
model_jeonbuk2 <- stepAIC(model_jeonbuk, direction = "both")
summary(model_jeonbuk2)
#--------------------------------------------------------------


#충남
chungnam_data <- read_excel("C:/Users/admin/Desktop/DB/dataset/Chungnam.xlsx")
cor(chungnam_data[, c("응급실혼잡도", "응급실이용환자수", "이동환자수", "교통사고건수")])
model_chungnam <- lm(응급실혼잡도 ~ ., data = chungnam_data)
summary(model_chungnam)
model_chungnam2 <- stepAIC(model_chungnam, direction = "both")
summary(model_chungnam2)

#충북
chungbuk_data <- read_excel("C:/Users/admin/Desktop/DB/dataset/Chungbuk.xlsx")
cor(chungbuk_data[, c("응급실혼잡도", "응급실이용환자수", "이동환자수", "교통사고건수")])
model_chungbuk <- lm(응급실혼잡도 ~ ., data = chungbuk_data)
summary(model_chungbuk)
model_chungbuk2 <- stepAIC(model_chungbuk, direction = "both")
summary(model_chungbuk2)

#강원
gangwon_data <- read_excel("C:/Users/admin/Desktop/DB/dataset/Gangwon.xlsx")
model_gangwon <- lm(응급실혼잡도 ~ ., data = gangwon_data)
summary(model_gangwon)
model_gangwon2 <- stepAIC(model_gangwon, direction = "both")
summary(model_gangwon2)
#경기
gyeonggi_data <- read_excel("C:/Users/admin/Desktop/DB/dataset/Gyeonggi.xlsx")
model_gyeonggi <- lm(응급실혼잡도 ~ ., data = gyeonggi_data)
summary(model_gyeonggi)
model_gyeonggi2 <- stepAIC(model_gyeonggi, direction = "both")
summary(model_gyeonggi2)
#세종
sejong_data <- read_excel("C:/Users/admin/Desktop/DB/dataset/Sejong.xlsx")
cor(sejong_data[, c("응급실혼잡도", "응급실이용환자수", "이동환자수", "교통사고건수")])
model_sejong <- lm(응급실혼잡도 ~ ., data = sejong_data)
summary(model_sejong)
model_sejong2 <- stepAIC(model_sejong, direction = "both")
summary(model_sejong2)

sejong_data2 <- sejong_data[19:nrow(sejong_data), ]
cor(sejong_data2[, c("응급실혼잡도", "응급실이용환자수", "이동환자수", "교통사고건수")])
model_sejong3 <- lm(응급실혼잡도 ~ ., data = sejong_data2)
summary(model_sejong3)
model_sejong4 <- stepAIC(model_sejong3, direction = "both")
summary(model_sejong4)

#울산
ulsan_data <- read_excel("C:/Users/admin/Desktop/DB/dataset/Ulsan.xlsx")
model_ulsan <- lm(응급실혼잡도 ~ ., data = ulsan_data)
summary(model_ulsan)
model_ulsan2 <- stepAIC(model_ulsan, direction = "both")
summary(model_ulsan2)
#대전
daejeon_data <- read_excel("C:/Users/admin/Desktop/DB/dataset/Daejeon.xlsx")
model_daejeon <- lm(응급실혼잡도 ~ ., data = daejeon_data)
summary(model_daejeon)
model_daejeon2 <- stepAIC(model_daejeon, direction = "both")
summary(model_daejeon2)

#광주
gwangju_data <- read_excel("C:/Users/admin/Desktop/DB/dataset/Gwangju.xlsx")
model_gwangju <- lm(응급실혼잡도 ~ ., data = gwangju_data)
summary(model_gwangju)
model_gwangju2 <- stepAIC(model_gwangju, direction = "both")
summary(model_gwangju2)
#인천
incheon_data <- read_excel("C:/Users/admin/Desktop/DB/dataset/Incheon.xlsx")
model_incheon <- lm(응급실혼잡도 ~ ., data = incheon_data)
summary(model_incheon)
model_incheon2 <- stepAIC(model_incheon, direction = "both")
summary(model_incheon2)
#대구
daegu_data <- read_excel("C:/Users/admin/Desktop/DB/dataset/degu.xlsx")
model_daegu <- lm(응급실혼잡도 ~ ., data = daegu_data)
summary(model_daegu)
model_daegu2 <- stepAIC(model_daegu, direction = "both")
summary(model_daegu2)
#부산
busan_data <- read_excel("C:/Users/admin/Desktop/DB/dataset/busan.xlsx")
model_busan <- lm(응급실혼잡도 ~., data = busan_data)
summary(model_busan)
model_busan2 <- stepAIC(model_busan, direction = "both")
summary(model_busan2)

busan_last <- busan_data[25:36,]
busan_last
busan_con <- c()

for(i in 1:12) {
  con <- (-6.388e+02)+(3.163e-01 * 2024)+(-9.561e-02 * busan_last$month[i])+(2.035e-03 *busan_last$이동환자수[i])+
    (9.909e-04 * busan_last$응급실이용환자수[i]) + (-7.732e-03 *busan_last$교통사고건수[i])
  
  busan_con <- c(busan_con, con)
}

busan_con

month <- c(1:12)

plot(month, busan_con, main="2022년 부산 응급실 혼잡도 예측", type="o", lty=1, lwd=2, col="red", 
     xlab="월", ylab="혼잡도")

#서울
seoul_data <- read_excel("C:/Users/admin/Desktop/DB/dataset/seoul.xlsx")
model_seoul <- lm(응급실혼잡도 ~., data = seoul_data)
summary(model_seoul)
model_seoul2 <- stepAIC(model_seoul, direction = "both")
summary(model_seoul2)
