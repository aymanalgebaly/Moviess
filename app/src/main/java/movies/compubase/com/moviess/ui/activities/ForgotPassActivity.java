package movies.compubase.com.moviess.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import movies.compubase.com.moviess.R;

public class ForgotPassActivity extends AppCompatActivity {

    @BindView(R.id.logo_pass_forgot)
    ImageView logoPassForgot;
    @BindView(R.id.lin_one)
    LinearLayout linOne;
    @BindView(R.id.send_msg)
    TextView sendMsg;
    @BindView(R.id.return_to_login_txt)
    TextView returnToLoginTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);
        ButterKnife.bind(this);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

    }

    @OnClick({R.id.send_msg, R.id.return_to_login_txt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.send_msg:
                break;
            case R.id.return_to_login_txt:
                startActivity(new Intent(ForgotPassActivity.this,LoginActivity.class));
                break;
        }
    }
}
