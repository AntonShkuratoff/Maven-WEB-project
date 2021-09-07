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
					name="page" value="/WEB-INF/jsp/news_page.jsp" /> <input
					type="submit" value="${ru_button}" />
			</form>
		</div>
		<div align="right">
			<form action="Controller" method="post">
				<input type="hidden" name="command" value="set_locale" /> <input
					type="hidden" name="local" value="en" /> <input type="hidden"
					name="page" value="/WEB-INF/jsp/news_page.jsp" /> <input
					type="submit" value="${en_button}" />
			</form>
		</div>
	</div>
	
	<div>
		<fieldset>
			<h1 align="center">
				<c:out value="${requestScope.news.title}"></c:out>
			</h1>
			<h3 align="center">
				<c:out value="${requestScope.news.content}"></c:out>
			</h3>
			
			<br>
			<c:if test="${!requestScope.flag}">
			<form action="Controller" method="post">
						<input type="hidden" name="command" value="PUT_NEWS_TO_FAVORITE" />						 
						<input type="hidden" name="userId" value="${sessionScope.user.id}" /> 
						<input type="hidden" name="newsId" value="${requestScope.news.id}" /> 
						<input type="submit" value="Add to favorite">
			</form>
			</c:if>
			
			<c:if test="${requestScope.flag}">
			<form action="Controller" method="post">
						<input type="hidden" name="command" value="DELETE_FROM_FAVORITE" />						 
						<input type="hidden" name="userId" value="${sessionScope.user.id}" /> 
						<input type="hidden" name="newsId" value="${requestScope.news.id}" /> 
						<input type="submit" value="Remove from favorite">
			</form>
			</c:if>
				
		</fieldset>	
	</div>
	<br>

	<c:forEach var="comment" items="${requestScope.commentsList}">
		<div>
			<fieldset>
				<legend>${comment.userLogin}</legend>				
				<h4><c:out value="${comment.content}" /></h4>				
				<h4 align="right"><c:out value="${comment.date}" /></h4>
				<c:if test="${sessionScope.user.id == comment.userId}">				
					<form action="Controller" method="post">
						<input type="hidden" name="command" value="DELETE_COMMENT" /> 
						<input type="hidden" name="commentId" value="${comment.id}" /> 
						<input type="hidden" name="userId" value="${comment.userId}" /> 
						<input type="hidden" name="newsId" value="${requestScope.news.id}" /> 
						<input type="submit" value="Delete">
					</form>				
				</c:if>
			</fieldset>
		</div>
	</c:forEach>	

	<form action="Controller" method="post">
		<input type="hidden" name="command" value="SEND_COMMENT" /> 
		<input type="hidden" name="userId" value="${sessionScope.user.id}" /> 
		<input type="hidden" name="newsId" value="${requestScope.news.id}" />

		<fieldset>
			<legend>Enter your comment:</legend>
			<textarea name="content" cols="100" rows="4"></textarea>
			<br> 
			<input type="submit" value="Send">
		</fieldset>
	</form>
	
</body>
</html>