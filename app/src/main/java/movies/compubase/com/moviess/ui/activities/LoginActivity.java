package movies.compubase.com.moviess.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.paperdb.Paper;
import movies.compubase.com.moviess.R;
import movies.compubase.com.moviess.data.API;
import movies.compubase.com.moviess.helper.LocalHelper;
import movies.compubase.com.moviess.helper.RetrofitClient;
import movies.compubase.com.moviess.model.LoginModel;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.logo_img_login)
    ImageView logoImgLogin;
    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.forgot_password)
    TextView forgotPassword;
    @BindView(R.id.login_btn_login)
    TextView loginBtnLogin;
    @BindView(R.id.register_btn_login)
    TextView registerBtnLogin;
    @BindView(R.id.login_txt)
    TextView loginTxt;
    @BindView(R.id.if_you)
    TextView ifYou;
    private SharedPreferences preferences;
    private String email, user_password;
    private int id;
    private String fname, lname, s_username, s_images, s_password, mobile, s_email;
    private String lannguage;


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocalHelper.onAttach(newBase, "en"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        preferences = getSharedPreferences("user", MODE_PRIVATE);
        lannguage = preferences.getString("lan", "");

        Toast.makeText(this, lannguage, Toast.LENGTH_SHORT).show();

        if (lannguage == null) {
//            Paper.book().write("language", "en");
//
//        } else {
            Paper.book().write("language", "ar");
            updateView((String) Paper.book().read("language"));
        }

    }

    private void updateView(String language) {
        Context context = LocalHelper.setLocale(this, language);
        Resources resources = context.getResources();

        loginBtnLogin.setText(resources.getString(R.string.login));
        username.setHint(resources.getString(R.string.username_or_email));
        password.setHint(resources.getString(R.string.password));
        forgotPassword.setText(resources.getString(R.string.forgot_password));
        loginTxt.setText(resources.getString(R.string.log_in));
        ifYou.setText(resources.getString(R.string.if_you_don_t_have_account));
        registerBtnLogin.setText(resources.getString(R.string.register));

    }

    public void userLogin() {
        email = username.getText().toString();
        user_password = password.getText().toString();

        if (!TextUtils.isEmpty(email) || !TextUtils.isEmpty(user_password)) {

            Call<ResponseBody> call2 = RetrofitClient.getInstant().create(API.class).UserLogin(email, user_password);

            call2.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    GsonBuilder builder = new GsonBuilder();
                    Gson gson = builder.create();

                    assert response.body() != null;
                    try {

                        List<LoginModel> loginModels = Arrays.asList(gson.fromJson(response.body().string(), LoginModel[].class));
                        if (response.isSuccessful()) {

                            id = loginModels.get(0).getId();
                            fname = loginModels.get(0).getFname();
                            lname = loginModels.get(0).getLname();
                            s_username = loginModels.get(0).getUsername();
                            mobile = loginModels.get(0).getMobile();
                            s_password = loginModels.get(0).getPassword();
                            s_images = loginModels.get(0).getImages();
                            s_email = loginModels.get(0).getEmail();

                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));

                            sharedLogin();
                        }

                    } catch (Exception e) {
                        Log.i("onResponse: ", e.getMessage());
                        Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    public void sharedLogin() {

        SharedPreferences.Editor editor = getSharedPreferences("user", MODE_PRIVATE).edit();

        preferences = getSharedPreferences("user", MODE_PRIVATE);

        editor.putBoolean("login", true);

        editor.putString("id", String.valueOf(id));
        editor.putString("fname", fname);
        editor.putString("lname", lname);
        editor.putString("email", s_email);
        editor.putString("phone", mobile);
        editor.putString("image", s_images);
        editor.putString("pass", s_password);
        editor.putString("username", s_username);
//        editor.putString("language", lannguage);
        editor.apply();
    }

    @OnClick({R.id.forgot_password, R.id.login_btn_login, R.id.register_btn_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.forgot_password:
                startActivity(new Intent(LoginActivity.this, ForgotPassActivity.class));
                break;
            case R.id.login_btn_login:
                userLogin();
                break;
            case R.id.register_btn_login:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
    }
}
