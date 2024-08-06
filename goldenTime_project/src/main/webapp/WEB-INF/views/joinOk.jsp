<%@ page import="java.util.*" %>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="com.mycompany.goldenTime.model.MemberVO"  %>
<%@ page import="com.mycompany.goldenTime.controller.MemberController"  %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<% 
	System.out.println("joinOk.jsp 호출됨");
	String joinMessage = (String)request.getAttribute("joinMessage");
%>
<% 	
	if(joinMessage != null && !joinMessage.isEmpty()) { %>
	<script language="javascript">
		console.log("<%=joinMessage%>");
         alert("<%= joinMessage%>");
         <% if (joinMessage.equals("회원 가입이 성공적으로 완료되었습니다.")) { %>
         		window.location.href = "login";
         <% } else {%>
         		window.location.href = "joinOk";
         <% } %>
	</script>            
        <% } %>
        
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

</body>
</html>