package movies.compubase.com.moviess.ui.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.Locale;
import java.util.Objects;

import io.paperdb.Paper;
import movies.compubase.com.moviess.R;
import movies.compubase.com.moviess.helper.LocalHelper;
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

    private ImageView imageView;
    private int bg = R.drawable.bg_navigation;
    private Toolbar toolbar, search_toolbar;
    private RelativeLayout relativeLayout;
    private AppBarLayout appBarLayout;
    private SharedPreferences preferences;
    private String id;
    private String username;
    private String image;
    private TextView textView,welcome,settings;
    private String language;
    private String home;
    private NavigationView navigationView;
    private MenuItem item_home,item_reco,item_contact,item_myRate,item_moviies,item_watchList,item_tv,item_cinema,item_gift,item_logout;
    private Menu menu;
    private EditText search;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocalHelper.onAttach(newBase, "en"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        navigationView = (NavigationView) findViewById(R.id.nav_view);

        menu = navigationView.getMenu();
        item_home = menu.findItem(R.id.nav_home);
        item_reco = menu.findItem(R.id.nav_recommendation);
        item_contact = menu.findItem(R.id.nav_contact);
        item_myRate = menu.findItem(R.id.nav_mayrate);
        item_moviies = menu.findItem(R.id.nav_movies);
        item_watchList = menu.findItem(R.id.nav_watchlist);
        item_tv = menu.findItem(R.id.nav_tv);
        item_cinema = menu.findItem(R.id.cinema);
        item_gift = menu.findItem(R.id.gift);
        item_logout = menu.findItem(R.id.logout);


        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);

        imageView = header.findViewById(R.id.imageView_header);
        textView = header.findViewById(R.id.name_value_header);
        welcome = header.findViewById(R.id.welcome_txt);
        settings = header.findViewById(R.id.txt_settings);


        preferences = getSharedPreferences("user", MODE_PRIVATE);
        id = preferences.getString("id", "");
        username = preferences.getString("username", "");
        image = preferences.getString("image", "");
        language = preferences.getString("lan", "");

        search = findViewById(R.id.search);

        Paper.init(this);

        language = Paper.book().read("language");
        if (language == null) {
            Paper.book().write("language", "en");
        }
//        else {
//            Paper.book().write("language", "ar");
//            updateView((String) Paper.book().read("language"));
//          }

        relativeLayout = findViewById(R.id.parent_search);
        search_toolbar = findViewById(R.id.home_activity_toolbar);
        appBarLayout = findViewById(R.id.home_activity_appbar);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(null);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        drawer.setBackgroundResource(bg);
        toggle.syncState();

        Glide.with(this).load(image).placeholder(R.drawable.user_defualt_img).into(imageView);
        textView.setText(username);


        HomeFragment homeFragment = new HomeFragment();
        displaySelectedFragment(homeFragment);
    }

    private void updateView(String language) {

        navigationView.getMenu().clear();
        navigationView.inflateMenu(R.menu.activity_home_drawer);

        Context context = LocalHelper.setLocale(this, language);
        Resources resources = context.getResources();

        search.setHint(resources.getString(R.string.search));

        item_home.setTitle(resources.getString(R.string.home));
        item_cinema.setTitle(resources.getString(R.string.cinema_halls));
        item_contact.setTitle(resources.getString(R.string.contact_us));
        item_gift.setTitle(resources.getString(R.string.competitions_and_awards));
        item_reco.setTitle(resources.getString(R.string.recommendation));
        item_moviies.setTitle(resources.getString(R.string.movies));
        item_tv.setTitle(resources.getString(R.string.tv));
        item_myRate.setTitle(resources.getString(R.string.my_rates));
        item_watchList.setTitle(resources.getString(R.string.watchlist));
        item_logout.setTitle(resources.getString(R.string.logout));

        String string = resources.getString(R.string.welcome);
        String string1 = resources.getString(R.string.settings);

        welcome.setText(string);
        settings.setText(string1);

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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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

            appBarLayout.setVisibility(View.GONE);


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

            appBarLayout.setVisibility(View.GONE);

        } else if (id == R.id.cinema) {
            CinemaHallsFragment cinemaHallsFragment = new CinemaHallsFragment();
            displaySelectedFragment(cinemaHallsFragment);

            appBarLayout.setVisibility(View.GONE);

        } else if (id == R.id.gift) {
            GiftFragment giftFragment = new GiftFragment();
            displaySelectedFragment(giftFragment);

            appBarLayout.setVisibility(View.GONE);

        } else if (id == R.id.logout) {

            SharedPreferences preferences = getSharedPreferences("user", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.apply();

            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            finish();

        } else if (id == R.id.nav_contact) {

            ContactUsFragment contactUsFragment = new ContactUsFragment();
            displaySelectedFragment(contactUsFragment);

            appBarLayout.setVisibility(View.GONE);
        }else if (id == R.id.layout_arabic){
            Paper.book().write("language","ar");
        }else if (id == R.id.layout_english){
            Paper.book().write("language","en");

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
        if (id.isEmpty()) {
            Toast.makeText(this, "Please create account first", Toast.LENGTH_SHORT).show();
        } else {
            startActivity(new Intent(HomeActivity.this, settingsActivity.class));

        }
    }
}
