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
                            <a href="javascript: void(0)" id="movies"><fmt:message key="header.label.movies"/></a>
                            <form id="openMovies" action="control_servlet" method="POST">
                                <input type="hidden" name="command" value="show_movies"/>
                            </form>
                        </div>
                        <div class="header-menu_item">
                            <a href="javascript: void(0)" id="schedule"><fmt:message key="header.label.schedule"/></a>
                            <form id="openEvents" action="control_servlet" method="POST">
                                <input type="hidden" name="command" value="show_events"/>
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
                                    <input type="hidden" name="command" value="profile"/>
                                    <button type="submit" class="button_success button_fit_content">
                                        <span class="button_title"><fmt:message key="sidebar.guest.button.sign_in"/></span>
                                    </button>
                                </form>
                            </c:if>
                            <c:if test="${authorisedUser != null}">
                                <a href="javascript:void(0)" id="profile" class="user_avatar">
                                    <img data-src="${avatarUrl}" src="${avatarUrl}"
                                         class="avatar avatar_medium" alt="NO IMAGE">
                                    <form id="profileOpen" action="control_servlet" method="POST">
                                        <input type="hidden" name="command" value="profile"/>
                                    </form>
                                </a>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    -->


    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="js/jquery-3.2.1.slim.min.js"></script>
    <script src="js/popper.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    </body>
</html>