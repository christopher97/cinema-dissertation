package com.example.cinema_mobile;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.example.cinema_mobile.helpers.AppConfig;
import com.example.cinema_mobile.helpers.Helper;
import com.example.cinema_mobile.helpers.JsonResponseCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private EditText name;
    private EditText email;
    private EditText password;
    private EditText icNumber;
    private EditText contactNumber;

    private Context mContext;
    private boolean registered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mContext = getApplicationContext();
        registered = false;

        this.name = findViewById(R.id.nameRegister);
        this.email = findViewById(R.id.emailRegister);
        this.password = findViewById(R.id.passwordRegister);
        this.icNumber = findViewById(R.id.icRegister);
        this.contactNumber = findViewById(R.id.contactRegister);
    }

    public void register(View view) {
        // specify URL
        String url = AppConfig.registerURL();

        // create JSON object
        JSONObject body = new JSONObject();
        try {
            body.put("name", name.getText());
            body.put("email", email.getText());
            body.put("password", password.getText());
            body.put("ic_number", icNumber.getText());
            body.put("contact_number", contactNumber.getText());
        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        Helper.MakeJsonObjectRequest(mContext, Request.Method.POST, url, body, new JsonResponseCallback() {
            @Override
            public void onError(VolleyError error) {

                NetworkResponse response = error.networkResponse;
                String errorBody = new String(response.data);

                Toast toast = Toast.makeText(RegisterActivity.this, errorBody, Toast.LENGTH_LONG);
                toast.show();
            }

            @Override
            public void onResponse(JSONObject response) {
                try {
                    // save token in shared preferences
                    String token = response.getString("token");

                    // save token
                    Helper.setToken(mContext, token);
                    Helper.setPref(mContext, false);
                    registered = true;

                    Toast toast = Toast.makeText(
                            RegisterActivity.this,
                            "Registration Successful",
                            Toast.LENGTH_LONG
                    );
                    toast.show();

                    Intent intent = new Intent(RegisterActivity.this, PreferenceActivity.class);
                    startActivity(intent);
                    finish();
                } catch (JSONException ex) {
                    Toast toast = Toast.makeText(RegisterActivity.this,
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

    @Override
    public void finish() {
        Intent data = new Intent();

        if (registered)
            setResult(RESULT_OK, data);
        else
            setResult(RESULT_CANCELED, data);

        super.finish();
    }
}
