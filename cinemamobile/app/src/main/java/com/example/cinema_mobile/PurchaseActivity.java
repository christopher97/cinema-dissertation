package com.example.cinema_mobile;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.example.cinema_mobile.helpers.AppConfig;
import com.example.cinema_mobile.helpers.Helper;
import com.example.cinema_mobile.helpers.JsonResponseCallback;
import com.example.cinema_mobile.models.Cinema;
import com.example.cinema_mobile.models.Movie;
import com.example.cinema_mobile.models.Performance;
import com.example.cinema_mobile.models.Playtime;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class PurchaseActivity extends AppCompatActivity {

    private Context mContext;
    private Movie movie;

    private TextView title;
    private Spinner cinemaSpinner;
    private Spinner dateSpinner;
    private Spinner timeSpinner;
    private TextView ticketPrice;
    private TextView availableTickets;
    private EditText totalTickets;

    private ArrayList<Cinema> cinemas;
    private ArrayList<Playtime> playtimes;
    private ArrayList<Performance> performances;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        movie = bundle.getParcelable("movie");

        mContext = getApplicationContext();
        this.bindProperties();

        cinemas = new ArrayList<>();
        playtimes = new ArrayList<>();
        performances = new ArrayList<>();

        this.fetchCinemas();
        this.fetchPlaytimes();
        this.fetchPerformances();

        // choose cinema
        // choose date
        // choose time
        // calculate price
        // make yes no confirmation dialog
        // make http request
        // return to the tickets list activity
    }

    private void fetchCinemas() {
        String url = AppConfig.cinemaURL();

        Helper.MakeJsonObjectRequest(mContext, Request.Method.GET, url, null, new JsonResponseCallback() {
            @Override
            public void onError(VolleyError error) {
                Toast toast = Toast.makeText(mContext, "An error occurred", Toast.LENGTH_LONG);
                toast.show();
            }

            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray arr = response.getJSONArray("cinema");

                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject curr = arr.getJSONObject(i);

                        Cinema cinema = new Cinema();
                        cinema.setId(curr.getInt("id"));
                        cinema.setName(curr.getString("name"));
                        cinema.setSeat_capacity(curr.getInt("seat_capacity"));
                        cinemas.add(cinema);
                    }
                    ArrayAdapter<Cinema> cinemaAdapter =
                            new ArrayAdapter<Cinema>(mContext, android.R.layout.simple_spinner_item, cinemas);
                    cinemaSpinner.setAdapter(cinemaAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public HashMap<String, String> setHeaders() {
                return null;
            }
        });
    }

    private void fetchPlaytimes() {
        String url = AppConfig.playtimeURL(movie.getId());

        Helper.MakeJsonObjectRequest(mContext, Request.Method.GET, url, null, new JsonResponseCallback() {
            @Override
            public void onError(VolleyError error) {
                Toast toast = Toast.makeText(mContext, "An error occurred", Toast.LENGTH_LONG);
                toast.show();
            }

            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray arr = response.getJSONArray("playtime");

                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject curr = arr.getJSONObject(i);

                        Playtime playtime = new Playtime();
                        playtime.setId(curr.getInt("id"));
                        playtime.setMovieID(curr.getInt("movie_id"));

                        DateFormat sdf = new SimpleDateFormat("hh:mm:ss");
                        playtime.setTime(Time.valueOf(curr.getString("time")));
                        playtimes.add(playtime);
                    }
                    ArrayAdapter<Playtime> playtimeAdapter =
                            new ArrayAdapter<Playtime>(mContext, android.R.layout.simple_spinner_item, playtimes);
                    dateSpinner.setAdapter(playtimeAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public HashMap<String, String> setHeaders() {
                return null;
            }
        });
    }

    private void fetchPerformances() {
        String url = AppConfig.performanceURL(movie.getId());

        Helper.MakeJsonObjectRequest(mContext, Request.Method.GET, url, null, new JsonResponseCallback() {
            @Override
            public void onError(VolleyError error) {
                Toast toast = Toast.makeText(mContext, "Fetch performance error", Toast.LENGTH_LONG);
                toast.show();
//                NetworkResponse response = error.networkResponse;
//                try {
//                    JSONObject err = new JSONObject(new String(response.data));
//                    String errorBody = err.getString("error");
//
//                    Toast toast = Toast.makeText(mContext, errorBody, Toast.LENGTH_LONG);
//                    toast.show();
//                } catch(JSONException e) {
//                    e.printStackTrace();
//                }
            }

            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray arr = response.getJSONArray("performance");

                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject curr = arr.getJSONObject(i);

                        Performance performance = new Performance();
                        performance.setId(curr.getInt("id"));
                        performance.setPlaytimeID(curr.getInt("play_time_id"));
                        performance.setCinemaID(curr.getInt("cinema_id"));

                        SimpleDateFormat df = AppConfig.dateFormat();
                        Date date = df.parse(curr.getString("date"));
                        performance.setDate(date);

                        performance.setBookedSeat(curr.getInt("booked_seat"));
                        performance.setTotalSeat(curr.getInt("total_seat"));

                        performances.add(performance);
                    }
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

    private void bindProperties() {
        title = findViewById(R.id.purchaseTitleText);
        title.setText(movie.getTitle());

        cinemaSpinner = findViewById(R.id.cinemaSpinner);
        dateSpinner = findViewById(R.id.dateSpinner);
        timeSpinner = findViewById(R.id.timeSpinner);

        ticketPrice = findViewById(R.id.ticketPriceText);
        ticketPrice.setText(String.valueOf(movie.getTicketPrice()));

        availableTickets = findViewById(R.id.availableTicketsText);
        totalTickets = findViewById(R.id.totalTicketsText);
    }
}
