package com.lobach.movielounge.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class RequestContent {
    private static final int REQUEST_PARAMETER_INDEX = 0;

    private Map<String, Object> requestAttributes;
    private Map<String, String[]> requestParameters;
    private Map<String, Object> sessionAttributes;

    private boolean invalidate = false;

    public RequestContent(HttpServletRequest request) {
        requestAttributes = new HashMap<>();
        requestParameters = new HashMap<>();
        sessionAttributes = new HashMap<>();
        extractContent(request);
    }

    public Object getRequestAttribute(String attrName) {
        return requestAttributes.get(attrName);
    }

    public void setRequestAttribute(String attrName, Object attrValue) {
        requestAttributes.put(attrName, attrValue);
    }

    public String[] getRequestParameterArray(String paramName) {
        return requestParameters.get(paramName);
    }

    public String getRequestParameter(String paramName) {
        String[] parameterStrings = requestParameters.get(paramName);
        if (parameterStrings != null) {
            return parameterStrings[REQUEST_PARAMETER_INDEX];
        }
        return null;
    }

    public Object getSessionAttribute(String name) {
        return sessionAttributes.get(name);
    }

    public void setSessionAttribute(String name, Object value) {
        sessionAttributes.put(name, value);
    }

    public void invalidateSession() {
        invalidate = true;
    }

    public void passContent(HttpServletRequest request) {
        requestAttributes.forEach(request::setAttribute);
        HttpSession session = request.getSession();
        sessionAttributes.forEach(session::setAttribute);
        if (invalidate) {
            request.getSession().invalidate();
        }
    }

    private void extractContent(HttpServletRequest request) {
        Iterator<String> requestAttributesIterator = request.getAttributeNames().asIterator();
        while (requestAttributesIterator.hasNext()) {
            String name = requestAttributesIterator.next();
            requestAttributes.put(name, request.getAttribute(name));
        }

        requestParameters = request.getParameterMap();

        HttpSession session = request.getSession();
        Iterator<String> sessionAttributesIterator = session.getAttributeNames().asIterator();
        while (sessionAttributesIterator.hasNext()) {
            String name = sessionAttributesIterator.next();
            sessionAttributes.put(name, session.getAttribute(name));
        }
    }
}
