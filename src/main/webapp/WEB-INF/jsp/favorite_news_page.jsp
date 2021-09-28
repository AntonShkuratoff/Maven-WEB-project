<%@page import="java.util.ArrayList"%>
<%@page import="by.itacademy.newsportal.bean.News"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Favorite News</title>

<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="resources.localization.local" var="loc" />
<fmt:message bundle="${loc}" key="local.locbutton.name.ru" var="ru_button" />
<fmt:message bundle="${loc}" key="local.locbutton.name.en" var="en_button" />
<fmt:message bundle="${loc}" key="local.backToMain" var="back_to_main"/>
<fmt:message bundle="${loc}" key="local.news.favorite" var="favorite_news"/>

</head>
<body>
	<div>
	<a href="http://localhost:8080/NewsPortal/Controller?command=go_to_main_page&page=1">${back_to_main}</a>
		<div>
			<div align="right">
				<form action="Controller" method="post">
					<input type="hidden" name="command" value="set_locale" />
					<input type="hidden" name="local" value="ru" />
					<input type="hidden" name="page" value="/WEB-INF/jsp/favorite_news_page.jsp"/>
					<input type="submit" value="${ru_button}" />
				</form>
			</div>
			<div align="right">
				<form action="Controller" method="post">
					<input type="hidden" name="command" value="set_locale" />
					<input type="hidden" name="local" value="en" />
					<input type="hidden" name="page" value="/WEB-INF/jsp/favorite_news_page.jsp" />
					<input type="submit" value="${en_button}" />
				</form>
			</div>
		</div>

	</div>
	<h2>${favorite_news}</h2>
	<c:forEach var="news" items="${requestScope.newsList}">
		<a href="http://localhost:8080/NewsPortal/Controller?command=go_to_news_page&news_type=published&news_ID=${news.id}">				
				<div>
					<fieldset>
						<legend>${news.title}</legend>				
						<h3 align='center'><c:out value="${news.brief}" /></h3>
						<h4 align="right"><c:out value="${news.date}" /></h4>				
					</fieldset>						
				</div>				
		</a>
	</c:forEach>		

</body>
</html>