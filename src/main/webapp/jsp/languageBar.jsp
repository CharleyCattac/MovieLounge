<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="interface"/>
<html>
<head>
    <meta http-equiv='Content-Type' content='text/html; charset=UTF-8'/>
</head>
<body>

<div class="">
    <a href="javascript: void(0)" id="en">
        <img src="img/lang/en.jpg" class="lang_img">
    </a>
    <form id="to_en" action="controller" method="GET">
        <input type="hidden" name="command" value="change_locale"/>
        <input type="hidden" name="locale" value="en"/>
    </form>
</div>

<div>
    <a href="javascript: void(0)" id="ru">
        <img src="img/lang/ru.jpg" class="lang_img">
    </a>
    <form id="to_ru" action="controller" method="GET">
        <input type="hidden" name="command" value="change_locale"/>
        <input type="hidden" name="locale" value="ru"/>
    </form>
</div>

<div>
    <a href="javascript: void(0)" id="by">
        <img src="img/lang/by.jpg" class="lang_img">
    </a>
    <form id="to_by" action="controller" method="GET">
        <input type="hidden" name="command" value="change_locale"/>
        <input type="hidden" name="locale" value="by"/>
    </form>
</div>
</body>
</html>
