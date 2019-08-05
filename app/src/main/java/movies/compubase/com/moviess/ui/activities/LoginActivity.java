package movies.compubase.com.moviess.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import movies.compubase.com.moviess.R;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

    }

    @OnClick({R.id.forgot_password, R.id.login_btn_login, R.id.register_btn_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.forgot_password:
                startActivity(new Intent(LoginActivity.this,ForgotPassActivity.class));
                break;
            case R.id.login_btn_login:
                startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                break;
            case R.id.register_btn_login:
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                break;
        }
    }
}
