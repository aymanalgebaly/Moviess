package movies.compubase.com.moviess.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.azoft.carousellayoutmanager.CarouselLayoutManager;
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.azoft.carousellayoutmanager.CenterScrollListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.paperdb.Paper;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;
import movies.compubase.com.moviess.R;
import movies.compubase.com.moviess.adapter.CommentAdapter;
import movies.compubase.com.moviess.adapter.MovieSlideAdapter;
import movies.compubase.com.moviess.data.API;
import movies.compubase.com.moviess.helper.LocalHelper;
import movies.compubase.com.moviess.helper.RetrofitClient;
import movies.compubase.com.moviess.helper.TinyDB;
import movies.compubase.com.moviess.model.CommentMovie;
import movies.compubase.com.moviess.model.ListOfMoviesModel;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class MovieActivity extends AppCompatActivity {

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

//    private ViewPager viewPagerMovieActivity,slideImage;
//    private ImageSliderViewPagerAdapter sliderViewPagerAdapter;
//    private TabLayout tabs;
//    private ImageView imgMovieActivity;
    private TextView num_rate;
//    private MovieViewPagerAdapter movieViewPagerAdapter;
    private TextView watchList_btn;
    private ListOfMoviesModel listOfMoviesModel;
    private List<ListOfMoviesModel> listOfMoviesModelArrayList = new ArrayList<>();
    private MovieSlideAdapter adapter;
    private RecyclerView recyclerView,rcvComments;
    private int id;
    private SharedPreferences preferences;
    private String id_user;
    private TextView rate_btn,info_btn,comment_btn;
    private LinearLayout lin_info,lin_comm;
    private MaterialRatingBar ratingBar;
    private Float rate ;
    private float rating;
    private TinyDB tinyDB;
    private int id_movie;
    private CommentAdapter comment_adapter;
    private CommentMovie listOfComment;
    private ArrayList<CommentMovie> listOfCommentArrayList = new ArrayList<>();

    TextView filmLanguage,category,duration,age,description,dateRelease;
    private String s_id;
    private String language;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocalHelper.onAttach(newBase, "en"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        filmLanguage = findViewById(R.id.language_value);
        category = findViewById(R.id.category_value);
        duration = findViewById(R.id.duration_value);
        age = findViewById(R.id.age_value);
        description = findViewById(R.id.description_value);
        dateRelease = findViewById(R.id.release_value);
        info_btn = findViewById(R.id.info_btn);
        comment_btn = findViewById(R.id.comment_btn);
        watchList_btn = findViewById(R.id.add_watchList);
        rate_btn = findViewById(R.id.rate_btn_movie_activity);


        rcvComments = findViewById(R.id.rcv_comments);

        preferences = getSharedPreferences("user", MODE_PRIVATE);
        language = preferences.getString("lan", "");

        if (language == null) {
            Paper.book().write("language", "en");

        }
//        else {
//            Paper.book().write("language", "ar");
//            updateView((String) Paper.book().read("language"));
//        }

        lin_info = findViewById(R.id.lin_about_movie);
        lin_comm = findViewById(R.id.lin_comments);

        info_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lin_comm.setVisibility(View.GONE);
                lin_info.setVisibility(View.VISIBLE);
            }
        });


        comment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lin_comm.setVisibility(View.VISIBLE);
                lin_info.setVisibility(View.GONE);

                setupRecyclerComment();
                fetchDataComment(s_id);
            }
        });

        ratingBar = findViewById(R.id.rateBar_movie_activity);
        num_rate = findViewById(R.id.num_movie_rcv_design);


        recyclerView = findViewById(R.id.rcv_movie);

        watchList_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id_user.isEmpty()){

                    Toast.makeText(MovieActivity.this, "Please create account first", Toast.LENGTH_SHORT).show();
                }else {

                    addWatchList();
                }
            }
        });

        preferences = getSharedPreferences("user",MODE_PRIVATE);
         id_user = preferences.getString("id", "");


         Intent intent = getIntent();
         if (intent != null){

             id = intent.getIntExtra("id", id_movie);
         }
         s_id = String.valueOf(id);

        rate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (id_user.isEmpty()){
                    Toast.makeText(MovieActivity.this, "Please create account first", Toast.LENGTH_SHORT).show();
                }else {

                    Intent intent1 = new Intent(MovieActivity.this,DialogActivity.class);
                    intent1.putExtra("id", id);
                    startActivity(intent1);
                }
            }
        });

        setupRecycler();
        fetchData();
    }

    private void updateView(String language) {

        Context context = LocalHelper.setLocale(this, language);
        Resources resources = context.getResources();

        info_btn.setText(resources.getString(R.string.info));
        comment_btn.setText(resources.getString(R.string.comments));

        watchList_btn.setText(resources.getString(R.string.watchlist));
        rate_btn.setText(resources.getString(R.string.rate_now));
    }

    private void setupRecyclerComment() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvComments.setLayoutManager(linearLayoutManager);
