/*
 * Copyright (c) 2016. iUlling Corp.
 * Created By Kil-Ho Choi
 */

package com.ulling.firebasetest;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.ulling.firebasetest.config.ConfigActivity;
import com.ulling.firebasetest.dynamiclink.DeepLinkActivity;
import com.ulling.firebasetest.dynamiclink.DynamicLinkActivity;
import com.ulling.firebasetest.fcm.FcmMainActivity;

public class MainActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener {
  public static final String DEEP_LINK = "/test/mycolor";
  public static final String DYNAMIC_LINK = "/test2/goodcolor";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
            .setAction("Action", null).show();
      }
    });
    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
        this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    drawer.setDrawerListener(toggle);
    toggle.syncState();
    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
    navigationView.setNavigationItemSelectedListener(this);
    // 오류 생성 후 보내기
//        FirebaseCrash.report(new Exception("My first Android non-fatal error"));
//        try {
//            // Some code here...
//        } catch (Exception e) {
//            // Don't know what to do with this...
//            FirebaseCrash.log("Caught an unexpected exception while doing some work");
//            FirebaseCrash.report(e);
//        }
//        Intent intent = getIntent();
//        if (intent == null || intent.getData() == null) {
//            finish();
//        }
//        openDeepLink(intent.getData());
  }

  private void openDeepLink(Uri deepLink) {
    String path = deepLink.getPath();
    if (DEEP_LINK.equals(path)) {
      startActivity(new Intent(this, DeepLinkActivity.class));
    } else if (DYNAMIC_LINK.equals(path)) {
      startActivity(new Intent(this, DynamicLinkActivity.class));
    } else {
      // Fall back to the main activity
    }
  }

  @Override
  public void onBackPressed() {
    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    if (drawer.isDrawerOpen(GravityCompat.START)) {
      drawer.closeDrawer(GravityCompat.START);
    } else {
      super.onBackPressed();
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();
    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @SuppressWarnings("StatementWithEmptyBody")
  @Override
  public boolean onNavigationItemSelected(MenuItem item) {
    // Handle navigation view item clicks here.
    int id = item.getItemId();
    if (id == R.id.nav_camera) {
      Intent intent = new Intent(this, FcmMainActivity.class);
      startActivity(intent);
    } else if (id == R.id.nav_gallery) {
      Intent intent = new Intent(this, ConfigActivity.class);
      startActivity(intent);
    } else if (id == R.id.nav_slideshow) {
    } else if (id == R.id.nav_manage) {
    } else if (id == R.id.nav_share) {
    } else if (id == R.id.nav_send) {
    }
    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    drawer.closeDrawer(GravityCompat.START);
    return true;
  }
}
