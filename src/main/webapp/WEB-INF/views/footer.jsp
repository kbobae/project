<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

</head>
<body>
  <footer class="container-fluid">
        <div class="row align-items-center">
            <!-- 로고 -->
            <div class="col-lg-4">
                <a class="navbar-brand imgbox" href="<c:url value='/'/>">
                    <img id="logo" src="img/goldenTimeLogo_2.png" alt="GoldenTime" style="max-width: 100%;">
                </a>
            </div>
    
            <!-- 정보 -->
            <div class="col-lg-6">
                <div>
                    <h4 class="font-weight-bold">Information</h4>
                    <ul class="list-unstyled">
                        <li class="bi bi-signpost-split-fill">
                            <span class="text-secondary"> 주소 : 서울 마포구 신촌로 176(대흥동 12-20)</span>
                        </li>
                        <li class="bi bi-telephone-fill">
                            <span class="text-secondary"> 대표전화 : 02-718-8513</span>
                        </li>
                        <li class="bi bi-envelope-fill">
                            <span class="text-secondary"> 이메일 : GoldenTime@gmail.com</span>
                        </li>
                        <li class="bi bi-calendar-week">
                            <span class="text-secondary"> 근무시간 : 월 ~ 금 / AM 9:00 ~ PM 6:00</span>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </footer>
</body>
</html>