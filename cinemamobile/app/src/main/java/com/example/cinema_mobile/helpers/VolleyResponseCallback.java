package com.example.cinema_mobile.helpers;

import com.android.volley.VolleyError;
import org.json.JSONObject;
import java.util.HashMap;

public interface VolleyResponseCallback {
    void onError(VolleyError error);

    void onResponse(JSONObject response);

    HashMap<String, String> setHeaders();
}
