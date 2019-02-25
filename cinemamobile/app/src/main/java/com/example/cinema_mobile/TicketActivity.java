package com.example.cinema_mobile;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.example.cinema_mobile.helpers.AppConfig;
import com.example.cinema_mobile.helpers.Helper;
import com.example.cinema_mobile.helpers.JsonResponseCallback;
import com.example.cinema_mobile.models.Ticket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

public class TicketActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private Context mContext;

    private ArrayList<Ticket> newTickets;
    private ArrayList<Ticket> pastTickets;

    // new ticket recyclerView
    RecyclerView newTicketsView;
    RecyclerView.LayoutManager newTicketsLayoutManager;
    TicketAdapter newTicketAdapter;

    // past ticket recyclerView
    RecyclerView pastTicketsView;
    RecyclerView.LayoutManager pastTicketsLayoutManager;
    TicketAdapter pastTicketAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);

        mContext = getApplicationContext();

        newTickets = new ArrayList<>();
        pastTickets = new ArrayList<>();

        this.handleRecyclerView();
        this.setupNavbar();
        this.loadTickets();
    }

    private void setRecyclerData() {
        if (!newTickets.isEmpty())
            newTicketAdapter.notifyDataSetChanged();
        if (!pastTickets.isEmpty())
            pastTicketAdapter.notifyDataSetChanged();
    }

    private void handleRecyclerView() {
        // handle recyclerView
        newTicketsView = findViewById(R.id.newTicketsRecycler);
        newTicketsLayoutManager = new LinearLayoutManager(this);
        newTicketsView.setLayoutManager(newTicketsLayoutManager);

        pastTicketsView = findViewById(R.id.pastTicketsRecycler);
        pastTicketsLayoutManager = new LinearLayoutManager(this);
        pastTicketsView.setLayoutManager(pastTicketsLayoutManager);

        // set adapter
        newTicketAdapter = new TicketAdapter(newTickets);
        newTicketAdapter.setOnClick(new TicketAdapter.OnItemClicked() {
            @Override
            public void onItemClick(int position) {
                Ticket ticket = newTickets.get(position);
                Intent intent = new Intent(mContext, TicketDetailActivity.class);
                intent.putExtra("ticket", ticket);
                mContext.startActivity(intent);
                finish();
            }
        });
        newTicketsView.setAdapter(newTicketAdapter);

        pastTicketAdapter = new TicketAdapter(pastTickets);
        pastTicketAdapter.setOnClick(new TicketAdapter.OnItemClicked() {
            @Override
            public void onItemClick(int position) {
                Ticket ticket = newTickets.get(position);
                Intent intent = new Intent(mContext, TicketDetailActivity.class);
                intent.putExtra("ticket", ticket);
                mContext.startActivity(intent);
            }
        });
        pastTicketsView.setAdapter(pastTicketAdapter);
    }

    private void loadTickets() {
        String token = Helper.getToken(mContext);
        String url = AppConfig.ticketsURL(token);

        Helper.MakeJsonObjectRequest(mContext, Request.Method.GET, url, null, new JsonResponseCallback() {
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
                    System.out.println(response);
                    JSONArray arr = response.getJSONArray("tickets");
                    for(int i=0; i<arr.length(); i++) {
                        JSONObject curr = arr.getJSONObject(i);

                        Ticket ticket = new Ticket();
                        ticket.setId(curr.getInt("id"));
                        ticket.setQty(curr.getInt("qty"));
                        ticket.setStatus(curr.getInt("status"));

                        JSONObject performance = curr.getJSONObject("performance");
                        ticket.setDate(
                                AppConfig.spinnerDateFormat()
                                        .format(AppConfig.dateFormat()
                                                .parse(performance.getString("date"))));

                        JSONObject cinema = performance.getJSONObject("cinema");
                        ticket.setCinema(cinema.getString("name"));

                        JSONObject playtime = performance.getJSONObject("playtime");
                        ticket.setTime(playtime.getString("time"));

                        JSONObject movie = playtime.getJSONObject("movie");
                        ticket.setMovieTitle(movie.getString("title"));
                        ticket.setImageURL(movie.getString("image_url"));

                        int status = curr.getInt("status");
                        if (status == AppConfig.valid_ticket)
                            newTickets.add(ticket);
                        else
                            pastTickets.add(ticket);
                    }
                    setRecyclerData();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public HashMap<String, String> setHeaders() {
                return null;
            }
        });
    }

    private void setupNavbar() {
        mDrawerLayout = findViewById(R.id.ticket_drawer);

        NavigationView navigationView = findViewById(R.id.ticket_nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        Helper.onNavigationItemSelected(mContext, menuItem, mDrawerLayout);
                        return true;
                    }
                }
        );

        Toolbar toolbar = findViewById(R.id.ticket_toolbar);
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
