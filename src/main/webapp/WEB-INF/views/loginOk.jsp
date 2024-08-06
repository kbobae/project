<%@ page import="java.util.*" %>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="com.mycompany.goldenTime.model.MemberVO"  %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<% String errorMessage = (String)request.getAttribute("errorMessage"); %>

<script language="javascript">
    console.log("ErrorMessage: <%= errorMessage %>");
</script>

<% if(errorMessage != null && !errorMessage.isEmpty()) { %>
		<script language="javascript">
			alert("<%= errorMessage%>");
			history.go(-1); 
		</script>
<%	} %>
</body>
</html>