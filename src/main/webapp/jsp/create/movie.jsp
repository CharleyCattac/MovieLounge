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
    <title><fmt:message key="create.movie.title"/></title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" rel="stylesheet">
    <link href="<c:url value="/css/style.css"/>" rel="stylesheet">
    <link href="<c:url value="/css/main.css"/>" rel="stylesheet">

    <link href="https://cdnjs.cloudflare.com/ajax/libs/jquery-datetimepicker/2.5.20/jquery.datetimepicker.css"
          rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-datetimepicker/2.5.20/jquery.datetimepicker.full.js"></script>

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
</head>
<body>
<div class="app">
    <jsp:include page="/jsp/header.jsp"/>
    <div class="app_inner"  style="padding-top: 64px; align-content: center">
        <div class="main">
            <div class="main_inner">
                <div class="container row news" style="align-items: center; height: min-content">
                    <div class="container flex-lg-column" style="max-width: max-content">
                        <div class="login_card card_width-middle">
                            <div class="card-header">
                                <h3><fmt:message key="create.movie.header"/></h3>
                            </div>
                            <div class="card-body">
                                <form id="createEventAct" action="control_servlet" method="POST">
                                    <input type="hidden" name="command" value="create_movie"/>
                                    <div class="input-group form-group">
                                        <div class="input-group-prepend">
                                            <span class="input-group-text"><i class="fas fa-film"></i></span>
                                        </div>
                                        <input name="title" type="text" class="form-control" required
                                               placeholder="<fmt:message key="create.movie.input_field.title"/>"/>
                                    </div>
                                    <div class="input-group form-group">
                                        <div class="input-group-prepend">
                                            <span class="input-group-text"><i class="fas fa-info-circle"></i></span>
                                        </div>
                                        <input name="description" type="text" class="form-control" required
                                               placeholder="<fmt:message key="create.movie.input_field.description"/>"/>
                                    </div>
                                    <div class="input-group form-group">
                                        <div class="input-group-prepend">
                                            <span class="input-group-text"><i class="fas fa-smile"></i></span>
                                        </div>
                                        <input name="director" type="text" class="form-control" required
                                               placeholder="<fmt:message key="create.movie.input_field.director"/>"/>
                                    </div>
                                    <div class="input-group form-group">
                                        <div class="input-group-prepend">
                                            <span class="input-group-text"><i class="far fa-calendar-check"></i></span>
                                        </div>
                                        <input name="year" type="number" class="form-control" required
                                               min="1980" max="2020"
                                               placeholder="<fmt:message key="create.movie.input_field.year"/>"/>
                                    </div>
                                    <div class="input-group form-group">
                                        <div class="input-group-prepend">
                                            <span class="input-group-text"><i class="fas fa-star"></i></span>
                                        </div>
                                        <input name="rating" type="number" class="form-control" required
                                               min="0" max="10" step="0.001"
                                               placeholder="<fmt:message key="create.movie.input_field.rating"/>"/>
                                    </div>
                                    <div class="input-group form-group">
                                        <div class="input-group-prepend">
                                            <span class="input-group-text"><i class="fas fa-image"></i></span>
                                        </div>
                                        <input name="poster" type="url" class="form-control" required
                                               placeholder="<fmt:message key="create.movie.input_field.url"/>"/>
                                    </div>
                                    <div class="form-group">
                                        <button type="submit" class="float-right button_yellow button_font-middle">
                                            <span class="button_title"><fmt:message key="create.button"/></span>
                                        </button>
                                    </div>
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

