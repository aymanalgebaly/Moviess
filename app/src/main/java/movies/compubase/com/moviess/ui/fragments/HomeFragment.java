package movies.compubase.com.moviess.ui.fragments;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import movies.compubase.com.moviess.R;
import movies.compubase.com.moviess.adapter.HomeAdapter;
import movies.compubase.com.moviess.model.HomeModel;
import movies.compubase.com.moviess.ui.activities.MovieActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    public static int rate;
    private RecyclerView recyclerView;
    private HomeAdapter adapter;
    private int [] img , num;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.home_rcv);

        setupRecycler();
        fetchData();
//        onTouchAdapter();
        return view;
    }

    private void setupRecycler() {

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new HomeAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }
    private void fetchData (){
        List<HomeModel> homeModels = new ArrayList<>();

        img = new int[]{R.drawable.titanic, R.drawable.anti_man, R.drawable.avengers,
                R.drawable.titanic, R.drawable.anti_man, R.drawable.avengers,
                R.drawable.titanic, R.drawable.anti_man, R.drawable.avengers};
        num = new int[]{1, 2, 3, 4, 5, 1, 2, 3, 4};

        for ( int i = 0; i <img.length ; i++) {
            homeModels.add(new HomeModel(img[i],num[i]));
        }
        adapter.setData(homeModels);
        adapter.notifyDataSetChanged();
    }
    private void onTouchAdapter() {
        adapter.onItemClickedListner(new HomeAdapter.onItemClickListner() {
            @Override
            public void onClick(HomeModel homeModel) {

                Intent myIntent = new Intent(getContext(), MovieActivity.class);
//                myIntent.putExtra("img", img[i]); //Optional parameters
                Bitmap bmp = BitmapFactory.decodeResource(getResources(),homeModel.getImg());
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                //myIntent.putExtra("img",byteArray);
                //myIntent.putExtra("num", num[i]); //Optional parameters

                getActivity().startActivity(myIntent);

//                MovieActivity.imageList = img;
//                MovieActivity.numList = num;
            }
        });
    }
}
