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

import com.example.cinema_mobile.helpers.Helper;

public class TicketActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);

        mContext = getApplicationContext();

        this.setupNavbar();
        this.loadTickets();
    }

    private void loadTickets() {

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
