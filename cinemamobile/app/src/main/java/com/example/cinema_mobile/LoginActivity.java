package com.example.cinema_mobile;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.example.cinema_mobile.helpers.AppConfig;
import com.example.cinema_mobile.helpers.Helper;
import com.example.cinema_mobile.helpers.JsonResponseCallback;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import com.pusher.pushnotifications.BeamsCallback;
import com.pusher.pushnotifications.PushNotifications;
import com.pusher.pushnotifications.PusherCallbackError;
import com.pusher.pushnotifications.auth.AuthData;
import com.pusher.pushnotifications.auth.AuthDataGetter;
import com.pusher.pushnotifications.auth.BeamsTokenProvider;

public class LoginActivity extends AppCompatActivity {

    private Context mContext;
    private int registerCode = 5;

    // input fields
    private EditText email;
    private EditText password;

    private boolean preference_set;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.mContext = getApplicationContext();

        this.email = findViewById(R.id.emailLogin);
        this.password = findViewById(R.id.passwordLogin);
    }

    public void register(View view) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivityForResult(intent, registerCode);
    }

    public void login(View view) {
        // Specify the URL of request
        String url = AppConfig.loginURL();
        System.out.println("URL: " + url);

        // Here we create the JSON Object of the HTTP request body we want to send
        JSONObject body = new JSONObject();
        try {
            body.put("email", email.getText().toString());
            body.put("password", password.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Helper.MakeJsonObjectRequest(mContext, Request.Method.POST, url, body, new JsonResponseCallback() {
            @Override
            public void onError(VolleyError error) {

                NetworkResponse response = error.networkResponse;
                try {
                    JSONObject err = new JSONObject(new String(response.data));
                    String errorBody = err.getString("error");

                    Toast toast = Toast.makeText(LoginActivity.this, errorBody, Toast.LENGTH_LONG);
                    toast.show();
                } catch(JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onResponse(JSONObject response) {
                try {
                    // save token in shared preferences
                    String token = response.getString("token");
                    preference_set = (response.getInt("preference_set") == 1);

                    // save token
                    Helper.setToken(mContext, token);
                    Helper.setPref(mContext, preference_set);

                    Toast toast = Toast.makeText(
                            LoginActivity.this,
                            "Login Successful",
                            Toast.LENGTH_LONG
                    );
                    toast.show();

                    String userID = String.valueOf(response.getInt("user_id"));
                    integrateBeams(userID);
                } catch (JSONException ex) {
                    Toast toast = Toast.makeText(LoginActivity.this,
                            "Login failed, please try again", Toast.LENGTH_LONG);
                    toast.show();
                }
            }

            @Override
            public HashMap<String, String> setHeaders() {
                return null;
            }
        });
    }

    private void integrateBeams(String userID) {
        final String token = Helper.getToken(mContext);
        BeamsTokenProvider tokenProvider = new BeamsTokenProvider(
                AppConfig.beamsTokenURL(token),
                new AuthDataGetter() {
                    @NotNull
                    @Override
                    public AuthData getAuthData() {
                        // Headers and URL query params your auth endpoint needs to
                        // request a Beams Token for a given user
                        HashMap<String, String> headers = new HashMap<>();
                        headers.put("Authorization", "Bearer " + token);
                        HashMap<String, String> queryParams = new HashMap<>();
                        return new AuthData(
                                headers,
                                queryParams
                        );
                    }
                }
        );

        PushNotifications.setUserId(userID, tokenProvider, new BeamsCallback<Void, PusherCallbackError>() {
            @Override
            public void onSuccess(@NotNull Void... voids) {
                Log.i("BEAMS", "SUCCESS!!!");
                if (!preference_set) {
                    Intent intent = new Intent(LoginActivity.this, PreferenceActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(LoginActivity.this, MainPageActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(PusherCallbackError pusherCallbackError) {
                Toast toast = Toast.makeText(mContext, "An error occurred.", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            finish();
        }
    }
}
