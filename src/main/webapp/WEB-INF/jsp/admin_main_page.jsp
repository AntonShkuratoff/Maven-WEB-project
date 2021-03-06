<%@page import="java.util.ArrayList"%>
<%@page import="by.itacademy.newsportal.bean.News"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Main page</title>
<style>
.flex-container-column {
	display: flex;
	flex-direction: column;
}

.flex-container-row {
	display: flex;
	flex-direction: row;
	justify-content: space-around;
}

.flex-container-row>.head {
	flex: 0 0 20em;
	background-color: grey;
}

.flex-container-row>.buttons {
	flex: 1 1 500px;
}

.art_ex {
	background-color: rgb(177, 174, 174);
	margin-top: 1em;
	margin-bottom: 1em;
	margin-left: 1em;
}

.head_1 {
	font-weight: bolder;
	font-size: 30px;
	margin-bottom: 0;
}
</style>
<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="resources.localization.local" var="loc" />
<fmt:message bundle="${loc}" key="local.title" var="title" />
<fmt:message bundle="${loc}" key="local.locbutton.name.ru" var="ru_button" />
<fmt:message bundle="${loc}" key="local.locbutton.name.en" var="en_button" />
<fmt:message bundle="${loc}" key="local.logout" var="log_out" />
<fmt:message bundle="${loc}" key="local.news.add" var="add_news" />
<fmt:message bundle="${loc}" key="local.news.offered" var="offered_news" />

</head>
<body>
	<div class="flex-container-column">
		
		<div class="buttons">
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
		</div>
		
		<div class="flex-container-row">
			<div class="head" align="center">
				<p class="head_1">
					<c:out value="${title}"></c:out>
				</p>
			</div>

			<div class="buttons">				
				
				<div class="art_ex" align="right">
					<form action="Controller" method="get">
						<input type="hidden" name="command" value="go_to_news_redactor">
						<input type="submit" value="${add_news}">
					</form>
				</div>
				
				<div class="art_ex" align="right">
					<form action="Controller" method="get">
						<input type="hidden" name="command" value="GO_TO_USERS_NEWS">
						<input type="submit" value="${offered_news}">
					</form>
				</div>

				<div class="art_ex" align="right">
					<form action="Controller" method="post">
						<input type="hidden" name="command" value="log_out">
						<input type="submit" value="${log_out}">
					</form>
				</div>
			</div>
		</div>
	</div>

	<c:forEach var="news" items="${requestScope.newsList}">
		<a href="http://localhost:8080/NewsPortal/Controller?command=go_to_news_page&news_ID=${news.id}&news_type=published">			
			<div>
				<fieldset>
					<legend>${news.title}</legend>				
					<h3 align='center'><c:out value="${news.brief}" /></h3>
					<h4 align="right"><c:out value="${news.date}" /></h4>
				</fieldset>
			</div>			
		</a>
	</c:forEach>
	
	<%
	int numberOfPages = (int) request.getAttribute("numberOfPages");
	out.print("<h4 align='center'>");
	for (int i = 1; i <= numberOfPages; i++) {		
		out.print("| <a href='http://localhost:8080/NewsPortal/Controller?command=GO_TO_MAIN_PAGE&page=" + i + "'>" + i +"</a> |");		
	}	
	out.print("</h4>");
	%>	

</body>
</html>