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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cinema_mobile.helpers.AppConfig;
import com.example.cinema_mobile.helpers.Helper;
import com.example.cinema_mobile.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieDetailActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private Movie movie;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        mContext = getApplicationContext();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        movie = bundle.getParcelable("movie");

        this.setupNavbar();
        this.setMovieDetails();
    }

    private void setMovieDetails() {
        ImageView moviePoster = findViewById(R.id.movieDetailPoster);
        TextView titleText = findViewById(R.id.detailTitleText);
        TextView runningTimeText = findViewById(R.id.detailRunningTimeText);
        TextView languageText = findViewById(R.id.detailLanguageText);
        TextView releaseDateText = findViewById(R.id.detailReleaseText);
        TextView synopsisText = findViewById(R.id.detailSynopsisText);
        TextView distributorText = findViewById(R.id.detailDistributorText);
        TextView ratingText = findViewById(R.id.detailCensorText);
        TextView castsText = findViewById(R.id.detailCastText);
        TextView genresText = findViewById(R.id.detailGenreText);
        TextView directorsText = findViewById(R.id.detailDirectorText);

        Picasso.get().load(movie.getImageURL()).into(moviePoster);
        titleText.setText(movie.getTitle());

        String runningTime = movie.getRunningTime() + " minutes";
        runningTimeText.setText(runningTime);

        languageText.setText(movie.getLanguage());
        releaseDateText.setText(AppConfig.dateFormat().format(movie.getReleaseDate()));
        synopsisText.setText(movie.getSynopsis());
        distributorText.setText(movie.getDistributor());
        ratingText.setText(movie.getRating());

        String casts = this.separateWithCommas(movie.getCasts());
        castsText.setText(casts);

        String genres = this.separateWithCommas(movie.getGenres());
        genresText.setText(genres);

        String directors = this.separateWithCommas(movie.getDirectors());
        directorsText.setText(directors);
    }

    public void purchaseTicket(View view) {
        Intent intent = new Intent(mContext, PurchaseActivity.class);
        intent.putExtra("movie", movie);
        startActivity(intent);
    }

    private String separateWithCommas(ArrayList<String> items) {
        String result = "";
        result += items.get(0);
        for(int i = 1; i < items.size(); i++) {
            result += ", " + items.get(i);
        }
        return result;
    }

    private void setupNavbar() {
        mDrawerLayout = findViewById(R.id.movie_detail_drawer);

        NavigationView navigationView = findViewById(R.id.movie_detail_nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        Helper.onNavigationItemSelected(mContext, menuItem, mDrawerLayout);
                        return true;
                    }
                }
        );

        Toolbar toolbar = findViewById(R.id.movie_detail_toolbar);
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
