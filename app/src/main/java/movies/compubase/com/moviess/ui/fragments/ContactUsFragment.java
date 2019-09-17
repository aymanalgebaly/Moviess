package movies.compubase.com.moviess.ui.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;
import movies.compubase.com.moviess.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactUsFragment extends Fragment {


    @BindView(R.id.img_contact)
    CircleImageView imgContact;
    @BindView(R.id.hallo_contact)
    TextView halloContact;
    @BindView(R.id.name_contact)
    TextView nameContact;
    @BindView(R.id.txt_contact)
    TextView txtContact;
    @BindView(R.id.msg_contact)
    EditText msgContact;
    @BindView(R.id.mail_contact)
    EditText mailContact;
    @BindView(R.id.send_txt_btn_contact)
    TextView sendTxtBtnContact;
    Unbinder unbinder;
    private SharedPreferences preferences;
    private String language;

    public ContactUsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact_us, container, false);

        preferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        language = preferences.getString("lan", "");

        if (language == null) {
            Paper.book().write("language", "en");

        }else {
            Paper.book().write("language", "ar");
            updateView((String) Paper.book().read("language"));
        }

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    private void updateView(String language) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.send_txt_btn_contact)
    public void onViewClicked() {
    }
}
