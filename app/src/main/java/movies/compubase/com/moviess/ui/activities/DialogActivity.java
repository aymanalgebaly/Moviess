package movies.compubase.com.moviess.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.paperdb.Paper;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;
import movies.compubase.com.moviess.R;
import movies.compubase.com.moviess.data.API;
import movies.compubase.com.moviess.helper.LocalHelper;
import movies.compubase.com.moviess.helper.RetrofitClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DialogActivity extends AppCompatActivity {

    @BindView(R.id.rate_bar_dialog)
    MaterialRatingBar rateBarDialog;
    @BindView(R.id.rate_field)
    EditText rateField;
    @BindView(R.id.rate_btn_movie)
    Button rateBtnMovie;
    private String ratefeld;
    private float rating;
    private SharedPreferences preferences;
    private String id_user;
    private int id;
    private String language;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocalHelper.onAttach(newBase, "en"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        ButterKnife.bind(this);

        rateBarDialog.setNumStars(5);

        Intent intent = getIntent();
        id = Objects.requireNonNull(intent.getExtras()).getInt("id");

        preferences = getSharedPreferences("user", MODE_PRIVATE);
        id_user = preferences.getString("id", "");
        language = preferences.getString("lan", "");

        if (language == null) {
            Paper.book().write("language", "en");

        }
//        else {
//            Paper.book().write("language", "ar");
//            updateView((String) Paper.book().read("language"));
//        }
    }
    private void updateView(String language) {

        Context context = LocalHelper.setLocale(this, language);
        Resources resources = context.getResources();

        rateBtnMovie.setText(resources.getString(R.string.rate_now));
        rateField.setText(resources.getString(R.string.write_comment));
    }

    @OnClick(R.id.rate_btn_movie)
    public void onViewClicked() {

        ratefeld = rateField.getText().toString();
        rating = rateBarDialog.getRating();


        Retrofit retrofit = RetrofitClient.getInstant();
        API api = retrofit.create(API.class);
        Call<ResponseBody> responseBodyCall = api.InsertComment(id_user, String.valueOf(id), ratefeld, String.valueOf(rating));
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){

                    Toast.makeText(DialogActivity.this, id_user + id + rating, Toast.LENGTH_SHORT).show();

                    onBackPressed();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(DialogActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
