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
<fmt:message bundle="${loc}" key="local.news.addToFavorite" var="add_to_favorite"/>
<fmt:message bundle="${loc}" key="local.news.removeFromFavorite" var="remove_from_favorite"/>
<fmt:message bundle="${loc}" key="local.delete" var="delete" />
<fmt:message bundle="${loc}" key="local.send" var="send" />
<fmt:message bundle="${loc}" key="local.comment.enter" var="enter_comment"/>

</head>
<body>
	<a href="http://localhost:8080/NewsPortal/Controller?command=go_to_main_page&page=1">${back_to_main}</a>

	<div class="buttons">
		<div align="right">
			<form action="Controller" method="post">
				<input type="hidden" name="command" value="set_locale" />
				<input type="hidden" name="local" value="ru" />
				<input type="hidden" name="page" value="/WEB-INF/jsp/news_page.jsp" />
				<input type="submit" value="${ru_button}" />
			</form>
		</div>
		<div align="right">
			<form action="Controller" method="post">
				<input type="hidden" name="command" value="set_locale" />
				<input type="hidden" name="local" value="en" />
				<input type="hidden" name="page" value="/WEB-INF/jsp/news_page.jsp" />
				<input type="submit" value="${en_button}" />
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
						<input type="submit" value="${add_to_favorite}">
			</form>
			</c:if>
			
			<c:if test="${requestScope.flag}">
			<form action="Controller" method="post">
						<input type="hidden" name="command" value="DELETE_FROM_FAVORITE" />						 
						<input type="hidden" name="userId" value="${sessionScope.user.id}" /> 
						<input type="hidden" name="newsId" value="${requestScope.news.id}" /> 
						<input type="submit" value="${remove_from_favorite}">
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
						<input type="submit" value="${delete}">
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
			<legend>${enter_comment}</legend>
			<textarea name="content" cols="100" rows="4"></textarea>
			<br> 
			<input type="submit" value="${send}">
		</fieldset>
	</form>
	
</body>
</html>