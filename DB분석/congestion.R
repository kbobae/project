#응급실 혼잡률 파일 불러오기(전지역)
library(readxl)
congestion <- read_excel("C:/Users/admin/Desktop/DB/응급실 혼잡률.xlsx")

#행렬전환
congestion <- t(congestion)

#1행을 열 이름으로 변경
colnames(congestion) <- congestion[1,] 

#1행 제거
congestion <- congestion[-1,] 

#데이터프레임으로 변경
congestion <- as.data.frame(congestion)

#열 이름 확인
names(congestion)

#열 이름 변경
colnames(congestion)[1:2] <- c("year", "month")

#필요없는 행 제거
congestion <- subset(congestion, !grepl('계', month))

#필요없는 열 제거
congestion <- congestion[, -3]
str(congestion)

#'월' 제거 후 수치형으로 변경
congestion$month <- as.numeric(gsub("\\D", "", congestion$month))

#year열 수치형으로 변경
congestion$year <- as.numeric(congestion$year)

#수치형으로 변경 후 데이터프레임으로 변경
congestion <- lapply(congestion, function(x) as.numeric(as.character(x)))
congestion <- as.data.frame(congestion)
str(congestion)

#파일 내보내기
#library(openxlsx)
#write.xlsx(congestion, "congestion.xlsx")

#-------------------------------------------------------------------

