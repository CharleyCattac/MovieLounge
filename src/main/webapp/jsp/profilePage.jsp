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

                <c:if test="${fatalMessage == null}">
                    <c:if test="${usersRole == 'ADMIN'}">
                        <article class="news news_main">
                            <div class="container row news" style="align-items: center; height: min-content">
                                <!--
                            <c:if test="${usersSize == 0}">
                                <div class="header-padding single_line_typo">
                                    <p><c:out value="${userErrorMessage}"/></p>
                                </div>
                            </c:if>
                            <table>
                                <tr>
                                    <th><fmt:message key="profile.user.booking_table.theme"/></th>
                                    <th><fmt:message key="profile.user.booking_table.date"/></th>
                                    <th><fmt:message key="profile.user.booking_table.status"/></th>
                                    <th><fmt:message key="profile.user.booking_table.booked"/></th>
                                    <th><fmt:message key="profile.user.booking_table.action"/></th>
                                    <th><fmt:message key="profile.user.booking_table.comment"/></th>
                                </tr>
                                <c:forEach items="${users}" var="user">
                                <tr>
                                    <td>booking.movieEvent.theme</td>
                                    <td>booking.movieEvent.date</td>
                                    <td>booking.paid</td>
                                    <td>booking.amount</td>
                                    <td>
                                        <form id="deleteBookButton" action="control_servlet" method="POST">
                                            <input type="hidden" name="command" value="delete_booking"/>
                                            <button type="submit" class="button_red button_font-middle">
                                                <span class="button_title"><fmt:message key="events_page.admin.item.button.delete"/></span>
                                            </button>
                                        </form>
                                    </td>
                                    <td>
                                        <c:if test="${errorMessage != null && errorBookId == user.id}">
                                            <div class="auth_field">
                                                <div class="auth_error_typo"> ${errorMessage}</div>
                                            </div>
                                        </c:if>
                                    </td>
                                </tr>
                                </c:forEach>
                            </table>
                            -->
                            </div>
                        </article>
                    </c:if>

                    <article class="news news_main">
                        <div class="container row news" style="align-items: center; height: min-content">

                        </div>
                        <!--
                        <c:if test="${bookingSize == 0}">
                            <div class="header-padding single_line_typo">
                                <p><c:out value="${errorMessage}"/></p>
                            </div>
                        </c:if>
                        <table>
                            <tr>
                                <th><fmt:message key="profile.user.booking_table.theme"/></th>
                                <th><fmt:message key="profile.user.booking_table.date"/></th>
                                <th><fmt:message key="profile.user.booking_table.status"/></th>
                                <th><fmt:message key="profile.user.booking_table.booked"/></th>
                                <th><fmt:message key="profile.user.booking_table.action"/></th>
                                <th><fmt:message key="profile.user.booking_table.comment"/></th>
                            </tr>
                            <c:forEach items="${bookings}" var="user">
                            <tr>
                                <td>booking.movieEvent.theme</td>
                                <td>booking.movieEvent.date</td>
                                <td>booking.paid</td>
                                <td>booking.amount</td>
                                <td>
                                    <form id="deleteBookButton" action="control_servlet" method="POST">
                                        <input type="hidden" name="command" value="delete_booking"/>
                                        <button type="submit" class="button_red button_font-middle">
                                            <span class="button_title"><fmt:message key="events_page.admin.item.button.delete"/></span>
                                        </button>
                                    </form>
                                </td>
                                <td>
                                    <c:if test="${errorMessage != null && errorBookId == user.id}">
                                        <div class="auth_field">
                                            <div class="auth_error_typo"> ${errorMessage}</div>
                                        </div>
                                    </c:if>
                                </td>
                            </tr>
                            </c:forEach>
                        </table>
                        -->
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
</div>
<script src="//code.jquery.com/jquery-3.4.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
<script type="text/javascript" src="https://unpkg.com/popper.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/crypto-js/3.1.9-1/crypto-js.min.js"></script>

</body>
</html>
