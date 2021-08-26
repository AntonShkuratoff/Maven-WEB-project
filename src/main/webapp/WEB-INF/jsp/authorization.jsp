<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Authorization form</title>

<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="resources.localization.local" var="loc" />
<fmt:message bundle="${loc}" key="local.authorization.head" var="head" />
<fmt:message bundle="${loc}" key="local.locbutton.name.ru" var="ru_button" />
<fmt:message bundle="${loc}" key="local.locbutton.name.en" var="en_button" />
<fmt:message bundle="${loc}" key="local.login" var="login" />
<fmt:message bundle="${loc}" key="local.password" var="password" />
<fmt:message bundle="${loc}" key="local.registration.submit" var="submit" />

</head>
<body>
<a href="http://localhost:8080/NewsPortal/Controller?command=go_to_main_page&page=1">Back to Main page</a>

	<div align="right">
		<form action="Controller" method="post">
			<input type="hidden" name="command" value="set_locale" /> 
			<input type="hidden" name="local" value="ru" />
			<input type="submit" value="${ru_button}" />
		</form>
	</div>
	<div align="right">
		<form action="Controller" method="post">
			<input type="hidden" name="command" value="set_locale" />
			<input type="hidden" name="local" value="en" />
			<input type="submit" value="${en_button}" />
		</form>
	</div>

	<h1 align="center">${head}</h1>
	<br />
	
	<form action="Controller" method="post">
		<input type="hidden" name="command" value="get_user" /> 
		<label><input type="text" name="login" required pattern="[a-zA-Z0-9]{2,20}">${login}</label><br>
		<label><input type="password" name="password" required pattern="[a-zA-Z0-9]{5,20}" autocomplete="off">${password}</label><br>
		<input type="submit" value="${submit}" /><br />
	</form>
</body>
</html>