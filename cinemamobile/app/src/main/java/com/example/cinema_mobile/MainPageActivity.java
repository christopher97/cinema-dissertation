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
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.example.cinema_mobile.helpers.AppConfig;
import com.example.cinema_mobile.helpers.Helper;
import com.example.cinema_mobile.helpers.NetworkResponseCallback;
import com.example.cinema_mobile.models.Movie;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class MainPageActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private Context mContext;

    private String TAG = "MainPageActivity";
    private ArrayList<Movie> movies = new ArrayList<Movie>();
    private ArrayList<Movie> recommendedMovies = new ArrayList<Movie>();

    // recommendedView Variables
    RecyclerView recommendedListView;
    RecyclerView.LayoutManager recommendedLayoutManager;
    MovieAdapter recommendedAdapter;

    // RecyclerView Variables
    RecyclerView playingListView;
    RecyclerView.LayoutManager playingLayoutManager;
    MovieAdapter playingAdapter;

    private String getToken() { return Helper.getToken(mContext); }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        mContext = getApplicationContext();

        this.checkPreference();
        this.handleRecyclerView();
        this.setupNavbar();
        this.loadMovies();
    }

    private void checkPreference() {
        boolean preference_set = Helper.getPref(mContext);
        if (!preference_set) {
            Intent intent = new Intent(MainPageActivity.this, PreferenceActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void setRecyclerData() {
        if (!movies.isEmpty()) {
            playingAdapter.notifyDataSetChanged();
            recommendedAdapter.notifyDataSetChanged();
        }
    }

    private void loadMovies() {
        String token = Helper.getToken(mContext);
        String url = AppConfig.recommendedURL(token);
        Helper.MakeNetworkResponseRequest(mContext, Request.Method.GET, url, null, new NetworkResponseCallback() {
            @Override
            public void onError(VolleyError error) {

                NetworkResponse response = error.networkResponse;
                try {
                    JSONObject err = new JSONObject(new String(response.data));
                    String errorBody = err.getString("error");

                    Toast toast = Toast.makeText(MainPageActivity.this, errorBody, Toast.LENGTH_LONG);
                    toast.show();
                } catch(JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onResponse(NetworkResponse response) {
                // if there is no result, no need to process JSON
                if (response.statusCode == AppConfig.NO_CONTENT) {
                    Toast toast = Toast.makeText(mContext, "No movie at the moment", Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    JSONObject json = Helper.parseJSON(response);
                    try {
                        JSONArray moviesArr = json.getJSONArray("movies");
                        for(int i=0; i<moviesArr.length(); i++) {
                            JSONObject curr = moviesArr.getJSONObject(i);
                            Movie movie = setMovieObject(curr);

                            if (movie != null) {
                                movies.add(movie);

                                boolean recommended = curr.getBoolean("recommended");
                                if (recommended) {
                                    recommendedMovies.add(movie);
                                }
                            }
                        }

                        setRecyclerData();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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

    private Movie setMovieObject(JSONObject curr) {
        try {
            Movie movie = new Movie();
            movie.setId(curr.getInt("id"));
            movie.setTitle(curr.getString("title"));
            movie.setRunningTime(curr.getInt("running_time"));
            movie.setLanguage(curr.getString("language"));
            movie.setReleaseDate(
                    AppConfig.dateFormat().parse(curr.getString("release_date")));
            movie.setTicketPrice(curr.getInt("ticket_price"));
            movie.setSynopsis(curr.getString("synopsis"));
            movie.setImageURL(curr.getString("image_url"));
            movie.setDistributor(curr.getString("distributor"));
            movie.setStartDate(
                    AppConfig.dateFormat().parse(curr.getString("start_date")));
            movie.setEndDate(
                    AppConfig.dateFormat().parse(curr.getString("end_date")));

            JSONObject rating = curr.getJSONObject("rating");
            movie.setRating(rating.getString("description"));

            ArrayList<String> casts = this.jsonArrayToString(curr.getJSONArray("casts"));
            movie.setCasts(casts);

            ArrayList<String> genres = this.jsonArrayToString(curr.getJSONArray("genres"));
            movie.setGenres(genres);

            ArrayList<String> directors = this.jsonArrayToString(curr.getJSONArray("directors"));
            movie.setDirectors(directors);

            return movie;
        } catch (JSONException e) {
            e.printStackTrace();
        }
         catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private ArrayList<String> jsonArrayToString(JSONArray jsArr) {
        ArrayList<String> arr = new ArrayList<>();
        for(int i = 0; i < jsArr.length(); i++) {
            try {
                JSONObject curr = jsArr.getJSONObject(i);
                arr.add(curr.getString("name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return arr;
    }

    private void handleRecyclerView() {
        // handle recyclerView
        playingListView = findViewById(R.id.playingList);
        recommendedListView = findViewById(R.id.recommendedList);

        playingLayoutManager = new LinearLayoutManager(this);
        playingListView.setLayoutManager(playingLayoutManager);
        
        recommendedLayoutManager = new LinearLayoutManager(this);
        recommendedListView.setLayoutManager(recommendedLayoutManager);

        // set playingAdapter
        playingAdapter = new MovieAdapter(movies);
        playingAdapter.setOnClick(new MovieAdapter.OnItemClicked() {
            @Override
            public void onItemClick(int position) {
                Movie movie = movies.get(position);
                Intent intent = new Intent(mContext, MovieDetailActivity.class);
                intent.putExtra("movie", movie);
                mContext.startActivity(intent);
            }
        });
        playingListView.setAdapter(playingAdapter);
        
        // set recommendedAdapter
        recommendedAdapter = new MovieAdapter(recommendedMovies);
        recommendedAdapter.setOnClick(new MovieAdapter.OnItemClicked() {
            @Override
            public void onItemClick(int position) {
                Movie movie = movies.get(position);
                Intent intent = new Intent(mContext, MovieDetailActivity.class);
                intent.putExtra("movie", movie);
                mContext.startActivity(intent);
            }
        });
        recommendedListView.setAdapter(recommendedAdapter);
    }

    private void setupNavbar() {
        mDrawerLayout = findViewById(R.id.main_page_drawer);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        Helper.onNavigationItemSelected(mContext, menuItem, mDrawerLayout);
                        return true;
                    }
                }
        );

        Toolbar toolbar = findViewById(R.id.main_page_toolbar);
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
