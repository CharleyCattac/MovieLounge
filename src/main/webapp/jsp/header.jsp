<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${locale}" scope="session" />
<fmt:setBundle basename="interface"/>
<html>
    <body>

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
                            <form id="openMovies" action="control_servlet" method="POST">
                                <input type="hidden" name="command" value="show_movies"/>
                                <button type="submit" class="button_fit_content button_transparent">
                                    <a class="header-menu_item_text" href="javascript: void(0)" id="movies">
                                        <fmt:message key="header.label.movies"/>
                                    </a>
                                </button>
                            </form>
                        </div>
                        <div class="header-menu_item">
                            <form id="openEvents" action="control_servlet" method="POST">
                                <input type="hidden" name="command" value="show_events"/>
                                <button type="submit" class="button_fit_content button_transparent">
                                    <a class="header-menu_item_text" href="javascript: void(0)" id="events">
                                        <fmt:message key="header.label.events"/>
                                    </a>
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
                            <c:if test="${authorisedUser == null}">
                                <form id="sighInButton" action="control_servlet" method="POST">
                                    <input type="hidden" name="command" value="log_in"/>
                                    <button type="submit" class="button_success">
                                        <span class="button_title"><fmt:message key="sidebar.guest.button.log_in"/></span>
                                    </button>
                                </form>
                            </c:if>
                            <c:if test="${authorisedUser != null}">
                                <a href="javascript:void(0)" id="profile" class="user_avatar">
                                    <img data-src="${avatarUrl}" src="${avatarUrl}"
                                         class="avatar avatar_medium" alt="NO IMAGE">
                                    <form id="profileOpen" action="control_servlet" method="POST">
                                        <input type="hidden" name="command" value="show_profile"/>
                                    </form>
                                </a>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    </body>
</html>