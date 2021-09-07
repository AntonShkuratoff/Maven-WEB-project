<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Success!</title>

<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="resources.localization.local" var="loc" />
<fmt:message bundle="${loc}" key="local.success.message" var="message" />
<fmt:message bundle="${loc}" key="local.homepage" var="homepage" />

</head>
<body>

<div align="right">
		<form action="Controller" method="post">
			<input type="hidden" name="command" value="set_locale" /> 
			<input type="hidden" name="local" value="ru" />
			<input type="hidden" name="page" value="/WEB-INF/jsp/success.jsp" />
			<input type="submit" value="${ru_button}" />
		</form>
	</div>
	<div align="right">
		<form action="Controller" method="post">
			<input type="hidden" name="command" value="set_locale" />
			<input type="hidden" name="local" value="en" />
			<input type="hidden" name="page" value="/WEB-INF/jsp/success.jsp" /> 
			<input type="submit" value="${en_button}" />
		</form>
	</div>

	<h1 align="center">${message} <a href="http://localhost:8080/NewsPortal">${homepage}.</a></h1>
</body>
</html>