package com.example.cinema_mobile;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
import com.example.cinema_mobile.helpers.NetworkResponseCallback;
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
import java.util.Calendar;
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
    private ArrayList<String> dates;

    private Cinema selectedCinema;
    private Playtime selectedPlaytime;
    private String selectedDate;
    private int total;

    private AlertDialog.Builder purchaseAlertBuilder;
    private String confirmationMessage = "Ticket Purchase Confirmation";
    private Performance performance;

    private String getToken() { return Helper.getToken(mContext); }

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
        dates = new ArrayList<>();

        selectedCinema = null;
        selectedPlaytime = null;
        selectedDate = null;

        this.fetchCinemas();
        this.fetchPlaytimes();
        this.generateDates();

        this.setSpinnerListener();
        this.createAlertDialog();

        // make http request
        // return to the tickets list activity
    }

    public void onPurchaseButtonClick(View view) {
        String temp = totalTickets.getText().toString();
        if (temp.equals("") || selectedDate.equals("")) {
            Toast toast = Toast.makeText(mContext, "Please fill all of the fields!", Toast.LENGTH_LONG);
            toast.show();
            return;
        }

        total = Integer.parseInt(temp);
        purchaseAlertBuilder.setMessage(total + " ticket(s) - RM " + (movie.getTicketPrice()*total));
        purchaseAlertBuilder.show();
    }

    private void generateDates() {
        Calendar cal = Calendar.getInstance();

        dates.add("");
        while (!cal.getTime().after(movie.getEndDate())) {
            String date = AppConfig.spinnerDateFormat().format(cal.getTime());
            dates.add(date);
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }
        dates.add(AppConfig.spinnerDateFormat().format(cal.getTime()));   // adds the last date

        ArrayAdapter<String> dateAdatper =
                new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, dates);
        dateSpinner.setAdapter(dateAdatper);
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
                    timeSpinner.setAdapter(playtimeAdapter);
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

    private void bindProperties() {
        title = findViewById(R.id.purchaseTitleText);
        title.setText(movie.getTitle());

        cinemaSpinner = findViewById(R.id.cinemaSpinner);
        dateSpinner = findViewById(R.id.dateSpinner);
        timeSpinner = findViewById(R.id.timeSpinner);

        String priceStr = String.valueOf(movie.getTicketPrice()) + " RM";
        ticketPrice = findViewById(R.id.ticketPriceText);
        ticketPrice.setText(priceStr);

        availableTickets = findViewById(R.id.availableTicketsText);
        totalTickets = findViewById(R.id.totalTicketsText);
    }

    private void setSpinnerListener() {
        cinemaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCinema = cinemas.get(position);
                checkTicketAvailability();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        timeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedPlaytime = playtimes.get(position);
                checkTicketAvailability();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        dateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String curr = dates.get(position);
                if (curr.equals("")) return;
                try {
                    Date date = AppConfig.spinnerDateFormat().parse(curr);
                    selectedDate = AppConfig.dateFormat().format(date);

                    System.out.println(selectedDate);
                    checkTicketAvailability();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void checkTicketAvailability() {
        if (!(selectedCinema == null || selectedDate == null || selectedPlaytime == null)) {
            String url = AppConfig.availabilityURL();

            JSONObject body = new JSONObject();
            try {
                body.put("play_time_id", selectedPlaytime.getId());
                body.put("cinema_id", selectedCinema.getId());
                body.put("date", selectedDate);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Helper.MakeNetworkResponseRequest(mContext, Request.Method.POST, url, body, new NetworkResponseCallback() {
                @Override
                public void onError(VolleyError error) {
                    try {
                        NetworkResponse response = error.networkResponse;

                        JSONObject err = new JSONObject(new String(response.data));
                        String errorBody = err.getString("error");

                        Toast toast = Toast.makeText(mContext, errorBody, Toast.LENGTH_LONG);
                        toast.show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onResponse(NetworkResponse response) {
                    System.out.println(response);
                    try {
                        JSONObject json = Helper.parseJSON(response);
                        System.out.println(json);
                        JSONObject obj = json.getJSONObject("performance");

                        performance = new Performance();
                        performance.setId(obj.getInt("id"));
                        performance.setPlaytimeID(obj.getInt("play_time_id"));
                        performance.setCinemaID(obj.getInt("cinema_id"));
                        performance.setDate(AppConfig.dateFormat().parse(obj.getString("date")));
                        performance.setTotalSeat(obj.getInt("total_seat"));
                        performance.setBookedSeat(obj.getInt("booked_seat"));

                        int avTicket = performance.getAvailableSeat();
                        availableTickets.setText(String.valueOf(avTicket));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public HashMap<String, String> setHeaders() {
                    HashMap<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json");

                    return headers;
                }
            });
        }
    }

    private void createAlertDialog() {
        purchaseAlertBuilder = new AlertDialog.Builder(this);

        purchaseAlertBuilder.setTitle(this.confirmationMessage);
        purchaseAlertBuilder.setMessage("");

        purchaseAlertBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                checkout();
            }
        });

        purchaseAlertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // empty, only close the dialog
            }
        });
    }

    private void checkout() {
        String token = getToken();
        String url = AppConfig.purchaseURL(token);

        Toast toast = Toast.makeText(mContext, "Processing your purchase...", Toast.LENGTH_LONG);
        toast.show();

        JSONObject body = new JSONObject();
        try {
            body.put("performance_id", performance.getId());
            body.put("qty", total);

            Helper.MakeJsonObjectRequest(mContext, Request.Method.POST, url, body, new JsonResponseCallback() {
                @Override
                public void onError(VolleyError error) {
                    try {
                        NetworkResponse response = error.networkResponse;

                        JSONObject err = new JSONObject(new String(response.data));
                        String errorBody = err.getString("error");

                        Toast toast = Toast.makeText(mContext, errorBody, Toast.LENGTH_LONG);
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

                        Intent intent = new Intent(mContext, TicketActivity.class);
                        startActivity(intent);
                        finish();
                    } catch (JSONException e) {
                        e.printStackTrace();
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
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
