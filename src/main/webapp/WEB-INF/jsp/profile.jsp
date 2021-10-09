<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Profile</title>

<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="resources.localization.local" var="loc" />
<fmt:message bundle="${loc}" key="local.profile.head" var="head" />
<fmt:message bundle="${loc}" key="local.registration.personalInformation" var="personal" />
<fmt:message bundle="${loc}" key="local.registration.newPersonalInformation" var="new_personal_information" />
<fmt:message bundle="${loc}" key="local.registration.fullName" var="fullName" />
<fmt:message bundle="${loc}" key="local.registration.surname" var="surname" />
<fmt:message bundle="${loc}" key="local.registration.name" var="name" />
<fmt:message bundle="${loc}" key="local.registration.patronymic" var="patronymic" />
<fmt:message bundle="${loc}" key="local.registration.birthDay" var="birthday" />
<fmt:message bundle="${loc}" key="local.registration.month.january" var="january" />
<fmt:message bundle="${loc}" key="local.registration.month.february" var="february" />
<fmt:message bundle="${loc}" key="local.registration.month.march" var="march" />
<fmt:message bundle="${loc}" key="local.registration.month.april" var="april" />
<fmt:message bundle="${loc}" key="local.registration.month.may" var="may" />
<fmt:message bundle="${loc}" key="local.registration.month.june" var="june" />
<fmt:message bundle="${loc}" key="local.registration.month.july" var="july" />
<fmt:message bundle="${loc}" key="local.registration.month.august" var="august" />
<fmt:message bundle="${loc}" key="local.registration.month.september" var="september" />
<fmt:message bundle="${loc}" key="local.registration.month.october" var="october" />
<fmt:message bundle="${loc}" key="local.registration.month.november" var="november" />
<fmt:message bundle="${loc}" key="local.registration.month.december" var="december" />
<fmt:message bundle="${loc}" key="local.registration.recoveryInformation" var="recoveryInformation" />
<fmt:message bundle="${loc}" key="local.login" var="login" />
<fmt:message bundle="${loc}" key="local.password" var="password" />
<fmt:message bundle="${loc}" key="local.email" var="email" />
<fmt:message bundle="${loc}" key="local.registration.controlButtons" var="controlButtons" />
<fmt:message bundle="${loc}" key="local.registration.submit" var="submit" />
<fmt:message bundle="${loc}" key="local.registration.reset" var="reset" />
<fmt:message bundle="${loc}" key="local.locbutton.name.ru" var="ru_button" />
<fmt:message bundle="${loc}" key="local.locbutton.name.en" var="en_button" />
<fmt:message bundle="${loc}" key="local.backToMain" var="back_to_main"/>

</head>
<body>
<a href="http://localhost:8080/NewsPortal/Controller?command=go_to_main_page&page=1">${back_to_main}</a>
	<div align="right">
		<form action="Controller" method="post">
			<input type="hidden" name="command" value="set_locale" />
			<input type="hidden" name="local" value="ru" />
			<input type="hidden" name="page" value="/WEB-INF/jsp/profile.jsp" />
			<input type="submit" value="${ru_button}" />
		</form>
	</div>
	<div align="right">
		<form action="Controller" method="post">
			<input type="hidden" name="command" value="set_locale" />
			<input type="hidden" name="local" value="en" />
			<input type="hidden" name="page" value="/WEB-INF/jsp/profile.jsp" />
			<input type="submit" value="${en_button}" />
		</form>
	</div>	

	<h1>${head}</h1>
	<p>${surname}: ${requestScope.registrationInfo.surname}</p>
	<p>${name}: ${requestScope.registrationInfo.name}</p>
	<p>${patronymic}: ${requestScope.registrationInfo.patronymic}</p>
	<p>${birthday}: ${requestScope.registrationInfo.birthday}</p>
	<p>${login}: ${requestScope.registrationInfo.login}</p>
	<p>${email}: ${requestScope.registrationInfo.email}</p>

	<h2>${new_personal_information}</h2>

	<form action="Controller" method="post">
		<input type="hidden" name="command" value="update_user" />
		<fieldset>
			<legend>${personal}</legend>
			<fieldset>
				<legend>${fullName}</legend>
				<label><input type="text" name="surname" value="${surname}" autofocus required pattern="[А-Яа-яЁё]{2,25}" maxlength="25"></label><br>
				<label><input type="text" name="name" value="${name}" required pattern="[А-Яа-яЁё]{2,25}" maxlength="25"></label><br>
				<label><input type="text" name="patronymic" value="${patronymic}" required pattern="[А-Яа-яЁё]{2,25}" maxlength="25"></label><br>
			</fieldset>

			<fieldset>
				<legend>${birthday}</legend>
				<input type="text" name="year" pattern="[0-9]{4}" value="2000"><br>
				<select name="month">
					<option value="01">${january}</option>
					<option value="02">${february}</option>
					<option value="03">${march}</option>
					<option value="04">${april}</option>
					<option value="05">${may}</option>
					<option value="06">${june}</option>
					<option value="07">${july}</option>
					<option value="08">${august}</option>
					<option value="09">${september}</option>
					<option value="10">${october}</option>
					<option value="11">${november}</option>
					<option value="12">${december}</option>
				</select> <br> <input type="number" name="day" min="1" max="31" step="1"
					value="1">
			</fieldset>

			<fieldset>
				<legend>${recoveryInformation}</legend>
				<label><input type="text" name="login" required pattern="[a-zA-Z0-9]{2,20}">${login}</label><br> 
				<label><input type="password" name="password" required pattern="[a-zA-Z0-9]{5,20}" autocomplete="off">${password}</label><br>
				<label><input type="email" name="email" required pattern="[a-zA-Z0-9]+@[a-zA-Z0-9]+\.[a-zA-Z0-9]+"> ${email}</label>
			</fieldset>

			<fieldset>
				<legend>${controlButtons}</legend>
				<input type="submit" name="sub" value="${submit}"> <input
					type="reset" name="res" value="${reset}"><br>
			</fieldset>

		</fieldset>
	</form>
</body>
</html>