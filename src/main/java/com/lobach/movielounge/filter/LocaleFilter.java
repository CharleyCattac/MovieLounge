package com.lobach.movielounge.filter;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;

@WebFilter(urlPatterns = {"/*"}, initParams = {
        @WebInitParam(name = "locale", value = "EN", description = "Locale setting")})

public class LocaleFilter implements Filter {
    private static String shortLanguageName;
    private static String defaultBasename;
    private static final String LOCALE_ATTRIBUTE = "locale";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        shortLanguageName = filterConfig.getInitParameter(LOCALE_ATTRIBUTE);
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest) servletRequest).getSession();
        String localeAttr = (String) session.getAttribute(LOCALE_ATTRIBUTE);
        if (localeAttr == null) {
            localeAttr = shortLanguageName;
        }
        session.setAttribute(LOCALE_ATTRIBUTE, localeAttr);
        servletResponse.setLocale(Locale.forLanguageTag(localeAttr));
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }
}
