package movies.compubase.com.moviess.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.load.engine.Resource;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.paperdb.Paper;
import movies.compubase.com.moviess.R;
import movies.compubase.com.moviess.helper.LocalHelper;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.logo_main)
    ImageView logoMain;
    @BindView(R.id.txt_one)
    TextView txtOne;
    @BindView(R.id.txt_two)
    TextView txtTwo;
    @BindView(R.id.register_txt_btn)
    TextView registerTxtBtn;
    @BindView(R.id.login_txt_btn)
    TextView loginTxtBtn;
    @BindView(R.id.lin_log)
    LinearLayout linLog;
    @BindView(R.id.browse_txt_btn)
    TextView browseTxtBtn;
    @BindView(R.id.lin_browse)
    LinearLayout linBrowse;
    @BindView(R.id.arabic_txt)
    TextView arabicTxt;
    @BindView(R.id.english_txt)
    TextView englishTxt;
    private TextView arabic, english, login, register, browse;
    private String lan;
    private String language;
    private SharedPreferences preferences;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocalHelper.onAttach(newBase, "en"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Paper.init(this);

        language = Paper.book().read("language");
        if (language == null) {
            Paper.book().write("language", "en");
        }

        arabic = findViewById(R.id.arabic_txt);
        arabic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Paper.book().write("language","ar");
                updateView((String)Paper.book().read("language"));
            }
        });

        english = findViewById(R.id.english_txt);
        english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Paper.book().write("language","en");
                updateView((String)Paper.book().read("language"));
            }
        });

        login = findViewById(R.id.login_txt_btn);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

        register = findViewById(R.id.register_txt_btn);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            }
        });

        browse = findViewById(R.id.browse_txt_btn);
        browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, HomeActivity.class));
            }
        });

    }

    private void updateView(String language) {
        Context context = LocalHelper.setLocale(this, language);
        Resources resources = context.getResources();

        txtOne.setText(resources.getString(R.string.your_main_destination));
        txtTwo.setText(resources.getString(R.string.in_discussing_and_evaluating_films));
        registerTxtBtn.setText(resources.getString(R.string.register));
        loginTxtBtn.setText(resources.getString(R.string.login));
        browseTxtBtn.setText(resources.getString(R.string.browse));
        arabicTxt.setText(resources.getString(R.string.arabic));
        englishTxt.setText(resources.getString(R.string.english));

        SharedPreferences.Editor editor = getSharedPreferences("user", MODE_PRIVATE).edit();

        preferences = getSharedPreferences("user", MODE_PRIVATE);

        editor.putBoolean("login", true);

        editor.putString("lan",language);

        editor.apply();
    }



}
