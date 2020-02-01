<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${locale}" scope="session" />
<fmt:setBundle basename="interface"/>
<html>
<head>
    <meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" rel="stylesheet">
    <link href="<c:url value="css/style.css" />" rel="stylesheet">
    <link href="<c:url value="css/main.css" />" rel="stylesheet">
</head>
<body>
<div class="sidebar-block sidebar-block_border">
    <div class="tabs sidebar-auth">
        <div class="tabs_container">
            <div class="tabs_tab tabs_tab_visible auth" id="sign_in-form">
                <div class="auth_header"></div>
                <div class="auth_content">
                    <form class="needs-validation" method="POST" action="control_servlet">
                        <input type="hidden" name="command" value="sign_in">
                            <div class="auth_field">
                                <fmt:message key="sidebar.guest.label.email"/>
                                <div class="input input_small">
                                    <div class="input_box">
                                        <input class="input_input form-control" type="text" name="email"
                                               pattern="^([a-zA-Z0-9_\-\.]+)@([a-zA-Z0-9_\-\.]+)\.([a-zA-Z]{2,5})$"
                                               title="<fmt:message key="sidebar.guest.correction.email"/>"
                                               placeholder="<fmt:message key="sidebar.guest.hint.email"/>" tabindex="0"
                                               id="guestEmail">
                                        <span class="input_clear input_clear_visible">
                                                            <span class="input_clear input_clear_visible">
                                                                <span class="input_clear-inner"><span
                                                                        class="input_clear-background"></span></span>
                                                            </span>
                                                        </span>
                                    </div>
                                </div>
                            </div>
                            <div class="auth_field">
                                <fmt:message key="sidebar.guest.label.password"/>
                                <div class="input input_small">
                                    <div class="input_box">
                                        <input class="input_input form-control" type="password"
                                               pattern="^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\s).{5,15}$"
                                               title="<fmt:message key="sidebar.guest.correction.password"/>"
                                               placeholder="<fmt:message key="sidebar.guest.hint.password"/>" tabindex="0"
                                               id="guestPassword">
                                        <input type="hidden" id="user-real-password-login" name="password"/>
                                    </div>
                                </div>
                            </div>
                            <div class="auth_field">
                                <span class="auth_error"></span>
                            </div>
                            <div class="auth_field">
                                <button type="submit" class="button_success button_fit_content">
                                    <span class="button_title"><fmt:message key="sidebar.guest.button.sign_in"/></span>
                                </button>
                            </div>
                            <div class="auth_field">
                                <div class="auth_error"> ${errorMessage}</div>
                            </div>
                            <div class="auth_field">
                                <a class="auth_action"
                                   id="register-form-link"><fmt:message key="sidebar.guest.button.sign_up"/></a>
                            </div>
                    </form>
                </div>
            </div>
            <div class="tabs_tab auth" style="display: none" id="register-form">
                <div class="auth_header"><fmt:message key="sidebar.guest.label.email"/></div>
                <div class="auth_content">
                    <form class="needs-validation" method="POST" action="controller" autocomplete="off">
                        <input type="hidden" name="command" value="sign_up">

                        <fmt:message key="sidebar.guest.label.name"/>
                        <div class="auth_field">
                            <div class="input input_small">
                                  <span class="input_box">
                                            <input class="input_input form-control" name="email"
                                                   pattern="^([a-zA-Z0-9_\-\.]+)@([a-zA-Z0-9_\-\.]+)\.([a-zA-Z]{2,5})$"
                                                   title="<fmt:message key="sidebar.guest.correction.email"/>"
                                                   placeholder="<fmt:message key="sidebar.guest.hint.email"/>"
                                            />
                                  </span>
                            </div>
                        </div>

                        <fmt:message key="sidebar.guest.label.password"/>
                        <div class="auth_field">
                            <div class="input input_box input_small" data-role="auth">
                                <span class="input_box">
                                    <input class="input_input form-control"
                                           type="password"
                                           pattern="^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\s).{5,15}$"
                                           title="<fmt:message key="sidebar.guest.correction.password"/>"
                                           placeholder="<fmt:message key="sidebar.guest.hint.password"/>"/>
                                    <input type="hidden" id="user-real-password-register"
                                           name="password"/>
                                    </span>
                            </div>
                        </div>

                        <fmt:message key="sidebar.guest.label.name"/>
                        <div class="auth_field">
                            <div class="input input_small">
                                  <span class="input_box">
                                        <input class="input_input form-control" name="name"
                                               pattern="(?=^.{0,40}$)^[a-zа-яA-ZА-Я-]+\s[a-zа-яA-ZА-Я-]+$"
                                               title="<fmt:message key="sidebar.guest.correction.name"/>"
                                               placeholder="<fmt:message key="sidebar.guest.hint.name"/>"
                                        />
                                  </span>
                            </div>
                        </div>

                        <fmt:message key="sidebar.guest.label.phone_number"/>
                        <div class="auth_field">
                            <div class="input input_small">
                                  <span class="input_box">
                                        <input class="input_input form-control" name="phone_number"
                                               pattern="^[0-9]*$"
                                               title="<fmt:message key="sidebar.guest.correction.phone_number"/>"
                                               placeholder="<fmt:message key="sidebar.guest.hint.phone_number"/>"
                                        />
                                  </span>
                            </div>
                        </div>

                        <fmt:message key="sidebar.guest.label.avatar_url"/>
                        <div class="auth_field">
                            <div class="input input_small">
                                  <span class="input_box">
                                        <input class="input_input form-control" name="avatar_url"
                                               placeholder="<fmt:message key="sidebar.guest.hint.avatar_url"/>"
                                        />
                                  </span>
                            </div>
                        </div>

                        <div class="auth_field">
                            <span class="auth_error"></span>
                        </div>
                        <div class="auth_field">
                            <button type="submit" class="button_success button_fit_content">
                                <fmt:message key="sidebar.guest.button.sign_up"/>
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
<script src="//code.jquery.com/jquery-3.4.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
<script type="text/javascript" src="https://unpkg.com/popper.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/crypto-js/3.1.9-1/crypto-js.min.js"></script>
</html>
