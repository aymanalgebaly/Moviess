package movies.compubase.com.moviess.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import movies.compubase.com.moviess.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CinemaHallsFragment extends Fragment {


    public CinemaHallsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cinema_halls, container, false);
    }

    

}
