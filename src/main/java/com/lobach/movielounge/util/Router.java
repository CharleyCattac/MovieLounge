package com.lobach.movielounge.util;

public class Router {
    public enum RouteType {
        NOTHING, FORWARD, REDIRECT;
    }

    public final String url;
    private RouteType routeType;

    public Router(String url) {
        this.url = url;
        this.routeType = RouteType.FORWARD;
    }

    public RouteType getRouteType() {
        return routeType;
    }

    public void setRouteType(RouteType routeType) {
        this.routeType = routeType;
    }
}
