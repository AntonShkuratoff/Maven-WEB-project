<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Unknown Command</title>

<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="resources.localization.local" var="loc" />
<fmt:message bundle="${loc}" key="local.unknownCommand.head" var="head" />
<fmt:message bundle="${loc}" key="local.unknownCommand.message" var="message" />
<fmt:message bundle="${loc}" key="local.homepage" var="homepage" />

</head>
<body>
	<h1 align="center">${head}</h1>
	<p>${message} <a href="http://localhost:8080/NewsPortal">${homepage}</a></p>
	
	<%
	if (request.getAttribute("errorArray") != null) {
		List<String> errorArray = new ArrayList<String>();
		errorArray = (List<String>) request.getAttribute("errorList");
		for (String error : errorArray) {
			out.print(error);
		}
	}
	%>

</body>
</html>