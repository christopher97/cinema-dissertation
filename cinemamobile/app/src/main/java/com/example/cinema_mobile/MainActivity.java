package com.example.cinema_mobile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.example.cinema_mobile.helpers.AppConfig;
import com.example.cinema_mobile.helpers.Helper;
import com.example.cinema_mobile.helpers.VolleyResponseCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private Context mContext;

    private String getToken() { return Helper.getToken(mContext); }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        // check if user is logged in
        mContext = getApplicationContext();
        String token = getToken();
        System.out.println("TOKEN:" + token);
        Log.d("TOKEN VALUE: ", token);
        if (token.equals("")) {
            System.out.println("kontol");
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            checkTokenValidity(token);
        }
    }

    private void checkTokenValidity(String token) {
        // get request URL
        String url = AppConfig.validateTokenURL(token);

        Helper.MakeJsonObjectRequest(mContext, Request.Method.POST, url, null,
                new VolleyResponseCallback() {
                    @Override
                    public void onError(VolleyError error) {
                        NetworkResponse response = error.networkResponse;
                        if (response.statusCode == AppConfig.UNAUTHORIZED) {
                            Toast toast = Toast.makeText(MainActivity.this,
                                    "Session Expired", Toast.LENGTH_LONG);
                            toast.show();

                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // get token
                            String token = response.getString("token");

                            // set token
                            Helper.setToken(mContext, token);

                            Intent intent = new Intent(MainActivity.this, MainPageActivity.class);
                            startActivity(intent);
                            finish();
                        } catch (JSONException ex) {
                            Toast toast = Toast.makeText(MainActivity.this,
                                    "Token validation failed", Toast.LENGTH_LONG);
                            toast.show();
                        }
                    }

                    @Override
                    public HashMap<String, String> setHeaders() {
                        String token = getToken();

                        HashMap<String, String> headers = new HashMap<>();
                        headers.put("Authorization", "Bearer " + token);
                        return headers;
                    }
                });
    }
}
