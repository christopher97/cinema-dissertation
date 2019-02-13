package com.example.cinema_mobile.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public final class Helper {

    private Helper() {
        /*
         *      private constructor to prevent instantiation of this class
         */
    }

    public static final SharedPreferences getPreferences(Context ctx) {
        return ctx.getSharedPreferences("LOGIN_CREDENTIALS", ctx.MODE_PRIVATE);
    }

    public static final String getToken(Context ctx) {
        SharedPreferences preference = getPreferences(ctx);
        return preference.getString("token", "");
    }

    public static final void MakeJsonObjectRequest(Context context,
                                                   int method, String url, JSONObject body,
                                                   final VolleyResponseCallback callback) {

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (method, url, body, new Response.Listener<JSONObject>() {

                    // if response is HTTP 200 OK
                    @Override
                    public void onResponse(JSONObject response) {
                        callback.onResponse(response);
                    }
                }, new Response.ErrorListener() {

                    // on error
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onError(error);
                    }
                }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = super.getHeaders();

                HashMap<String, String> res = callback.setHeaders();
                if (res != null)
                    headers = res;

                return headers;
            }
        };

        VolleySingleton.getInstance(context).addToRequestQueue(jsObjRequest);
    }
}
