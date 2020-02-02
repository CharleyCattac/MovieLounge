<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="interface"/>
<html>
    <head>
        <meta http-equiv='Content-Type' content='text/html; charset=UTF-8'/>
    </head>
    <body>
        <div class="lang_bar-item">
            <div>
                <form id="to_en" action="control_servlet" method="GET">
                    <input type="hidden" name="command" value="change_locale"/>
                    <input type="hidden" name="locale" value="EN"/>
                    <button type="submit" class="button_width_30 button_transparent">
                        <img class="lang_img" src="img/lang/en.jpg"/>
                    </button>
                </form>
            </div>

            <div>
                <form id="to_ru" action="control_servlet" method="GET">
                    <input type="hidden" name="command" value="change_locale"/>
                    <input type="hidden" name="locale" value="RU"/>
                    <button type="submit" class="button_width_30 button_transparent">
                        <img class="lang_img" src="img/lang/ru.jpg"/>
                    </button>
                </form>
            </div>

            <div>
                <form id="to_by" action="control_servlet" method="GET">
                    <input type="hidden" name="command" value="change_locale"/>
                    <input type="hidden" name="locale" value="BY"/>
                    <button type="submit" class="button_width_30 button_transparent">
                        <img class="lang_img" src="img/lang/by.jpg"/>
                    </button>
                </form>
            </div>
        </div>
    </body>
</html>
