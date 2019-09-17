package movies.compubase.com.moviess.ui.fragments;


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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import movies.compubase.com.moviess.R;
import movies.compubase.com.moviess.adapter.HomeAdapter;
import movies.compubase.com.moviess.data.API;
import movies.compubase.com.moviess.helper.RetrofitClient;
import movies.compubase.com.moviess.model.HomeModel;
import movies.compubase.com.moviess.model.ListOfMoviesModel;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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
    private ListOfMoviesModel listOfMoviesModel;
    private List<ListOfMoviesModel> listOfMoviesModelArrayList = new ArrayList<>();


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

    }

    private void fetchData() {


        listOfMoviesModelArrayList.clear();

        Call<ResponseBody> call2 = RetrofitClient.getInstant().create(API.class).ListOfMovies();

        call2.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();


                try {
                    assert response.body() != null;
                    List<ListOfMoviesModel> listOfMoviesModels = Arrays.asList(gson.fromJson(response.body().string(), ListOfMoviesModel[].class));

                    if (response.isSuccessful()) {


                        for (int j = 0; j < listOfMoviesModels.size(); j++) {

                            listOfMoviesModel = new ListOfMoviesModel();

                            listOfMoviesModel.setLanguage(listOfMoviesModels.get(j).getLanguage());
                            listOfMoviesModel.setDuration(listOfMoviesModels.get(j).getDuration());
                            listOfMoviesModel.setName(listOfMoviesModels.get(j).getName());
                            listOfMoviesModel.setDes(listOfMoviesModels.get(j).getDes());
                            listOfMoviesModel.setType(listOfMoviesModels.get(j).getType());
                            listOfMoviesModel.setAgeRate(listOfMoviesModels.get(j).getAgeRate());
                            listOfMoviesModel.setReleaseDate(listOfMoviesModels.get(j).getReleaseDate());
                            listOfMoviesModel.setRate(listOfMoviesModels.get(j).getRate());
                            listOfMoviesModel.setId(listOfMoviesModels.get(j).getId());
                            listOfMoviesModel.setImg1(listOfMoviesModels.get(j).getImg1());
//                            listOfMoviesModel.setImg2(listOfMoviesModels.get(j).getImg2());
//                            listOfMoviesModel.setImg3(listOfMoviesModels.get(j).getImg3());
//                            listOfMoviesModel.setImg4(listOfMoviesModels.get(j).getImg4());


                            listOfMoviesModelArrayList.add(listOfMoviesModel);

                        }

                        adapter = new HomeAdapter(listOfMoviesModelArrayList);
                        moviesRcv.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }



                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
