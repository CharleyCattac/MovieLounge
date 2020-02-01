package com.lobach.movielounge.filter;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import java.io.IOException;
import java.util.Locale;

@WebFilter(urlPatterns = {"/*"}, initParams = {
        @WebInitParam(name = "locale", value = "EN", description = "Locale setting")})

public class LocaleFilter implements Filter {
    private static String shortLanguageName;
    private static final String LOCALE_ATTRIBUTE = "locale";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        shortLanguageName = filterConfig.getInitParameter(LOCALE_ATTRIBUTE);
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setAttribute(LOCALE_ATTRIBUTE, shortLanguageName);
        servletResponse.setLocale(Locale.forLanguageTag(shortLanguageName));
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
