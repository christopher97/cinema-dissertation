package com.example.cinema_mobile;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.example.cinema_mobile.helpers.AppConfig;
import com.example.cinema_mobile.helpers.Helper;
import com.example.cinema_mobile.helpers.JsonResponseCallback;
import com.example.cinema_mobile.models.Actor;
import com.example.cinema_mobile.models.Director;
import com.example.cinema_mobile.models.Genre;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class PreferenceActivity extends AppCompatActivity {

    private Context mContext;

    private ConstraintLayout actor_pref_const;
    private ConstraintLayout genre_pref_const;
    private ConstraintLayout director_pref_const;

    private DisplayMetrics displayMetrics;

    private ArrayList<Actor> actors;        // list of actors
    private ArrayList<Spinner> actorSpinners;    // list of actorSpinners
    private TextView actorAddBtn;
    private TextView actorSubBtn;

    private ArrayList<Genre> genres;        // list of genres
    private ArrayList<Spinner> genreSpinners;   // list of genreSpinners
    private TextView genreAddBtn;
    private TextView genreSubBtn;

    private ArrayList<Director> directors;        // list of genres
    private ArrayList<Spinner> directorSpinners;   // list of genreSpinners
    private TextView directorAddBtn;
    private TextView directorSubBtn;

    private String getToken() { return Helper.getToken(mContext); }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);

        this.mContext = getApplicationContext();
        displayMetrics = getResources().getDisplayMetrics();

        actorSpinners = new ArrayList<>();
        actors = new ArrayList<>();

        genreSpinners = new ArrayList<>();
        genres = new ArrayList<>();

        directorSpinners = new ArrayList<>();
        directors = new ArrayList<>();

        bindElements();
        fetchActors();
    }

    private void bindElements() {
        this.actor_pref_const = findViewById(R.id.actor_preference_constraint);
        this.actorAddBtn = findViewById(R.id.actor_add_btn);
        this.actorSubBtn = findViewById(R.id.actor_sub_btn);

        this.genre_pref_const = findViewById(R.id.genre_preference_constraint);
        this.genreAddBtn = findViewById(R.id.genre_add_btn);
        this.genreSubBtn = findViewById(R.id.genre_sub_btn);

        this.director_pref_const = findViewById(R.id.director_preference_constraint);
        this.directorAddBtn = findViewById(R.id.director_add_btn);
        this.directorSubBtn = findViewById(R.id.director_sub_btn);
    }

    private void setupListener() {
        actorAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // create new spinner
                ArrayAdapter<Actor> adapter =
                        new ArrayAdapter<Actor>(mContext, android.R.layout.simple_spinner_item, actors);
                Spinner spinner = setSpinner(adapter);

                // add the new spinner to the layout
                ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(
                        ConstraintLayout.LayoutParams.MATCH_CONSTRAINT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
                spinner.setLayoutParams(params);
                actor_pref_const.addView(spinner);

                // create constraint for the new spinner
                Spinner lastSpinner = actorSpinners.get(actorSpinners.size()-1);
                setConstraint(spinner, lastSpinner, actor_pref_const, actorAddBtn, actorSubBtn);
                actorSpinners.add(spinner);
            }
        });

        actorSubBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (actorSpinners.size() > 1) {
                    Spinner newLastSpinner = actorSpinners.get(actorSpinners.size()-2);
                    removeSpinner(actorSubBtn, actorAddBtn, actor_pref_const, newLastSpinner);

                    Spinner lastSpinner = actorSpinners.get(actorSpinners.size()-1);
                    actor_pref_const.removeView((View) lastSpinner);
                    actorSpinners.remove(actorSpinners.size()-1);
                }
            }
        });

        genreAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // create new spinner
                ArrayAdapter<Genre> adapter =
                        new ArrayAdapter<Genre>(mContext, android.R.layout.simple_spinner_item, genres);
                Spinner spinner = setSpinner(adapter);

                // add the new spinner to the layout
                ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(
                        ConstraintLayout.LayoutParams.MATCH_CONSTRAINT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
                spinner.setLayoutParams(params);
                genre_pref_const.addView(spinner);

                // create constraint for the new spinner
                Spinner lastSpinner = genreSpinners.get(genreSpinners.size()-1);
                setConstraint(spinner, lastSpinner, genre_pref_const, genreAddBtn, genreSubBtn);
                genreSpinners.add(spinner);
            }
        });

        genreSubBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (genreSpinners.size() > 1) {
                    Spinner newLastSpinner = genreSpinners.get(genreSpinners.size()-2);
                    removeSpinner(genreSubBtn, genreAddBtn, genre_pref_const, newLastSpinner);

                    Spinner lastSpinner = genreSpinners.get(genreSpinners.size()-1);
                    genre_pref_const.removeView((View) lastSpinner);
                    genreSpinners.remove(genreSpinners.size()-1);
                }
            }
        });

        directorAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // create new spinner
                ArrayAdapter<Director> adapter =
                        new ArrayAdapter<Director>(mContext, android.R.layout.simple_spinner_item, directors);
                Spinner spinner = setSpinner(adapter);

                // add the new spinner to the layout
                ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(
                        ConstraintLayout.LayoutParams.MATCH_CONSTRAINT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
                spinner.setLayoutParams(params);
                director_pref_const.addView(spinner);

                // create constraint for the new spinner
                Spinner lastSpinner = directorSpinners.get(directorSpinners.size()-1);
                setConstraint(spinner, lastSpinner, director_pref_const, directorAddBtn, directorSubBtn);
                directorSpinners.add(spinner);
            }
        });

        directorSubBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (directorSpinners.size() > 1) {
                    Spinner newLastSpinner = directorSpinners.get(directorSpinners.size()-2);
                    removeSpinner(directorSubBtn, directorAddBtn, director_pref_const, newLastSpinner);

                    Spinner lastSpinner = directorSpinners.get(directorSpinners.size()-1);
                    director_pref_const.removeView((View) lastSpinner);
                    directorSpinners.remove(directorSpinners.size()-1);
                }
            }
        });
    }

    private void removeSpinner(TextView subBtn, TextView addBtn, ConstraintLayout clayout, Spinner spinner) {
        int margin = Helper.dpToPixel(16, displayMetrics);

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(clayout);

        constraintSet.connect(addBtn.getId(), ConstraintSet.TOP, spinner.getId(), ConstraintSet.BOTTOM, margin);
        constraintSet.connect(subBtn.getId(), ConstraintSet.TOP, spinner.getId(), ConstraintSet.BOTTOM, margin);

        constraintSet.applyTo(clayout);
    }

    private Spinner setSpinner(ArrayAdapter adapter) {
        Spinner spinner = new Spinner(mContext);

        spinner.setAdapter(adapter);
        spinner.setId(View.generateViewId());
        spinner.setBackground(getDrawable(R.drawable.spinner_bg));

        return spinner;
    }

    private void setConstraint(Spinner spinner, Spinner lastSpinner, ConstraintLayout clayout,
                               TextView addBtn, TextView subBtn) {
        int margin = Helper.dpToPixel(16, displayMetrics);

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(clayout);

        constraintSet.connect(spinner.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, margin);
        constraintSet.connect(spinner.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, margin);
        constraintSet.connect(spinner.getId(), ConstraintSet.TOP, lastSpinner.getId(), ConstraintSet.BOTTOM, margin);

        constraintSet.constrainHeight(spinner.getId(), ConstraintSet.WRAP_CONTENT);
        constraintSet.constrainWidth(spinner.getId(), ConstraintSet.MATCH_CONSTRAINT);

        // create constraint for the button
        constraintSet.clear(addBtn.getId(), ConstraintSet.TOP);
        constraintSet.connect(addBtn.getId(), ConstraintSet.TOP, spinner.getId(), ConstraintSet.BOTTOM, margin);
        constraintSet.connect(subBtn.getId(), ConstraintSet.TOP, spinner.getId(), ConstraintSet.BOTTOM, margin);

        constraintSet.applyTo(clayout);
    }

    private void fetchActors() {
        String url = AppConfig.actorsURL();
        Helper.MakeJsonObjectRequest(mContext, Request.Method.GET, url, null, new JsonResponseCallback() {
            @Override
            public void onError(VolleyError error) {

                NetworkResponse response = error.networkResponse;
                try {
                    JSONObject err = new JSONObject(new String(response.data));
                    String errorBody = err.getString("error");

                    Toast toast = Toast.makeText(PreferenceActivity.this, errorBody, Toast.LENGTH_LONG);
                    toast.show();
                } catch(JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray json = response.getJSONArray("actor");

                    Actor actor = new Actor(0, "");
                    actors.add(actor);
                    for(int i=0; i<json.length(); i++) {
                        JSONObject curr = json.getJSONObject(i);

                        int id = curr.getInt("id");
                        String name = curr.getString("name");
                        actor = new Actor(id, name);

                        actors.add(actor);
                    }
                    ArrayAdapter<Actor> adapter =
                            new ArrayAdapter<Actor>(mContext, android.R.layout.simple_spinner_item, actors);

                    Spinner spinner = findViewById(R.id.actor_spinner_1);
                    spinner.setAdapter(adapter);
                    actorSpinners.add(spinner);

                    fetchGenres();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast toast = Toast.makeText(mContext, "Failed fetching data", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }

            @Override
            public HashMap<String, String> setHeaders() {
                return null;
            }
        });
    }

    private void fetchGenres() {
        String url = AppConfig.genresURL();
        Helper.MakeJsonObjectRequest(mContext, Request.Method.GET, url, null, new JsonResponseCallback() {
            @Override
            public void onError(VolleyError error) {

                NetworkResponse response = error.networkResponse;
                try {
                    JSONObject err = new JSONObject(new String(response.data));
                    String errorBody = err.getString("error");

                    Toast toast = Toast.makeText(PreferenceActivity.this, errorBody, Toast.LENGTH_LONG);
                    toast.show();
                } catch(JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray json = response.getJSONArray("genre");

                    Genre genre = new Genre(0, "");
                    genres.add(genre);
                    for(int i=0; i<json.length(); i++) {
                        JSONObject curr = json.getJSONObject(i);

                        int id = curr.getInt("id");
                        String name = curr.getString("name");
                        genre = new Genre(id, name);

                        genres.add(genre);
                    }
                    ArrayAdapter<Genre> adapter =
                            new ArrayAdapter<Genre>(mContext, android.R.layout.simple_spinner_item, genres);

                    Spinner spinner = findViewById(R.id.genre_spinner_1);
                    spinner.setAdapter(adapter);
                    genreSpinners.add(spinner);

                    fetchDirectors();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast toast = Toast.makeText(mContext, "Failed fetching data", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }

            @Override
            public HashMap<String, String> setHeaders() {
                return null;
            }
        });
    }

    private void fetchDirectors() {
        String url = AppConfig.directorsURL();
        Helper.MakeJsonObjectRequest(mContext, Request.Method.GET, url, null, new JsonResponseCallback() {
            @Override
            public void onError(VolleyError error) {

                NetworkResponse response = error.networkResponse;
                try {
                    JSONObject err = new JSONObject(new String(response.data));
                    String errorBody = err.getString("error");

                    Toast toast = Toast.makeText(PreferenceActivity.this, errorBody, Toast.LENGTH_LONG);
                    toast.show();
                } catch(JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray json = response.getJSONArray("director");

                    Director director = new Director(0, "");
                    directors.add(director);
                    for(int i=0; i<json.length(); i++) {
                        JSONObject curr = json.getJSONObject(i);

                        int id = curr.getInt("id");
                        String name = curr.getString("name");
                        director = new Director(id, name);

                        directors.add(director);
                    }
                    ArrayAdapter<Director> adapter =
                            new ArrayAdapter<Director>(mContext, android.R.layout.simple_spinner_item, directors);

                    Spinner spinner = findViewById(R.id.director_spinner_1);
                    spinner.setAdapter(adapter);
                    directorSpinners.add(spinner);

                    setupListener();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast toast = Toast.makeText(mContext, "Failed fetching data", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }

            @Override
            public HashMap<String, String> setHeaders() {
                return null;
            }
        });
    }

    public void submitPreferences(View view) {
        JSONObject body = new JSONObject();
        try {
            JSONArray actorArr = Helper.spinnerToJSONArray(actorSpinners);
            JSONArray genreArr = Helper.spinnerToJSONArray(genreSpinners);
            JSONArray dirArr = Helper.spinnerToJSONArray(directorSpinners);

            body.put("actors", actorArr);
            body.put("genres", genreArr);
            body.put("directors", dirArr);

            String token = Helper.getToken(mContext);
            String url = AppConfig.preferenceURL(token);

            Helper.MakeJsonObjectRequest(mContext, Request.Method.POST, url, body, new JsonResponseCallback() {
                @Override
                public void onError(VolleyError error) {
                    NetworkResponse response = error.networkResponse;
                    try {
                        JSONObject err = new JSONObject(new String(response.data));
                        String errorBody = err.getString("error");

                        Toast toast = Toast.makeText(PreferenceActivity.this, errorBody, Toast.LENGTH_LONG);
                        toast.show();
                    } catch(JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onResponse(JSONObject response) {
                    Toast toast = Toast.makeText(PreferenceActivity.this,
                            "Your preference has been saved!", Toast.LENGTH_LONG);
                    toast.show();

                    Helper.setPref(mContext, true);
                    Intent intent = new Intent(mContext, MainPageActivity.class);
                    mContext.startActivity(intent);
                    finish();
                }

                @Override
                public HashMap<String, String> setHeaders() {
                    String token = getToken();

                    HashMap<String, String> headers = new HashMap<>();
                    headers.put("Authorization", "Bearer " + token);
                    return headers;
                }
            });
            System.out.println(body);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
