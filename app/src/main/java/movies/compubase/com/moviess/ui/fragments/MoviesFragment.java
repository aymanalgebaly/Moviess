package movies.compubase.com.moviess.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import movies.compubase.com.moviess.R;
import movies.compubase.com.moviess.adapter.HomeAdapter;
import movies.compubase.com.moviess.model.HomeModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesFragment extends Fragment {


    @BindView(R.id.movies_rcv)
    RecyclerView moviesRcv;
    Unbinder unbinder;
    private int[] img , num ;
    private HomeAdapter adapter;
    private int i;

    public MoviesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movies, container, false);
        unbinder = ButterKnife.bind(this, view);

        setupRecycler();
        fetchData();

        return view;
    }

    private void setupRecycler() {

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
        moviesRcv.setLayoutManager(gridLayoutManager);
        adapter = new HomeAdapter(getActivity());
        moviesRcv.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }
    private void fetchData (){
        List<HomeModel> homeModels = new ArrayList<>();

        img = new int[]{R.drawable.titanic, R.drawable.anti_man, R.drawable.avengers,
                R.drawable.titanic, R.drawable.anti_man, R.drawable.avengers,
                R.drawable.titanic, R.drawable.anti_man, R.drawable.avengers};
        num = new int[]{1, 2, 3, 4, 5, 1, 2, 3, 4};

        for ( i = 0; i <img.length ; i++) {
            homeModels.add(new HomeModel(img[i],num[i]));
        }
        adapter.setData(homeModels);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}