package com.example.cinema_mobile;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
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

public class AccountActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private Context mContext;

    private EditText name;
    private EditText email;
    private EditText oldPass;
    private EditText newPass;
    private EditText icNumber;
    private EditText contactNumber;

    private String getToken() { return Helper.getToken(mContext); }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        mContext = getApplicationContext();
        setupNavbar();
        bindInputFields();
        fetchUserData();
    }

    private void fetchUserData() {
        String token = Helper.getToken(mContext);
        String url = AppConfig.userURL(token);
        Helper.MakeJsonObjectRequest(mContext, Request.Method.GET, url, null, new JsonResponseCallback() {
            @Override
            public void onError(VolleyError error) {
                NetworkResponse response = error.networkResponse;

                // expired token
                if (response.statusCode == AppConfig.UNAUTHORIZED) {
                    Toast toast = Toast.makeText(mContext, "Session Expired", Toast.LENGTH_LONG);
                    toast.show();

                    Intent intent = new Intent(mContext, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }

            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject user = response.getJSONObject("user");

                    name.setText(user.getString("name"));
                    email.setText(user.getString("email"));
                    icNumber.setText(user.getString("ic_number"));
                    contactNumber.setText(user.getString("contact_number"));
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast toast = Toast.makeText(mContext, "An error occurred", Toast.LENGTH_LONG);
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

    public void updateUserData(View view) {
        // if any field is empty
        if (name.getText().toString().equals("") || oldPass.getText().toString().equals("") ||
            newPass.getText().toString().equals("") || icNumber.getText().toString().equals("") ||
            contactNumber.getText().toString().equals("")) {

            Toast toast = Toast.makeText(mContext, "Please fill all fields!", Toast.LENGTH_LONG);
            toast.show();
            return;
        }

        //
        String token = Helper.getToken(mContext);
        String url = AppConfig.updateUserURL(token);

        // create JSON object
        JSONObject body = new JSONObject();
        try {
            body.put("name", name.getText());
            body.put("email", email.getText());
            body.put("oldPassword", oldPass.getText());
            body.put("newPassword", newPass.getText());
            body.put("ic_number", icNumber.getText());
            body.put("contact_number", contactNumber.getText());

            System.out.println(body);

            Helper.MakeJsonObjectRequest(mContext, Request.Method.PUT, url, body, new JsonResponseCallback() {
                @Override
                public void onError(VolleyError error) {
                    NetworkResponse response = error.networkResponse;

                    if (response.statusCode == AppConfig.UNAUTHORIZED) {
                        Toast toast = Toast.makeText(mContext, "Session Expired", Toast.LENGTH_LONG);
                        toast.show();

                        Intent intent = new Intent(mContext, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else if (response.statusCode == AppConfig.BAD_REQUEST) {
                        try {
                            JSONObject err = new JSONObject(new String(response.data));
                            String errorBody = err.getString("error");

                            Toast toast = Toast.makeText(AccountActivity.this, errorBody, Toast.LENGTH_LONG);
                            toast.show();
                        } catch(JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onResponse(JSONObject response) {
                    try {
                        // save token in shared preferences
                        String message = response.getString("message");

                        Toast toast = Toast.makeText(
                                AccountActivity.this,
                                message,
                                Toast.LENGTH_LONG
                        );
                        toast.show();

                        Intent intent = new Intent(AccountActivity.this, MainPageActivity.class);
                        startActivity(intent);
                        finish();
                    } catch (JSONException ex) {
                        Toast toast = Toast.makeText(AccountActivity.this,
                                "Update failed, please try again", Toast.LENGTH_LONG);
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
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }

    private void bindInputFields() {
        name = findViewById(R.id.accountName);
        email = findViewById(R.id.accountEmail);
        oldPass = findViewById(R.id.accountOldPassword);
        newPass = findViewById(R.id.accountNewPassword);
        icNumber = findViewById(R.id.accountICNumber);
        contactNumber = findViewById(R.id.accountContactNumber);

        email.setFocusable(false);
        email.setClickable(false);
    }

    private void setupNavbar() {
        mDrawerLayout = findViewById(R.id.account_drawer);

        NavigationView navigationView = findViewById(R.id.account_nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        Helper.onNavigationItemSelected(mContext, menuItem, mDrawerLayout);
                        return true;
                    }
                }
        );

        Toolbar toolbar = findViewById(R.id.account_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
