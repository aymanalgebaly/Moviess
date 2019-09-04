package movies.compubase.com.moviess.ui.activities;

import android.content.Intent;
import android.content.SharedPreferences;
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
import movies.compubase.com.moviess.R;
import movies.compubase.com.moviess.data.API;
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
    private SharedPreferences preferences;
    private String email,user_password;
    private int id;
    private String fname,lname,s_username,s_images,s_password,mobile,s_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

    }

    public void userLogin() {
        email = username.getText().toString();
        user_password = password.getText().toString();

        if (!TextUtils.isEmpty(email) || !TextUtils.isEmpty(user_password)) {

            Call<ResponseBody> call2 = RetrofitClient.getInstant().create(API.class).UserLogin(email,user_password);

            call2.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    GsonBuilder builder = new GsonBuilder();
                    Gson gson = builder.create();

                    assert response.body() != null;
                    try {

                        List<LoginModel> loginModels = Arrays.asList(gson.fromJson(response.body().string(), LoginModel[].class));
                        if (response.isSuccessful()){

                             id = loginModels.get(0).getId();
                            fname = loginModels.get(0).getFname();
                            lname = loginModels.get(0).getLname();
                            s_username = loginModels.get(0).getUsername();
                            mobile = loginModels.get(0).getMobile();
                            s_password = loginModels.get(0).getPassword();
                            s_images = loginModels.get(0).getImages();
                            s_email = loginModels.get(0).getEmail();

                                startActivity(new Intent(LoginActivity.this,HomeActivity.class));

                                sharedLogin();
                            }

                    }catch (Exception e)
                    {
                        Log.i( "onResponse: ",e.getMessage());
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
        editor.putString("pass",s_password);
        editor.putString("username",s_username);
        editor.apply();
    }

    @OnClick({R.id.forgot_password, R.id.login_btn_login, R.id.register_btn_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.forgot_password:
                startActivity(new Intent(LoginActivity.this,ForgotPassActivity.class));
                break;
            case R.id.login_btn_login:
                userLogin();
                break;
            case R.id.register_btn_login:
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                break;
        }
    }
}
