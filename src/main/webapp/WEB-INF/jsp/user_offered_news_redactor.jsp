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
<fmt:message bundle="${loc}" key="local.locbutton.name.ru"
	var="ru_button" />
<fmt:message bundle="${loc}" key="local.locbutton.name.en"
	var="en_button" />

</head>
<body>
	<a href="http://localhost:8080/NewsPortal/Controller?command=go_to_main_page&page=1">Back to Main page</a>
	
	<div class="buttons">
		<div align="right">
			<form action="Controller" method="post">
				<input type="hidden" name="command" value="set_locale" /> <input
					type="hidden" name="local" value="ru" /> <input type="hidden"
					name="page" value="/WEB-INF/jsp/admin_news_page.jsp" /> <input type="submit"
					value="${ru_button}" />
			</form>
		</div>
		<div align="right">
			<form action="Controller" method="post">
				<input type="hidden" name="command" value="set_locale" /> <input
					type="hidden" name="local" value="en" /> <input type="hidden"
					name="page" value="/WEB-INF/jsp/admin_news_page.jsp" /> <input type="submit"
					value="${en_button}" />
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
		<input type="hidden" name="command" value="delete_news" />
		<input type="hidden" name="news_type" value="offered" />					
		<input type="hidden" name="newsId" value="${requestScope.news.id}" />					
		<input type="submit" value="DELETE">		
	</form>	
	</fieldset>
	
	
	<form action="Controller" method="post">
		<input type="hidden" name="command" value="update_news" />
		<input type="hidden" name="role" value="${sessionScope.user.role}" />
		<input type="hidden" name="newsId" value="${requestScope.news.id}" />
		<input type="hidden" name="userId" value="${requestScope.news.userId}" />
	
		<fieldset>
			<legend>News redactor</legend>
			<p>Enter new information:</p>
			<label><input type="text" name="title" value="Title" required></label><br>
			<textarea name="brief" cols="100" rows="2">Brief</textarea><br>
			<textarea name="content" cols="100" rows="10">Content</textarea><br>
			<br> <input type="submit" value="Send">
		</fieldset>
	</form>	
		
</body>
</html>