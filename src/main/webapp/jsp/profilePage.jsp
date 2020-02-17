<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${locale}" scope="session" />
<fmt:setBundle basename="interface"/>
<html>
<head>
    <meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />
    <title><fmt:message key="profile.title"/></title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" rel="stylesheet">
    <link href="<c:url value="/css/style.css"/>" rel="stylesheet">
    <link href="<c:url value="/css/main.css"/>" rel="stylesheet">
</head>
<body>
<div class="app">
    <jsp:include page="/jsp/header.jsp"/>
    <div class="app_inner" style="padding-top: 64px">
        <div class="main">
            <div class="main_inner">
                <div class="container row news" style="align-items: center; height: min-content">
                    <div class="container flex-lg-column" style="max-width: 85%">
                        <article class="news_main" style="width: fit-content">
                            <header class="header-padding">
                                <h2 class="news_title"><fmt:message key="profile.description"/></h2>
                            </header>
                        </article>
                    </div>
                    <div class="container flex-sm-column"
                         style="align-content: start; width: max-content; margin-top: 7px">
                        <form id="signOutButton" action="control_servlet" method="POST">
                            <input type="hidden" name="command" value="log_out"/>
                            <button type="submit" class="button_red button_font-middle">
                                <span class="button_title"><fmt:message key="profile.side_panel.button.log_out"/></span>
                            </button>
                        </form>
                    </div>
                </div>
            </div>
        </div>

    </div>
</div>
<script src="//code.jquery.com/jquery-3.4.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
<script type="text/javascript" src="https://unpkg.com/popper.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/crypto-js/3.1.9-1/crypto-js.min.js"></script>

</body>
</html>
