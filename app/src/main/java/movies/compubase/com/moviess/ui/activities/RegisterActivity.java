package movies.compubase.com.moviess.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.paperdb.Paper;
import movies.compubase.com.moviess.R;
import movies.compubase.com.moviess.data.API;
import movies.compubase.com.moviess.helper.LocalHelper;
import movies.compubase.com.moviess.helper.RetrofitClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.logo_img)
    ImageView logoImg;
    @BindView(R.id.rel_register_img)
    RelativeLayout relRegisterImg;
    @BindView(R.id.username_regiser)
    EditText usernameRegiser;
    @BindView(R.id.first_name_edit)
    EditText firstNameEdit;
    @BindView(R.id.last_name_edit)
    EditText lastNameEdit;
    @BindView(R.id.phone_regiser)
    EditText phoneRegiser;
    @BindView(R.id.mail_regiser)
    EditText mailRegiser;
    @BindView(R.id.pass_regiser)
    EditText passRegiser;
    @BindView(R.id.terms_txt)
    TextView termsTxt;
    @BindView(R.id.register_btn_register)
    TextView registerBtnRegister;
    @BindView(R.id.login_btn_register)
    TextView loginBtnRegister;
    @BindView(R.id.reg_txt)
    TextView regTxt;
    @BindView(R.id.if_you_reg)
    TextView ifYouReg;
    private String fristname, lastname, userName, mail, phone, pass;
    private SharedPreferences preferences;
    private String lannguage;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocalHelper.onAttach(newBase, "en"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        preferences = getSharedPreferences("user", MODE_PRIVATE);
        lannguage = preferences.getString("lan", "");

        if (lannguage == null) {
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

        regTxt.setText(resources.getString(R.string.registration));
        usernameRegiser.setHint(resources.getString(R.string.username));
        lastNameEdit.setHint(resources.getString(R.string.last_name));
        firstNameEdit.setHint(resources.getString(R.string.first_name));
        phoneRegiser.setHint(resources.getString(R.string.phone_number));
        mailRegiser.setHint(resources.getString(R.string.email));
        passRegiser.setHint(resources.getString(R.string.password));
        termsTxt.setText(resources.getString(R.string.terms_and_conditions));
        registerBtnRegister.setText(resources.getString(R.string.register));
        ifYouReg.setText(resources.getString(R.string.if_you_have_an_account));
        loginBtnRegister.setText(resources.getString(R.string.login));
    }

    private void ValidateCenter() {
        fristname = firstNameEdit.getText().toString();
        lastname = lastNameEdit.getText().toString();
        userName = usernameRegiser.getText().toString();
        mail = mailRegiser.getText().toString();
        phone = phoneRegiser.getText().toString();
        pass = passRegiser.getText().toString();

        if (TextUtils.isEmpty(fristname)) {
            firstNameEdit.setError("First Name is required");
        } else if (TextUtils.isEmpty(lastname)) {
            lastNameEdit.setError("Last Name is required");
        } else if (TextUtils.isEmpty(userName)) {
            usernameRegiser.setError("Username is required");
        } else if (TextUtils.isEmpty(mail)) {
            mailRegiser.setError("Email is required");
        } else if (TextUtils.isEmpty(phone)) {
            phoneRegiser.setError("Phone Number is required");
        } else if (TextUtils.isEmpty(pass)) {
            passRegiser.setError("Password is required");
        } else {
            Retrofit retrofit = RetrofitClient.getInstant();
            API api = retrofit.create(API.class);
            Call<ResponseBody> responseBodyCall = api.register(userName, fristname, lastname, mail, pass, phone, "image");
            responseBodyCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    if (response.isSuccessful()) {
                        try {
                            assert response.body() != null;
                            String string = response.body().string();

                            //Toast.makeText(RegisterActivity.this, string, Toast.LENGTH_SHORT).show();

                            if (string.equals("True")) {
//                                onBackPressed();
                                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                            } else {
                                Toast.makeText(RegisterActivity.this, string, Toast.LENGTH_SHORT).show();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @OnClick({R.id.terms_txt, R.id.register_btn_register, R.id.login_btn_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.terms_txt:
                break;
            case R.id.register_btn_register:
                ValidateCenter();
//                startActivity(new Intent(RegisterActivity.this,HomeActivity.class));
                break;
            case R.id.login_btn_register:
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                break;
        }
    }
}
