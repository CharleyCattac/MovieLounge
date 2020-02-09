<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<fmt:setLocale value="${locale}" scope="session" />
<fmt:setBundle basename="interface"/>
<html>
    <body>
    <ctg:role role="${userRole}"/>
    <div class="header header_absolute">
        <div class="header_main">
            <div class="header_inner">
                <div class="header_item header_logo">
                    <div class="logo">
                        <a href="control_servlet"> <img src="img/logo.png" class="logo_img"></a>
                    </div>
                </div>

                <div class="header_item">
                    <div class="header-menu">
                        <div class="header-menu_item">
                            <form id="moviesButton" action="control_servlet" method="POST">
                                <input type="hidden" name="command" value="show_movies"/>
                                <button type="submit" class="button_head button_transparent">
                                    <span class="button_title"><fmt:message key="header.label.movies"/></span>
                                </button>
                            </form>
                        </div>
                        <div class="header-menu_item">
                            <form id="eventsButton" action="control_servlet" method="POST">
                                <input type="hidden" name="command" value="show_events"/>
                                <button type="submit" class="button_head button_transparent">
                                    <span class="button_title"><fmt:message key="header.label.movieEvents"/></span>
                                </button>
                            </form>
                        </div>
                        <div class="header-menu_item">
                            <form id="reviewsButton" action="control_servlet" method="POST">
                                <input type="hidden" name="command" value="show_reviews"/>
                                <button type="submit" class="button_head button_transparent">
                                    <span class="button_title"><fmt:message key="header.label.reviews"/></span>
                                </button>
                            </form>
                        </div>
                    </div>
                </div>

                <div class="header_item">
                    <div class="header-right-menu" style="align-items: center">
                        <div class="header-right_menu_item">
                            <jsp:include page="/jsp/languageBar.jsp"/>
                        </div>
                    </div>
                </div>

                <div class="header_item">
                    <div class="header-right-menu" style="align-items: center">
                        <div class="header-right_menu_item">
                            <c:if test="${currentUser == null}">
                                <form id="sighInButton" action="control_servlet" method="POST">
                                    <input type="hidden" name="command" value="change_page"/>
                                    <input type="hidden" name="page" value="path.log_in"/>
                                    <button type="submit" class="button_success">
                                        <span class="button_title"><fmt:message key="login.button.log_in"/></span>
                                    </button>
                                </form>
                            </c:if>
                            <c:if test="${currentUser != null}">
                                <div class="avatar avatar_small">
                                    <img class="img-fluid" style="cursor: pointer"
                                         src="<c:out value="${currentUser.avatarURL}"/>"
                                         alt="<c:out value="${currentUser.avatarURL}"/>"
                                    >
                                    <form id="profileOpen" action="control_servlet" method="POST">
                                        <input type="hidden" name="command" value="show_profile"/>
                                    </form>
                                </div>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    </body>
</html>