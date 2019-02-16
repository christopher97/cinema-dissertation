package com.example.cinema_mobile.helpers;

public class AppConfig {

    private static final String domain = "http://10.0.2.2:8000/api/";

    public static String loginURL() {
        return domain + "login";
    }

    public static String registerURL() {
        return domain + "register";
    }

    public static String validateTokenURL(String token) {
        return domain + "validate-token?token=" + token;
    }

    public static final int UNAUTHORIZED = 401;
    public static final int BAD_REQUEST = 400;
    public static final int UNPROCESSABLE = 422;

    public static final int OK = 200;
    public static final int CREATED = 201;
    public static final int ACCEPTED = 202;
    public static final int NO_CONTENT = 204;
}
