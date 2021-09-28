<%@page import="by.itacademy.newsportal.bean.News"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>NewsPage</title>

<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="resources.localization.local" var="loc" />
<fmt:message bundle="${loc}" key="local.locbutton.name.ru" var="ru_button" />
<fmt:message bundle="${loc}" key="local.locbutton.name.en" var="en_button" />
<fmt:message bundle="${loc}" key="local.backToMain" var="back_to_main"/>
<fmt:message bundle="${loc}" key="local.news.publish" var="publish_news"/>
<fmt:message bundle="${loc}" key="local.news.redactor" var="news_redactor"/>
<fmt:message bundle="${loc}" key="local.news.newInformation" var="new_information"/>
<fmt:message bundle="${loc}" key="local.news.title" var="news_title"/>
<fmt:message bundle="${loc}" key="local.news.brief" var="news_brief"/>
<fmt:message bundle="${loc}" key="local.news.content" var="news_content"/>
<fmt:message bundle="${loc}" key="local.delete" var="delete" />
<fmt:message bundle="${loc}" key="local.send" var="send" />

</head>
<body>
	<a href="http://localhost:8080/NewsPortal/Controller?command=go_to_main_page&page=1">${back_to_main}</a>
	
	<div class="buttons">
		<div align="right">
			<form action="Controller" method="post">
				<input type="hidden" name="command" value="set_locale" />				
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
	</div>
	
	
	<fieldset>
	<h1 align="center"><c:out value="${requestScope.news.title}"></c:out></h1>
	<h2 align="center"><c:out value="${requestScope.news.brief}"></c:out></h2>
	<h3 align="center"><c:out value="${requestScope.news.content}"></c:out></h3>
	<p><c:out value="News ID: ${requestScope.news.id }" /></p>
	<p><c:out value="Date of creation: ${requestScope.news.date }" /></p>	
	
	<form action="Controller" method="post">
		<input type="hidden" name="command" value="add_news" />
		<input type="hidden" name="role" value="USER_ADMIN" />				
		<input type="hidden" name="title" value="${requestScope.news.title}" />				
		<input type="hidden" name="brief" value="${requestScope.news.brief}" />				
		<input type="hidden" name="content" value="${requestScope.news.content}" />				
		<input type="hidden" name="userId" value="${requestScope.news.userId}" />				
		<input type="hidden" name="newsId" value="${requestScope.news.id}" />				
		<input type="submit" value="${publish_news}">		
	</form>	
	
	<br>
	
	<form action="Controller" method="post">
		<input type="hidden" name="command" value="delete_news" />
		<input type="hidden" name="news_type" value="offered" />					
		<input type="hidden" name="newsId" value="${requestScope.news.id}" />					
		<input type="submit" value="${delete}">		
	</form>	
	</fieldset>
	
	
	<form action="Controller" method="post">
		<input type="hidden" name="command" value="add_news" />
		<input type="hidden" name="role" value="USER_ADMIN" />
		<input type="hidden" name="newsId" value="${requestScope.news.id}" />
		<input type="hidden" name="userId" value="${requestScope.news.userId}" />
	
		<fieldset>
			<legend>${news_redactor}</legend>
			<p>${new_information}</p>
			<label><input type="text" name="title" value="${news_title}" required></label><br>
			<textarea name="brief" cols="100" rows="2">${news_brief}</textarea><br>
			<textarea name="content" cols="100" rows="10">${news_content}</textarea><br>
			<br> <input type="submit" value="${send}">
		</fieldset>
	</form>	
		
</body>
</html>