package movies.compubase.com.moviess.ui.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.IOException;

import movies.compubase.com.moviess.R;
import movies.compubase.com.moviess.helper.ProfilePicture;
import movies.compubase.com.moviess.ui.fragments.CinemaHallsFragment;
import movies.compubase.com.moviess.ui.fragments.ContactUsFragment;
import movies.compubase.com.moviess.ui.fragments.GiftFragment;
import movies.compubase.com.moviess.ui.fragments.HomeFragment;
import movies.compubase.com.moviess.ui.fragments.MoviesFragment;
import movies.compubase.com.moviess.ui.fragments.MyRateFragment;
import movies.compubase.com.moviess.ui.fragments.RecommendationFragment;
import movies.compubase.com.moviess.ui.fragments.TvFragment;
import movies.compubase.com.moviess.ui.fragments.WatchListFragment;

import static movies.compubase.com.moviess.R.drawable.bg_navigation;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int gallery_pick =1;
    private ImageView imageView;
    private int bg = R.drawable.bg_navigation;
    private Toolbar toolbar,search_toolbar;
    private RelativeLayout relativeLayout;
    private AppBarLayout appBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        relativeLayout = findViewById(R.id.parent_search);
        search_toolbar = findViewById(R.id.home_activity_toolbar);
        appBarLayout = findViewById(R.id.home_activity_appbar);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);

        imageView = findViewById(R.id.imageView);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        drawer.setBackgroundResource(bg);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        HomeFragment homeFragment = new HomeFragment();
        displaySelectedFragment(homeFragment);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

            HomeFragment homeFragment = new HomeFragment();
            displaySelectedFragment(homeFragment);

            appBarLayout.setVisibility(View.VISIBLE);
            // Handle the camera action
        } else if (id == R.id.nav_watchlist) {

            WatchListFragment watchListFragment = new WatchListFragment();
            displaySelectedFragment(watchListFragment);

            appBarLayout.setVisibility(View.VISIBLE);


        } else if (id == R.id.nav_recommendation) {

            RecommendationFragment recommendationFragment = new RecommendationFragment();
            displaySelectedFragment(recommendationFragment);

            appBarLayout.setVisibility(View.VISIBLE);


        } else if (id == R.id.nav_movies) {

            MoviesFragment moviesFragment = new MoviesFragment();
            displaySelectedFragment(moviesFragment);

            appBarLayout.setVisibility(View.VISIBLE);

        } else if (id == R.id.nav_tv) {

            TvFragment tvFragment = new TvFragment();
            displaySelectedFragment(tvFragment);

            appBarLayout.setVisibility(View.VISIBLE);

        } else if (id == R.id.nav_mayrate) {

            MyRateFragment myRateFragment = new MyRateFragment();
            displaySelectedFragment(myRateFragment);

            appBarLayout.setVisibility(View.VISIBLE);

        }else if (id == R.id.cinema){
            CinemaHallsFragment cinemaHallsFragment = new CinemaHallsFragment();
            displaySelectedFragment(cinemaHallsFragment);

        }else if (id == R.id.gift){
            GiftFragment giftFragment = new GiftFragment();
            displaySelectedFragment(giftFragment);

        }else if (id == R.id.logout){

        } else if (id == R.id.nav_contact){

            ContactUsFragment contactUsFragment = new ContactUsFragment();
            displaySelectedFragment(contactUsFragment);

            appBarLayout.setVisibility(View.GONE);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void displaySelectedFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.addToBackStack(null).commit();
    }

    public void settingstxt(View view) {
        startActivity(new Intent(HomeActivity.this,settingsActivity.class));
    }

    public void openGallery(View view) {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),gallery_pick);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ( resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();





            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Bitmap resized = Bitmap.createScaledBitmap(bitmap, 100, 100, true);
            imageView.setImageBitmap(ProfilePicture.getRoundedRectBitmap(resized));



        }
    }

    }
