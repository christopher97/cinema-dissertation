package com.example.cinema_mobile.helpers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.cinema_mobile.AccountActivity;
import com.example.cinema_mobile.LoginActivity;
import com.example.cinema_mobile.MainActivity;
import com.example.cinema_mobile.MainPageActivity;
import com.example.cinema_mobile.R;
import com.example.cinema_mobile.TicketActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
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

    public static final void setToken(Context ctx, String token) {
        SharedPreferences preferences = getPreferences(ctx);
        SharedPreferences.Editor editor = preferences.edit();

        // Save data
        editor.putString("token", token);
        editor.commit();
    }

    public static final void logout(Context ctx) {
        SharedPreferences pref = getPreferences(ctx);
        SharedPreferences.Editor editor = pref.edit();

        editor.remove("token");
        editor.commit();
    }

    public static final void onNavigationItemSelected
            (final Context ctx, MenuItem menuItem, DrawerLayout mDrawerLayout) {
        // set item as selected to persist highlight
        menuItem.setChecked(true);
        // close drawer when item is tapped
        mDrawerLayout.closeDrawers();

        // swap UI fragments here
        Intent intent;
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                intent = new Intent(ctx, MainPageActivity.class);
                ctx.startActivity(intent);
                break;
            case R.id.nav_tickets:
                intent = new Intent(ctx, TicketActivity.class);
                ctx.startActivity(intent);
                break;
            case R.id.nav_account:
                intent = new Intent(ctx, AccountActivity.class);
                ctx.startActivity(intent);
                break;
            case R.id.nav_logout:
                String token = getToken(ctx);
                String url = AppConfig.logoutURL(token);

                // call the jsonrequest
                MakeJsonObjectRequest(ctx, Request.Method.POST, url, null, new JsonResponseCallback() {
                    @Override
                    public void onError(VolleyError error) {
                        Toast toast = Toast.makeText(ctx, "Session Expired", Toast.LENGTH_LONG);
                        toast.show();

                        Intent intent = new Intent(ctx, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        ctx.startActivity(intent);
                    }

                    @Override
                    public void onResponse(JSONObject response) {
                        Helper.logout(ctx);
                        Toast toast = Toast.makeText(ctx, "You have logged out", Toast.LENGTH_LONG);
                        toast.show();

                        Intent intent = new Intent(ctx, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        ctx.startActivity(intent);
                    }

                    @Override
                    public HashMap<String, String> setHeaders() {
                        return null;
                    }
                });

                break;
        }
    }

    public static final JSONObject parseJSON(NetworkResponse response) {
        try {
            String jsonString =
                    new String(
                            response.data,
                            HttpHeaderParser.parseCharset(response.headers, AppConfig.utf_8));
            return new JSONObject(jsonString);
        } catch (UnsupportedEncodingException e) {
            return null;
        } catch (JSONException je) {
            return null;
        }
    }

    public static final void MakeNetworkResponseRequest(Context context,
                                                        int method, String url, JSONObject body,
                                                        final NetworkResponseCallback callback) {

        NetworkResponseRequest netResponseRequest = new NetworkResponseRequest
                (method, url, body, new Response.Listener<NetworkResponse>() {

                    // if response is 2xx
                    @Override
                    public void onResponse(NetworkResponse response) {
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

        netResponseRequest.setRetryPolicy
                (new DefaultRetryPolicy(10000, 1, 1.0f));

        VolleySingleton.getInstance(context).addToRequestQueue(netResponseRequest);
    }

    public static final void MakeJsonObjectRequest(Context context,
                                                   int method, String url, JSONObject body,
                                                   final JsonResponseCallback callback) {

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

        jsObjRequest.setRetryPolicy
                (new DefaultRetryPolicy(10000, 1, 1.0f));

        VolleySingleton.getInstance(context).addToRequestQueue(jsObjRequest);
    }
}
