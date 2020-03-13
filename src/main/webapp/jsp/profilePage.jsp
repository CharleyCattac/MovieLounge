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
                <div class="container row news" style="height: min-content">
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

                <div class="flex-row" style="align-content: center; padding-bottom: 15px; padding-top: 7px">
                    <form id="bookingsButton" action="control_servlet" method="POST">
                        <input type="hidden" name="command" value="open_profile"/>
                        <input type="hidden" name="tab" value="booking"/>
                        <button type="submit" class="button_green button_font-small"
                                <c:if test="${userRole == 'USER'}"> disabled </c:if>>
                            <span class="button_title"><fmt:message key="profile.bookings.button"/></span>
                        </button>
                    </form>
                    <c:if test="${userRole == 'ADMIN'}">
                        <form id="usersButton" action="control_servlet" method="POST">
                            <input type="hidden" name="command" value="open_profile"/>
                            <input type="hidden" name="tab" value="user"/>
                            <button type="submit" class="button_green button_font-small"
                                    style="margin-top: 5px">
                                <span class="button_title"><fmt:message key="profile.users.button"/></span>
                            </button>
                        </form>
                    </c:if>
                </div>

                <c:if test="${fatalMessage == null}">
                    <article class="news news_main">
                            <c:if test="${errorMessage != null}">
                                <div class="multi_line_typo-small " style="margin-bottom: 5px">
                                    <div class="auth_error_typo">
                                        <a><c:out value="${errorMessage}"/></a>
                                    </div>
                                </div>
                            </c:if>
                            <c:if test="${tab == null || tab == 'booking'}">
                                <c:forEach items="${bookings}" var="booking">
                                <div class="tab_content-padding">
                                    <div class="tab_content-item" style="padding-top: 5px;padding-bottom: 15px">
                                        <div class="container">
                                            <div class="flex-column" style="align-content: center">
                                                    <div class="flex-column" style="align-items: start; ; margin-top: -7px">
                                                        <div class="multi_line_typo" style="max-width: 65%">
                                                            <c:if test="${userRole == 'ADMIN'}">
                                                                <div class="tab-block_text" style="margin-r: -10px">
                                                                    <p><c:out value="${booking.user.name}"/></p>
                                                                </div>
                                                            </c:if>
                                                            <div class="tab-block_text">
                                                                <p><c:out value="${booking.movieEvent.theme}"/></p>
                                                            </div>
                                                            <div class="tab-block_text">
                                                                <p><c:out value="${booking.movieEvent.date}"/></p>
                                                            </div>
                                                            <div class="tab-block_text" style="margin-bottom: -10px">
                                                                <p>
                                                                    <fmt:message key="profile.bookings.amount"/>
                                                                    <c:out value="${booking.amount}"/>
                                                                </p>
                                                            </div>
                                                            <div class="container row">
                                                                <p>
                                                                    <fmt:message key="profile.bookings.status"/>
                                                                    <c:if test="${booking.paid == true}">
                                                                        <fmt:message key="profile.bookings.paid"/>
                                                                    </c:if>
                                                                    <c:if test="${booking.paid == false}">
                                                                        <fmt:message key="profile.bookings.unpaid"/>
                                                                    </c:if>
                                                                </p>
                                                                <c:if test="${userRole == 'ADMIN'}">
                                                                    <form id="paidButton" action="control_servlet" method="POST">
                                                                        <input type="hidden" name="command" value="update_booking"/>
                                                                        <input type="hidden" name="book_id" value="${booking.id}"/>
                                                                        <input type="hidden" name="current_status" value="${booking.paid}"/>
                                                                        <button type="submit" class="button_red button_font-small"
                                                                                style="padding-left: 25px" disabled>
                                                                    <span class="button_title" style="align-content: center">
                                                                        <fmt:message key="profile.bookings.button.change_status"/>
                                                                    </span>
                                                                        </button>
                                                                    </form>
                                                                </c:if>
                                                            </div>
                                                            <form id="deleteBookingButton" action="control_servlet" method="POST">
                                                                <input type="hidden" name="command" value="update_booking"/>
                                                                <input type="hidden" name="book_id" value="${booking.id}"/>
                                                                <input type="hidden" name="action" value="delete"/>
                                                                <button type="submit" class="button_red button_font-small"
                                                                        disabled>
                                                                    <span class="button_title">
                                                                        <fmt:message key="profile.bookings.button.delete"/>
                                                                    </span>
                                                                </button>
                                                            </form>
                                                        </div>
                                                    </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                </c:forEach>
                            </c:if>
                            <c:if test="${tab == 'user' && userRole == 'ADMIN'}">
                                <c:if test="${errorMessage != null}">
                                    <div class="multi_line_typo-small " style="margin-bottom: 5px">
                                        <div class="auth_error_typo">
                                            <a><c:out value="${errorMessage}"/></a>
                                        </div>
                                    </div>
                                </c:if>
                                <c:forEach items="${users}" var="User">
                                <div class="tab_content-item" style="padding-top: 5px;padding-bottom: 15px;
                                        height: fit-content">
                                    <div class="container">
                                        <div class="flex-column" style="align-content: center">
                                                <div class="row" style="align-items: center">
                                                    <div class="multi_line_typo" style="max-width: 65%">
                                                        <div class="tab-block_text">
                                                            <p><c:out value="${User.name}"/></p>
                                                        </div>
                                                        <div class="tab-block_text">
                                                            <p><c:out value="${User.email}"/></p>
                                                        </div>
                                                        <div class="container flex-row" style="margin-left: 10px">
                                                            <form id="statusButton" action="control_servlet" method="POST">
                                                                <input type="hidden" name="command" value="update_user"/>
                                                                <input type="hidden" name="user_id" value="${User.id}"/>
                                                                <input type="hidden" name="action" value="status"/>
                                                                <input type="hidden" name="user_status" value="${User.status}"/>
                                                                <button type="submit" class="button_red button_font-small" disabled>
                                                                    <span class="button_title">
                                                                        <c:if test="${User.status == 'ACTIVE'}">
                                                                            <fmt:message key="profile.users.button.ban"/>
                                                                        </c:if>
                                                                        <c:if test="${User.status == 'BANNED'}">
                                                                            <fmt:message key="profile.users.buttons.unban"/>
                                                                        </c:if>
                                                                    </span>
                                                                </button>
                                                            </form>
                                                            <form id="roleButton" action="control_servlet" method="POST">
                                                                <input type="hidden" name="command" value="update_user"/>
                                                                <input type="hidden" name="user_id" value="${User.id}"/>
                                                                <input type="hidden" name="action" value="role"/>
                                                                <input type="hidden" name="user_status" value="${User.role}"/>
                                                                <button type="submit" class="button_red button_font-small" disabled
                                                                style="padding-left: 15px">
                                                                    <span class="button_title">
                                                                        <c:if test="${User.role == 'ADMIN'}">
                                                                            <fmt:message key="profile.users.buttons.downgrade"/>
                                                                        </c:if>
                                                                        <c:if test="${User.status == 'USER'}">
                                                                            <fmt:message key="profile.users.buttons.upgrade"/>
                                                                        </c:if>
                                                                    </span>
                                                                </button>
                                                            </form>
                                                        </div>
                                                    </div>
                                                </div>
                                        </div>
                                    </div>
                                </div>
                                </c:forEach>
                            </c:if>
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
