package movies.compubase.com.moviess.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import movies.compubase.com.moviess.R;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

    }

    @OnClick({R.id.terms_txt, R.id.register_btn_register, R.id.login_btn_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.terms_txt:
                break;
            case R.id.register_btn_register:
                startActivity(new Intent(RegisterActivity.this,HomeActivity.class));
                break;
            case R.id.login_btn_register:
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                break;
        }
    }
}
