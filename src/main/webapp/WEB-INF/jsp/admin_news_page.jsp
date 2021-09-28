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
<fmt:message bundle="${loc}" key="local.backToMain" var="back_to_main" />
<fmt:message bundle="${loc}" key="local.send" var="send" />
<fmt:message bundle="${loc}" key="local.delete" var="delete" />
<fmt:message bundle="${loc}" key="local.news.redactor" var="news_redactor"/>
<fmt:message bundle="${loc}" key="local.news.newInformation" var="new_information"/>
<fmt:message bundle="${loc}" key="local.news.title" var="news_title"/>
<fmt:message bundle="${loc}" key="local.news.brief" var="news_brief"/>
<fmt:message bundle="${loc}" key="local.news.content" var="news_content"/>
<fmt:message bundle="${loc}" key="local.news.delete" var="news_delete"/>
<fmt:message bundle="${loc}" key="local.comment.enter" var="enter_comment"/>

</head>
<body>
	<a href="http://localhost:8080/NewsPortal/Controller?command=go_to_main_page&page=1">${back_to_main}</a>
	
	<div class="buttons">
		<div align="right">
			<form action="Controller" method="post">
				<input type="hidden" name="command" value="set_locale" />
				<input type="hidden" name="local" value="ru" />
				<input type="hidden" name="page" value="/WEB-INF/jsp/admin_news_page.jsp" />
				<input type="submit" value="${ru_button}" />
			</form>
		</div>
		<div align="right">
			<form action="Controller" method="post">
				<input type="hidden" name="command" value="set_locale" />
				<input type="hidden" name="local" value="en" />
				<input type="hidden" name="page" value="/WEB-INF/jsp/admin_news_page.jsp" />
				<input type="submit" value="${en_button}" />
			</form>
		</div>
	</div>
	
	<h1 align="center"><c:out value="${requestScope.news.title}"></c:out></h1>
	<h3 align="center"><c:out value="${requestScope.news.content}"></c:out></h3>
	<p><c:out value="News ID: ${requestScope.news.id}" /></p>
	<p><c:out value="Date of creation: ${requestScope.news.date}" /></p>
	
	<form action="Controller" method="post">
		<input type="hidden" name="command" value="update_news" />
		<input type="hidden" name="role" value="${sessionScope.user.role}" />

		<fieldset>
			<legend>${news_redactor}</legend>
			<p>${new_information}</p>
			<label><input type="text" name="title" value="${news_title}" required></label><br>
			<textarea name="brief" cols="50" rows="5">${news_brief}</textarea><br>
			<textarea name="content" cols="50" rows="20">${news_content}</textarea><br>
			<br> <input type="submit" value="${send}">
		</fieldset>
	</form>
	
	<form action="Controller" method="post">
		<input type="hidden" name="command" value="delete_news" />
		<input type="hidden" name="newsId" value="${requestScope.news.id}" />
		<input type="hidden" name="news_type" value="published" />
		<input type="hidden" name="role" value="ADMIN" />
		<fieldset>
			<legend>${news_delete}</legend>			
			<br> <input type="submit" value="${delete}">
		</fieldset>
	</form>
	
	<c:forEach var="comment" items="${requestScope.commentsList}">
		<div>
			<fieldset>
				<legend>${comment.userLogin}</legend>				
				<h4><c:out value="${comment.content}" /></h4>				
				<h4 align="right"><c:out value="${comment.date}" /></h4>
								
					<form action="Controller" method="post">
						<input type="hidden" name="command" value="DELETE_COMMENT" /> 
						<input type="hidden" name="commentId" value="${comment.id}" /> 
						<input type="hidden" name="userId" value="${comment.userId}" /> 
						<input type="hidden" name="newsId" value="${requestScope.news.id}" /> 
						<input type="submit" value="${delete}">
					</form>				
			</fieldset>
		</div>
	</c:forEach>	

	<form action="Controller" method="post">
		<input type="hidden" name="command" value="SEND_COMMENT" /> 
		<input type="hidden" name="userId" value="${sessionScope.user.id}" /> 
		<input type="hidden" name="newsId" value="${requestScope.news.id}" />

		<fieldset>
			<legend>${enter_comment}</legend>
			<textarea name="content" cols="100" rows="4"></textarea>
			<br> 
			<input type="submit" value="${send}">
		</fieldset>
	</form>
	
</body>
</html>