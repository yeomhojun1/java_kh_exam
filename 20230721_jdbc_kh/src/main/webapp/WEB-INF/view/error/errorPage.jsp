
<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
<title>Error</title>
</head>
<body>
Error Info : <%= exception.getClass().getName() %>
<hr>
오류페이지
<a href="${pageContext.request.contextPath }/main">main 이동</a>
</body>
</html>