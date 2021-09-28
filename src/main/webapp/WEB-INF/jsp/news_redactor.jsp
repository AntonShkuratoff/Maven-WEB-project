<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>News Redactor</title>

<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="resources.localization.local" var="loc" />
<fmt:message bundle="${loc}" key="local.authorization.head" var="head" />
<fmt:message bundle="${loc}" key="local.locbutton.name.ru" var="ru_button" />
<fmt:message bundle="${loc}" key="local.locbutton.name.en" var="en_button" />
<fmt:message bundle="${loc}" key="local.login" var="login" />
<fmt:message bundle="${loc}" key="local.password" var="password" />
<fmt:message bundle="${loc}" key="local.registration.submit" var="submit" />
<fmt:message bundle="${loc}" key="local.send" var="send" />
<fmt:message bundle="${loc}" key="local.news.redactor" var="news_redactor"/>
<fmt:message bundle="${loc}" key="local.news.title" var="news_title"/>
<fmt:message bundle="${loc}" key="local.news.brief" var="news_brief"/>
<fmt:message bundle="${loc}" key="local.news.content" var="news_content"/>
<fmt:message bundle="${loc}" key="local.backToMain" var="back_to_main"/>

</head>
<body>
<a href="http://localhost:8080/NewsPortal/Controller?command=go_to_main_page&page=1">${back_to_main}</a>

	<div align="right">
		<form action="Controller" method="post">
			<input type="hidden" name="command" value="set_locale" /> 
			<input type="hidden" name="local" value="ru" />
			<input type="hidden" name="page" value="/WEB-INF/jsp/news_redactor.jsp" />
			<input type="submit" value="${ru_button}" />
		</form>
	</div>
	<div align="right">
		<form action="Controller" method="post">
			<input type="hidden" name="command" value="set_locale" /> 
			<input type="hidden" name="local" value="en" /> 
			<input type="hidden" name="page" value="/WEB-INF/jsp/news_redactor.jsp" /> 
			<input type="submit" value="${en_button}" />
		</form>
	</div>

	<h1 align="center">${head}</h1>
	<br />

	<form action="Controller" method="post">
		<input type="hidden" name="command" value="add_news" />
		<input type="hidden" name="role" value="${sessionScope.user.role}" />
		<input type="hidden" name="userId" value="${sessionScope.user.id}" />

		<fieldset>
			<legend>${news_redactor}</legend>
			<label><input type="text" name="title" value="${news_title}" required></label><br>
			<textarea name="brief" cols="50" rows="5">${news_brief}</textarea><br>
			<textarea name="content" cols="50" rows="20">${news_content}</textarea><br>
			<br> <input type="submit" value="${send}">
		</fieldset>
	</form>
</body>
</html>