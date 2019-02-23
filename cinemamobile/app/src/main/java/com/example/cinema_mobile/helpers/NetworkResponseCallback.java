package com.example.cinema_mobile.helpers;

import com.android.volley.NetworkResponse;
import com.android.volley.VolleyError;
import java.util.HashMap;

public interface NetworkResponseCallback {
    void onError(VolleyError error);

    void onResponse(NetworkResponse response);

    HashMap<String, String> setHeaders();
}