//        adapter = new CommentAdapter(getActivity());
//        rcvComments.setAdapter(adapter);
//        adapter.notifyDataSetChanged();

    }

    private void fetchDataComment(String s_id) {

        listOfCommentArrayList.clear();

        Call<ResponseBody> call2 = RetrofitClient.getInstant().create(API.class).MovieByMovie(s_id);

        call2.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();


                try {
                    assert response.body() != null;
                    List<CommentMovie> commentMovies = Arrays.asList(gson.fromJson(response.body().string(), CommentMovie[].class));

                    if (response.isSuccessful()) {


                        for (int j = 0; j < commentMovies.size(); j++) {

                            listOfComment = new CommentMovie();

                            listOfComment.setComment(commentMovies.get(j).getComment());
                            listOfComment.setRate(commentMovies.get(j).getRate());
                            listOfComment.setIdMovie(commentMovies.get(j).getIdMovie());
                            listOfComment.setId(commentMovies.get(j).getId());
                            listOfComment.setIdUser(commentMovies.get(j).getIdUser());
                            listOfComment.setImages(commentMovies.get(j).getImages());
                            listOfComment.setUsername(commentMovies.get(j).getUsername());

                        }

                        listOfCommentArrayList.add(listOfComment);

                    }
                    comment_adapter = new CommentAdapter(listOfCommentArrayList);
                    rcvComments.setAdapter(comment_adapter);
                    comment_adapter.notifyDataSetChanged();


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(MovieActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addWatchList() {

        Retrofit retrofit = RetrofitClient.getInstant();
        API api = retrofit.create(API.class);
        Call<ResponseBody> responseBodyCall = api.AddWatchList(id_user, String.valueOf(id));
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String string = response.body().string();

                    if (string.equals("True")){

                        Toast.makeText(MovieActivity.this, "Moved to your watch list", Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(MovieActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupRecycler(){

        final CarouselLayoutManager layoutManager = new CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL, true);
        layoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());
        layoutManager.scrollToPosition(id);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addOnScrollListener(new CenterScrollListener());

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                filmLanguage.setText(listOfMoviesModelArrayList.get(layoutManager.getCenterItemPosition()).getLanguage());
                category.setText(listOfMoviesModelArrayList.get(layoutManager.getCenterItemPosition()).getType());
                duration.setText(listOfMoviesModelArrayList.get(layoutManager.getCenterItemPosition()).getDuration());
                description.setText(listOfMoviesModelArrayList.get(layoutManager.getCenterItemPosition()).getDes());
                age.setText(listOfMoviesModelArrayList.get(layoutManager.getCenterItemPosition()).getAgeRate());
                dateRelease.setText(listOfMoviesModelArrayList.get(layoutManager.getCenterItemPosition()).getReleaseDate());
                num_rate.setText(listOfMoviesModelArrayList.get(layoutManager.getCenterItemPosition()).getRate());
                ratingBar.setRating(Float.parseFloat(listOfMoviesModelArrayList.get(layoutManager.getCenterItemPosition()).getRate()));

                fetchDataComment(s_id);

            }
        });
    }

    private void fetchData() {

        listOfMoviesModelArrayList.clear();

        Call<ResponseBody> call2 = RetrofitClient.getInstant().create(API.class).ListOfMovies();

        call2.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();


                try {
                    assert response.body() != null;
                    List<ListOfMoviesModel> listOfMoviesModels = Arrays.asList(gson.fromJson(response.body().string(), ListOfMoviesModel[].class));

                    if (response.isSuccessful()) {


                        for (int j = 0; j < listOfMoviesModels.size(); j++) {

                            listOfMoviesModel = new ListOfMoviesModel();

                            listOfMoviesModel.setLanguage(listOfMoviesModels.get(j).getLanguage());
                            listOfMoviesModel.setDuration(listOfMoviesModels.get(j).getDuration());
                            listOfMoviesModel.setName(listOfMoviesModels.get(j).getName());
                            listOfMoviesModel.setDes(listOfMoviesModels.get(j).getDes());
                            listOfMoviesModel.setType(listOfMoviesModels.get(j).getType());
                            listOfMoviesModel.setAgeRate(listOfMoviesModels.get(j).getAgeRate());
                            listOfMoviesModel.setReleaseDate(listOfMoviesModels.get(j).getReleaseDate());
                            listOfMoviesModel.setRate(listOfMoviesModels.get(j).getRate());
                            listOfMoviesModel.setId(listOfMoviesModels.get(j).getId());
                            listOfMoviesModel.setImg1(listOfMoviesModels.get(j).getImg1());
                            listOfMoviesModel.setImg2(listOfMoviesModels.get(j).getImg2());
                            listOfMoviesModel.setImg3(listOfMoviesModels.get(j).getImg3());
                            listOfMoviesModel.setImg4(listOfMoviesModels.get(j).getImg4());

                            listOfMoviesModelArrayList.add(listOfMoviesModel);

                        }
                        adapter = new MovieSlideAdapter(listOfMoviesModelArrayList);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(MovieActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }


        });
    }
}
