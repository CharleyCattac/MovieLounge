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
    <title><fmt:message key="movies_page.title"/></title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/jquery-datetimepicker/3.2.1/jquery.datetimepicker.css"
          rel="stylesheet">
    <link href="<c:url value="/css/style.css" />" rel="stylesheet">
    <link href="<c:url value="/css/main.css" />" rel="stylesheet">

    <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-datetimepicker/3.2.1/jquery.datetimepicker.full.js"></script>
</head>
<body>
<div class="app">
    <jsp:include page="/jsp/header.jsp"/>
    <div class="app_inner" style="padding-top: 64px">
        <div class="main">
            <div class="main_inner">
                <c:if test="${error_message == null}">
                    <article class="news news_main">
                        <header class="news_header">
                            <h2 class="news_title"><fmt:message key="movies_page.description"/></h2>
                        </header>
                    </article>
                    <article class="news news_main">
                        <c:if test="${movies_size == 0}">
                            <div class="tab-block_text">
                                <p><fmt:message key="message.error.empty_list"/></p>
                            </div>
                        </c:if>
                        <c:forEach items="${movies}" var="movie">
                            <div class="tab_content">
                                <div class="tab_content-item">
                                    <div class="tab_typography">
                                        <div class="tab_title" onfocus="">
                                            <p><c:out value="${movie.title}"/></p>
                                        </div>
                                        <div class="tab-block_text">
                                            <p><c:out value="${movie.description}"/></p>
                                        </div>
                                        <div class="tab-block_text">
                                            <p><fmt:message key="movies_page.item.director"/>
                                                <c:out value="${movie.director}"/>
                                            </p>
                                        </div>
                                        <div class="tab-block_text">
                                            <p><fmt:message key="movies_page.item.release_year"/>
                                                <c:out value="${movie.releaseYear}"/>
                                            </p>
                                        </div>
                                        <div class="tab-block_text">
                                            <p><fmt:message key="movies_page.item.rating"/>
                                                <c:out value="${movie.rating}"/>
                                            </p>
                                        </div>
                                    </div>
                                    <div class="poster_url-medium">
                                        <picture>
                                            <source srcset="<c:out value="${movie.poster}"/>" type="image/svg+xml">
                                            <img src="<c:out value="${movie.poster}"/>" class="img-fluid"
                                                 alt="<c:out value="${movie.poster}"/>"
                                            >
                                        </picture>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </article>
                </c:if>
                <c:if test="${error_message != null}">
                    <div class="auth_field">
                        <div class="auth_error"> ${error_message}</div>
                    </div>
                </c:if>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
<script src="https://unpkg.com/popper.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/crypto-js/3.1.9-1/crypto-js.min.js"></script>
<script src="https://kit.fontawesome.com/e9321a3cf3.js"></script>

</body>
</html>
