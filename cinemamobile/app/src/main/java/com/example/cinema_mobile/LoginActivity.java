package com.example.cinema_mobile;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.example.cinema_mobile.helpers.AppConfig;
import com.example.cinema_mobile.helpers.Helper;
import com.example.cinema_mobile.helpers.VolleyResponseCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    private Context mContext;

    // input fields
    private EditText email;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.email = findViewById(R.id.emailLogin);
        this.password = findViewById(R.id.passwordLogin);
    }

    public void login(View view) {
        // Specify the URL of request
        String url = AppConfig.loginURL();

        // Here we create the JSON Object of the HTTP request body we want to send
        JSONObject body = new JSONObject();
        try {
            body.put("email", email.getText().toString());
            body.put("password", password.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Helper.MakeJsonObjectRequest(mContext, Request.Method.POST, url, body, new VolleyResponseCallback() {
            @Override
            public void onError(VolleyError error) {

            }

            @Override
            public void onResponse(JSONObject response) {

            }

            @Override
            public HashMap<String, String> setHeaders() {
                return null;
            }
        });
    }
}
