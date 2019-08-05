package movies.compubase.com.moviess.ui.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;
import movies.compubase.com.moviess.R;
import movies.compubase.com.moviess.adapter.ImageSliderViewPagerAdapter;
import movies.compubase.com.moviess.adapter.MovieViewPagerAdapter;
import movies.compubase.com.moviess.model.HomeModel;

public class MovieActivity extends AppCompatActivity {

    //    public static HomeModel homeModel;
    //    public static int[] imageList;
//    public static int[] numList;
//    @BindView(R.id.img_movie_activity)
//    ImageView imgMovieActivity;
//    @BindView(R.id.rating_bar_movie_activity)
//    MaterialRatingBar ratingBarMovieActivity;
//    @BindView(R.id.num_home_Movie_Activity)
//    TextView numHomeMovieActivity;
//    @BindView(R.id.rel_movie_activity)
//    RelativeLayout relMovieActivity;
//    @BindView(R.id.tabs)
//    TabLayout tabs;
//    @BindView(R.id.appBar_movie_activity)
//    AppBarLayout appBarMovieActivity;
//    @BindView(R.id.viewPager_movie_activity)
//    ViewPager viewPagerMovieActivity;

    private ViewPager viewPagerMovieActivity,slideImage;
    private ImageSliderViewPagerAdapter sliderViewPagerAdapter;
    private TabLayout tabs;
    private ImageView imgMovieActivity;
    private TextView textView;
    private MovieViewPagerAdapter movieViewPagerAdapter;
    private int img,number;
    private Button watchList_btn;
    private EditText comment;
    private String cooment_txt;
    private Button rate_button;
    private Dialog dialog;

    private int images[] = new int[]{
            R.drawable.titanic,R.drawable.anti_man,R.drawable.avengers,
            R.drawable.titanic,R.drawable.anti_man,R.drawable.avengers,
            R.drawable.titanic,R.drawable.anti_man,R.drawable.avengers
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);


//        imgMovieActivity = findViewById(R.id.img_movie_activity);
        tabs = findViewById(R.id.tabs);
        textView = findViewById(R.id.num_home_Movie_Activity);
        viewPagerMovieActivity = findViewById(R.id.viewPager_movie_activity);

        slideImage = findViewById(R.id.view_pager_img_movie_activity);
        sliderViewPagerAdapter = new ImageSliderViewPagerAdapter(this,images);
        slideImage.setAdapter(sliderViewPagerAdapter);

        Button rate_btn = findViewById(R.id.rate_btn_movie_activity);
        rate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog = new Dialog(MovieActivity.this);
                dialog.setContentView(R.layout.add_rate);
                dialog.setTitle("Rate");
                dialog.show();

            }
        });

        tabs.setupWithViewPager(viewPagerMovieActivity);

        movieViewPagerAdapter = new MovieViewPagerAdapter(getSupportFragmentManager());
        viewPagerMovieActivity.setAdapter(movieViewPagerAdapter);

        View root = tabs.getChildAt(0);
        if (root instanceof LinearLayout) {
            ((LinearLayout) root).setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
            GradientDrawable drawable = new GradientDrawable();
            drawable.setColor(getResources().getColor(R.color.black));
            drawable.setSize(2, 1);
            ((LinearLayout) root).setDividerPadding(10);
            ((LinearLayout) root).setDividerDrawable(drawable);
        }


//        Intent intent = getIntent();
//        int img = intent.getIntExtra("img", this.img);
//        int num = intent.getIntExtra("num", number);
//
////        Toast.makeText(this, String.valueOf(num), Toast.LENGTH_SHORT).show();
//
//        textView.setText(String.valueOf(num));
//
//        Picasso.get().load(img).into(imgMovieActivity);

//        numHomeMovieActivity.setText(num);
//        Picasso.get().load(image).into(imgMovieActivity);
    }

    public void save_rate(View view) {

        dialog.dismiss();
    }
}
