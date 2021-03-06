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
    <title><fmt:message key="create.event.title"/></title>
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
                                <h3><fmt:message key="create.event.header"/></h3>
                            </div>
                            <div class="card-body">
                                <form id="createEventAct" action="control_servlet" method="POST">
                                    <input type="hidden" name="command" value="create_event"/>
                                    <div class="input-group form-group">
                                        <div class="input-group-prepend">
                                            <span class="input-group-text"><i class="fas fa-table"></i></span>
                                        </div>
                                        <input name="date" type="text" class="form-control" id="date" required
                                               placeholder="<fmt:message key="create.event.input_field.date"/>"/>
                                    </div>
                                    <div class="input-group form-group">
                                        <div class="input-group-prepend">
                                            <span class="input-group-text"><i class="far fa-clock"></i></span>
                                        </div>
                                        <div class="time_border">
                                            <input name="hours" type="number" class="time_item" required
                                                   min="0" max="23" placeholder="00">:
                                            <input name="minutes" type="number" class="time_item" required
                                                   min="0" max="55" step="5" placeholder="00">
                                        </div>
                                    </div>
                                    <div class="input-group form-group">
                                        <div class="input-group-prepend">
                                            <span class="input-group-text"><i class="fab fa-ethereum"></i></span>
                                        </div>
                                        <input name="theme" type="text" class="form-control" required
                                               placeholder="<fmt:message key="create.event.input_field.theme"/>"/>
                                    </div>
                                    <div class="input-group form-group">
                                        <div class="input-group-prepend">
                                            <span class="input-group-text"><i class="fas fa-film"></i></span>
                                        </div>
                                        <input name="movie1" type="text" class="form-control" required
                                               placeholder="<fmt:message key="create.event.input_field.movie1"/>"/>
                                    </div>
                                    <div class="input-group form-group">
                                        <div class="input-group-prepend">
                                            <span class="input-group-text"><i class="fas fa-film"></i></span>
                                        </div>
                                        <input name="movie2" type="text" class="form-control" required
                                               placeholder="<fmt:message key="create.event.input_field.movie2"/>"/>
                                    </div>
                                    <div class="input-group form-group">
                                        <div class="input-group-prepend">
                                            <span class="input-group-text"><i class="fas fa-film"></i></span>
                                        </div>
                                        <input name="movie3" type="text" class="form-control" required
                                               placeholder="<fmt:message key="create.event.input_field.movie3"/>"/>
                                    </div>
                                    <div class="form-group">
                                        <button type="submit" class="float-right button_yellow button_font-middle">
                                            <span class="button_title"><fmt:message key="create.button"/></span>
                                        </button>
                                    </div>
                                    <script>
                                        $('#date').datetimepicker({
                                            format:"Y-m-d",
                                            timepicker:false,
                                            defaultDate: new Date(),
                                            scrollMonth:false,
                                            scrollTime:false,
                                            scrollInput:false,
                                            startDate: new Date(),
                                            minDate: new Date(),
                                            maxDate:'2020/12/31',
                                        });
                                    </script>
                                </form>
                            </div>
                            <div class="card-footer">
                                <div class="container flex-column">
                                    <h2><fmt:message key="create.event.text.0"/></h2>
                                    <a><fmt:message key="create.event.text.1"/></a>
                                    <br><fmt:message key="create.event.text.2"/></br>
                                </div>
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
