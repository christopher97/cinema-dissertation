package com.example.cinema_mobile;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.example.cinema_mobile.helpers.AppConfig;
import com.example.cinema_mobile.helpers.Helper;
import com.example.cinema_mobile.helpers.JsonResponseCallback;
import com.example.cinema_mobile.models.Ticket;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class TicketDetailActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private Ticket ticket;
    private Context mContext;

    private TextView status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_detail);

        mContext = getApplicationContext();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        ticket = bundle.getParcelable("ticket");

        this.setupNavbar();
        this.setTicketDetails();
    }

    private void setTicketDetails() {
        ImageView poster = findViewById(R.id.ticketDetailPoster);
        TextView title = findViewById(R.id.ticketDetailTitle);
        TextView date = findViewById(R.id.ticketDetailDate);
        TextView time = findViewById(R.id.ticketDetailTime);
        TextView cinema = findViewById(R.id.ticketDetailCinema);
        TextView qty = findViewById(R.id.ticketDetailQty);
        status = findViewById(R.id.ticketDetailStatus);

        Picasso.get().load(ticket.getImageURL()).into(poster);
        title.setText(ticket.getMovieTitle());
        date.setText(ticket.getDate());
        time.setText(ticket.getTime());
        cinema.setText(ticket.getCinema());
        qty.setText(AppConfig.getQtyString(ticket.getQty()));

        this.checkTicketValidity();
    }

    private void checkTicketValidity() {
        status.setText(AppConfig.getTicketStatus(ticket.getStatus()));
        if (ticket.getStatus() == AppConfig.used_ticket) {
            Button btn = findViewById(R.id.ticketDetailButton);
            btn.setVisibility(View.INVISIBLE);
        }
    }

    public void useTicket(View view) {
        String token = Helper.getToken(mContext);
        String url = AppConfig.invalidateURL(token);

        Toast toast = Toast.makeText(mContext, "Invalidating your ticket...", Toast.LENGTH_SHORT);
        toast.show();

        JSONObject body = new JSONObject();
        try {
            body.put("id", ticket.getId());

            Helper.MakeJsonObjectRequest(mContext, Request.Method.PUT, url, body, new JsonResponseCallback() {
                @Override
                public void onError(VolleyError error) {
                    NetworkResponse response = error.networkResponse;
                    try {
                        JSONObject json = new JSONObject(new String(response.data));
                        String errBody = json.getString("error");

                        Toast toast = Toast.makeText(mContext, errBody, Toast.LENGTH_SHORT);
                        toast.show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onResponse(JSONObject response) {
                    try {
                        String message = response.getString("message");

                        Toast toast = Toast.makeText(mContext, message, Toast.LENGTH_LONG);
                        toast.show();

                        ticket.setStatus(AppConfig.used_ticket);
                        checkTicketValidity();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public HashMap<String, String> setHeaders() {
                    return null;
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setupNavbar() {
        mDrawerLayout = findViewById(R.id.ticket_detail_drawer);

        NavigationView navigationView = findViewById(R.id.ticket_detail_nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        Helper.onNavigationItemSelected(mContext, menuItem, mDrawerLayout);
                        return true;
                    }
                }
        );

        Toolbar toolbar = findViewById(R.id.ticket_detail_toolbar);
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
