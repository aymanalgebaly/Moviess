package movies.compubase.com.moviess.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.azoft.carousellayoutmanager.CarouselLayoutManager;
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.azoft.carousellayoutmanager.CenterScrollListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;
import movies.compubase.com.moviess.R;
import movies.compubase.com.moviess.adapter.MtRatesAdapter;
import movies.compubase.com.moviess.data.API;
import movies.compubase.com.moviess.helper.RetrofitClient;
import movies.compubase.com.moviess.helper.TinyDB;
import movies.compubase.com.moviess.model.MyRatesResponse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewMyRatesActivity extends AppCompatActivity {


    @BindView(R.id.rating_bar_rate_design)
    MaterialRatingBar ratingBarRateDesign;
    @BindView(R.id.txt_value_comment_view)
    TextView txtValueCommentView;
    @BindView(R.id.rcv_movie_myrates_design)
    RecyclerView rcvMovieMyratesDesign;
    private int id_movie;
    private int id;
    private SharedPreferences preferences;
    private String id_user;
    private MtRatesAdapter adapter;
    private MyRatesResponse myRatesResponse;
    private String rate;
    private List<MyRatesResponse> myRatesResponseList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_my_rates);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        if (intent != null){

            id = intent.getIntExtra("id", id_movie);
        }
        Toast.makeText(this, String.valueOf(id), Toast.LENGTH_SHORT).show();

        preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        id_user = preferences.getString("id", "");

        setupRecycler();
        fetchData();
    }

    private void setupRecycler() {

        final CarouselLayoutManager layoutManager = new CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL, true);
        layoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());
        layoutManager.scrollToPosition(id);
        rcvMovieMyratesDesign.setLayoutManager(layoutManager);
        rcvMovieMyratesDesign.setHasFixedSize(true);
        rcvMovieMyratesDesign.addOnScrollListener(new CenterScrollListener());

        rcvMovieMyratesDesign.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                ratingBarRateDesign.setRating(Float.parseFloat(myRatesResponseList.get(layoutManager.getCenterItemPosition()).getRate()));
                txtValueCommentView.setText(myRatesResponseList.get(layoutManager.getCenterItemPosition()).getComment());

            }
        });
    }

    private void fetchData () {

        Call<ResponseBody> call2 = RetrofitClient.getInstant().create(API.class).MyRates(id_user);

        call2.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();

                assert response.body() != null;
                try {

                    List<MyRatesResponse> myRatesResponses = Arrays.asList(gson.fromJson(response.body().string(), MyRatesResponse[].class));
                    if (response.isSuccessful()){

                        for (int i = 0; i <myRatesResponses.size() ; i++) {

                            myRatesResponse = new MyRatesResponse();

                            myRatesResponse.setImg1(myRatesResponses.get(i).getImg1());
                            myRatesResponse.setRate(myRatesResponses.get(i).getRate());
                            myRatesResponse.setId(myRatesResponses.get(i).getId());
                            myRatesResponse.setComment(myRatesResponses.get(i).getComment());

                            rate = myRatesResponses.get(i).getRate();


                            myRatesResponseList.add(myRatesResponse);

                        }

                        adapter = new MtRatesAdapter(myRatesResponseList);
                        rcvMovieMyratesDesign.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(ViewMyRatesActivity.this, String.valueOf(id), Toast.LENGTH_SHORT).show();
                    }

                }catch (Exception e)
                {
                    Toast.makeText(ViewMyRatesActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(ViewMyRatesActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
