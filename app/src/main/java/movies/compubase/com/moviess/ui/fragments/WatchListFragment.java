package movies.compubase.com.moviess.ui.fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import movies.compubase.com.moviess.R;
import movies.compubase.com.moviess.adapter.HomeAdapter;
import movies.compubase.com.moviess.adapter.MovieAdapter;
import movies.compubase.com.moviess.data.API;
import movies.compubase.com.moviess.helper.RetrofitClient;
import movies.compubase.com.moviess.model.HomeModel;
import movies.compubase.com.moviess.model.WatchListResponse;
import movies.compubase.com.moviess.ui.activities.LoginActivity;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class WatchListFragment extends Fragment {


    @BindView(R.id.myWatchList_rcv)
    RecyclerView myWatchListRcv;
    Unbinder unbinder;
    private int[] img,num;
    private MovieAdapter adapter;
    private SharedPreferences preferences;
    private String id;
    private List<WatchListResponse>watchListResponseList = new ArrayList<>();
    private WatchListResponse watchListResponse;

    public WatchListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_watch_list, container, false);
        unbinder = ButterKnife.bind(this, view);

        preferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        id = preferences.getString("id", "");

        setupRecycler();
        fetchData();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void setupRecycler() {

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
        myWatchListRcv.setLayoutManager(gridLayoutManager);


    }
    private void fetchData (){

        Call<ResponseBody> call2 = RetrofitClient.getInstant().create(API.class).WatchList(id);

        call2.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();

                assert response.body() != null;
                try {

                    List<WatchListResponse> watchListResponses = Arrays.asList(gson.fromJson(response.body().string(), WatchListResponse[].class));
                    if (response.isSuccessful()){

                        for (int i = 0; i <watchListResponses.size() ; i++) {

                            watchListResponse = new WatchListResponse();

                            watchListResponse.setImg1(watchListResponses.get(i).getImg1());
                            watchListResponse.setRate(watchListResponses.get(i).getRate());
                            watchListResponse.setIdMovie(watchListResponses.get(i).getIdMovie());
                            watchListResponse.setIdUser(watchListResponses.get(i).getIdUser());
                            watchListResponse.setId(watchListResponses.get(i).getId());

                            Integer id = watchListResponses.get(0).getId();
                            Integer idMovie = watchListResponses.get(0).getIdMovie();
                            Integer idUser = watchListResponses.get(0).getIdUser();


                            watchListResponseList.add(watchListResponse);

                        }

                        adapter = new MovieAdapter(watchListResponseList);
                        myWatchListRcv.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(getActivity(), String.valueOf(id), Toast.LENGTH_SHORT).show();
                    }

                }catch (Exception e)
                {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}


