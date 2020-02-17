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
    <title><fmt:message key="events_page.title"/></title>
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
                <c:if test="${fatalMessage == null}">
                    <div class="container row news">
                        <article class="news_main"
                                <c:if test="${userRole == 'ADMIN' }"> style="max-width: 80%" </c:if>>
                            <header class="header-padding">
                                <h2 class="news_title"><fmt:message key="events_page.description"/></h2>
                            </header>
                        </article>
                        <c:if test="${userRole == 'ADMIN' }">
                        <div class="container flex-sm-column"
                             style="align-content: start; width: max-content; margin-top: 10px">
                            <form id="addEventButton" action="control_servlet" method="POST">
                                <input type="hidden" name="command" value="change_page"/>
                                <input type="hidden" name="page" value="path.create.event"/>
                                <button type="submit" class="button_red button_font-middle">
                                    <span class="button_title"><fmt:message key="events_page.admin.item.button.create"/></span>
                                </button>
                            </form>
                        </div>
                        </c:if>
                    </div>

                    <article class="news news_main">
                        <c:if test="${eventsSize == 0}">
                            <div class="header-padding single_line_typo">
                                <p><c:out value="${errorMessage}"/></p>
                            </div>
                        </c:if>
                        <c:forEach items="${events}" var="movieEvent">
                            <div class="tab_content-padding">
                                <div class="tab_content-item">
                                    <div class="multi_line_typo">
                                        <div class="container">
                                            <div class="flex-column" style="align-content: center">
                                                <div class="row" style="display: flow; margin-bottom: -20px;align-content: space-evenly" >
                                                    <div class="event_title" style="margin-left: -10px">
                                                        <p><fmt:message key="events_page.item.theme"/>
                                                            <c:out value="${movieEvent.theme}"/>
                                                        </p>
                                                    </div>
                                                    <div class="event_title" style="padding-left:30px ">
                                                        <p><fmt:message key="events_page.item.date"/>
                                                            <c:out value="${movieEvent.date}"/>
                                                        </p>
                                                    </div>
                                                </div>
                                                <div class="tab-block_text">
                                                    <p><fmt:message key="events_page.item.booked_1"/>
                                                        <c:out value="${movieEvent.bookingAmount}"/>
                                                        <fmt:message key="events_page.item.booked_2"/>
                                                    </p>
                                                </div>
                                                <div class="tab-block_text">
                                                    <p><fmt:message key="events_page.item.available"/>
                                                        <c:out value="${movieEvent.available}"/>
                                                    </p>
                                                </div>
                                                <div class="row"
                                                     style="margin-top: -20px; align-content: center;
                                                     margin-left: 7px; margin-bottom: 17px">
                                                    <c:forEach items="${movieEvent.movies}" var="movie">
                                                        <div class="flex-md-column"
                                                             style="
                                                             font-style: oblique; font-family: Calibri;
                                                             font-size: 22px; width: 255px; vertical-align: center">
                                                            <div class="tab-block_text"
                                                                 style="max-width: 250px; height: fit-content">
                                                                <p><c:out value="${movie.title}"/></p>
                                                            </div>
                                                        </div>
                                                    </c:forEach>
                                                    <c:forEach items="${movieEvent.movies}" var="movie">
                                                        <div class="flex-md-column"
                                                             style=" width: 255px; vertical-align: center">
                                                            <div class="poster_url poster_url-small">
                                                                <img src="<c:out value="${movie.poster}"/>" class="img-fluid"
                                                                     alt="<c:out value="${movie.poster}"/>"
                                                                >
                                                            </div>
                                                        </div>
                                                    </c:forEach>
                                                </div>
                                                <c:if test="${userRole == 'USER' }">
                                                    <div class="row" style="align-content: flex-start;
                                                    padding-bottom: 15px; padding-top: 7px">
                                                        <form id="bookButton" action="control_servlet" method="POST">
                                                            <input type="hidden" name="command" value="make_booking"/>
                                                            <input type="hidden" name="event_id" value="${movieEvent.id}"/>
                                                            <input type="hidden" name="user_id" value="${currentUser.id}"/>
                                                            <input type="hidden" name="current_amount" value="${movieEvent.bookingAmount}"/>
                                                            <div class="input-group form-group">
                                                                <input name="amount" type="number" class="form-control"
                                                                       min="1" max="20"
                                                                        <c:if test="${movieEvent.available == false}"> aria-disabled="true" </c:if>
                                                                       placeholder="<fmt:message key="events_page.user.item.hint.amount"/>"/>
                                                            </div>
                                                            <button type="submit" class="button_red button_padding-1" aria-pressed="true"
                                                            <c:if test="${movieEvent.available == false}"> disabled </c:if>>
                                                                <span class="button_title" style="width: 150%">
                                                                    <fmt:message key="events_page.user.item.button.book"/>
                                                                </span>
                                                            </button>
                                                        </form>
                                                        <c:if test="${errorMessage != null}">
                                                            <div class="multi_line_typo-small " style="margin: 5px">
                                                                <div class="auth_error_typo">
                                                                    <h3><c:out value="${errorMessage}"/></h3>
                                                                </div>
                                                            </div>
                                                        </c:if>
                                                    </div>
                                                </c:if>
                                                <c:if test="${userRole == 'ADMIN' }">
                                                    <div class="row" style="align-content: center;
                                                    padding-bottom: 15px; padding-top: 7px">
                                                        <form id="switchAvailabilityButton" action="control_servlet" method="POST">
                                                            <input type="hidden" name="command" value="switch_availability"/>
                                                            <input type="hidden" name="event_id" value="${movieEvent.id}"/>
                                                            <input type="hidden" name="event_availability" value="${movieEvent.available}"/>
                                                            <button type="submit" class="button_red button_padding-1"
                                                                    aria-pressed="true" style="margin-right: 20px">
                                                                <span class="button_title" style="width: 150%">
                                                                    <c:if test="${movieEvent.available == true }">
                                                                    <fmt:message key="events_page.admin.item.button.disable"/>
                                                                    </c:if>
                                                                    <c:if test="${movieEvent.available == false }">
                                                                        <fmt:message key="events_page.admin.item.button.set_available"/>
                                                                    </c:if>
                                                                </span>
                                                            </button>
                                                        </form>
                                                        <form id="deleteEventButton" action="control_servlet" method="POST">
                                                            <input type="hidden" name="command" value="delete_event"/>
                                                            <input type="hidden" name="event_id" value="${movieEvent.id}"/>
                                                            <button type="submit" class="button_red button_padding-1" aria-pressed="true">
                                                                <span class="button_title" style="width: 150%">
                                                                    <fmt:message key="events_page.admin.item.button.delete"/>
                                                                </span>
                                                            </button>
                                                        </form>
                                                    </div>
                                                </c:if>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </article>
                </c:if>
                <c:if test="${fatalMessage != null}">
                    <div class="auth_field">
                        <div class="auth_error_typo"> ${fatalMessage}</div>
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
