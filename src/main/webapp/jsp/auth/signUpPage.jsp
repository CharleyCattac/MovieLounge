<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<fmt:setLocale value="${locale}" scope="session" />
<fmt:setBundle basename="interface"/>
<html>
<head>
    <meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />
    <title><fmt:message key="sign_up.title"/></title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" rel="stylesheet">

    <!--Bootsrap 4 CDN-->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
          integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO"
          crossorigin="anonymous">

    <!--Fontawesome CDN-->
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css"
          integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU"
          crossorigin="anonymous">

    <!--Custom styles-->
    <link rel="stylesheet" type="text/css" href="styles.css">
    <link href="<c:url value="/css/style.css"/>" rel="stylesheet">
    <link href="<c:url value="/css/main.css"/>" rel="stylesheet">
</head>
<body>
<div class="app">
    <jsp:include page="/jsp/header.jsp"/>
    <div class="app_inner"  style="padding-top: 64px; align-content: center">
        <div class="main">
            <div class="main_inner">
                <div class="container row news" style="align-items: center; height: min-content">
                    <div class="container flex-lg-column" style="max-width: min-content">
                        <div class="login_card card_width-small">
                            <div class="card-header">
                                <h3><fmt:message key="sign_up.title"/></h3>
                            </div>
                            <div class="card-body">
                                <form id="signUpAct" action="control_servlet" method="POST" onsubmit="">
                                    <input type="hidden" name="command" value="sign_up"/>
                                    <div class="input-group form-group">
                                        <div class="input-group-prepend">
                                            <span class="input-group-text"><i class="fas fa-envelope"></i></span>
                                        </div>
                                        <input name="email" type="email" class="form-control"
                                               placeholder="<fmt:message key="login.hint.email"/>"
                                               value="${email}"/>
                                    </div>
                                    <div class="input-group form-group">
                                        <div class="input-group-prepend">
                                            <span class="input-group-text"><i class="fas fa-key"></i></span>
                                        </div>
                                        <input name="password" type="password" class="form-control"
                                               id="password"
                                               placeholder="<fmt:message key="login.hint.password"/>"/>
                                    </div>
                                    <div class="input-group form-group" style="margin-bottom: 40px">
                                        <div class="input-group-prepend">
                                            <span class="input-group-text"><i class="fas fa-key"></i></span>
                                        </div>
                                        <input name="password" type="password" class="form-control"
                                               id="confirmPassword"
                                               placeholder="<fmt:message key="sign_up.hint.password"/>"/>
                                    </div>
                                    <div class="input-group form-group">
                                        <div class="input-group-prepend">
                                            <span class="input-group-text"><i class="fas fa-user-alt"></i></span>
                                        </div>
                                        <input name="name" type="text" class="form-control"
                                               placeholder="<fmt:message key="sign_up.hint.name"/>"
                                               value="${name}"/>
                                    </div>
                                    <div class="form-group">
                                        <div class="input-group form-group">
                                            <div class="input-group-prepend">
                                                <span class="input-group-text"><i class="fas fa-phone"></i></span>
                                            </div>
                                            <input name="phone" type="tel" class="form-control"
                                                   placeholder="<fmt:message key="sign_up.hint.phone_number"/>"
                                                   value="${phone}"/>
                                        </div>
                                        <div class="form-group">
                                            <div class="input-group form-group">
                                                <div class="input-group-prepend">
                                                    <span class="input-group-text"><i class="fas fa-image"></i></span>
                                                </div>
                                                <input name="avatar" type="text" class="form-control"
                                                       placeholder="<fmt:message key="sign_up.hint.avatar_url"/>"
                                                       value="${avatar}"/>
                                            </div>
                                            <div class="form-group">
                                        <button type="submit" class="float-right button_yellow button_font-middle">
                                            <span class="button_title"><fmt:message key="sign_up.title"/></span>
                                        </button>
                                    </div>
                                    <script>
                                        var password = document.getElementById("password");
                                        var confirm_password = document.getElementById("confirmPassword");

                                        function validatePassword(){
                                            if(password.value !== confirm_password.value) {
                                                confirm_password.setCustomValidity("Passwords don't match");
                                            } else {
                                                confirm_password.setCustomValidity('');
                                            }
                                        }

                                        password.onchange = validatePassword;
                                        confirm_password.onkeyup = validatePassword;
                                    </script>
                                </form>
                            </div>
                        </div>
                        <c:if test="${errorMessage != null}">
                            <div class="multi_line_typo-small " style="margin: 5px">
                                <div class="auth_error_typo">
                                    <h3><c:out value="${errorMessage}"/></h3>
                                </div>
                            </div>
                        </c:if>
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
