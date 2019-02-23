package com.example.cinema_mobile.helpers;

import java.text.SimpleDateFormat;

public class AppConfig {

    public static SimpleDateFormat dateFormat() {
        return new SimpleDateFormat("yyyy-MM-dd");
    }

    private static final String domain = "http://10.0.2.2:8000/api/";

    public static String loginURL() {
        return domain + "login";
    }

    public static String registerURL() {
        return domain + "register";
    }

    public static String moviesURL() { return domain + "movies/playing"; }

    public static String updateUserURL(String token) {
        return domain + "user/update?token=" + token;
    }

    public static String userURL(String token) {
        return domain + "user?token=" + token;
    }

    public static String logoutURL(String token) {
        return domain + "logout?token=" + token;
    }

    public static String validateTokenURL(String token) {
        return domain + "validate-token?token=" + token;
    }

    public static String cinemaURL() {
        return domain + "cinema";
    }

    public static String playtimeURL(int id) {
        return domain + "movie/playtime/" + id;
    }

    public static String performanceURL(int id) {
        return domain + "movie/" + id + "/performance";
    }

    public static final String utf_8 = "utf-8";

    public static final int UNAUTHORIZED = 401;
    public static final int BAD_REQUEST = 400;
    public static final int UNPROCESSABLE = 422;

    public static final int OK = 200;
    public static final int CREATED = 201;
    public static final int ACCEPTED = 202;
    public static final int NO_CONTENT = 204;

    public static final String home = "Home";
    public static final String tickets = "Tickets";
    public static final String account = "Account";
}
