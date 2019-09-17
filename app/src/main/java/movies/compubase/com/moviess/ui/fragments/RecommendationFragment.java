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
import movies.compubase.com.moviess.adapter.RecomendationAdapter;
import movies.compubase.com.moviess.data.API;
import movies.compubase.com.moviess.helper.RetrofitClient;
import movies.compubase.com.moviess.model.HomeModel;
import movies.compubase.com.moviess.model.ListMoviesByRate;
import movies.compubase.com.moviess.model.ListOfMoviesModel;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecommendationFragment extends Fragment {


    @BindView(R.id.recommendation_rcv)
    RecyclerView recommendationRcv;
    Unbinder unbinder;
    private RecomendationAdapter adapter;
    private int[] img , num ;
    private int i;
    private ListMoviesByRate listMoviesRate;
    private List<ListMoviesByRate> listMoviesByRateArrayList = new ArrayList<>();

    public RecommendationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recommendation, container, false);
        unbinder = ButterKnife.bind(this, view);

        setupRecycler();
        fetchData();

        return view;
    }

    private void setupRecycler() {

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
        recommendationRcv.setLayoutManager(gridLayoutManager);


    }
    private void fetchData (){


            listMoviesByRateArrayList.clear();

            Call<ResponseBody> call2 = RetrofitClient.getInstant().create(API.class).Recomendation();

            call2.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    GsonBuilder builder = new GsonBuilder();
                    Gson gson = builder.create();


                    try {
                        assert response.body() != null;
                        List<ListMoviesByRate> listMoviesByRates = Arrays.asList(gson.fromJson(response.body().string(), ListMoviesByRate[].class));

                        if (response.isSuccessful()) {


                            for (int j = 0; j < listMoviesByRates.size(); j++) {

                                listMoviesRate = new ListMoviesByRate();

                                listMoviesRate.setLanguage(listMoviesByRates.get(j).getLanguage());
                                listMoviesRate.setDuration(listMoviesByRates.get(j).getDuration());
                                listMoviesRate.setName(listMoviesByRates.get(j).getName());
                                listMoviesRate.setDes(listMoviesByRates.get(j).getDes());
                                listMoviesRate.setType(listMoviesByRates.get(j).getType());
                                listMoviesRate.setAgeRate(listMoviesByRates.get(j).getAgeRate());
                                listMoviesRate.setReleaseDate(listMoviesByRates.get(j).getReleaseDate());
                                listMoviesRate.setRate(listMoviesByRates.get(j).getRate());
                                listMoviesRate.setId(listMoviesByRates.get(j).getId());
                                listMoviesRate.setImg1(listMoviesByRates.get(j).getImg1());
//                            listOfMoviesModel.setImg2(listOfMoviesModels.get(j).getImg2());
//                            listOfMoviesModel.setImg3(listOfMoviesModels.get(j).getImg3());
//                            listOfMoviesModel.setImg4(listOfMoviesModels.get(j).getImg4());


                                listMoviesByRateArrayList.add(listMoviesRate);

                            }

                            adapter = new RecomendationAdapter(listMoviesByRateArrayList);
                            recommendationRcv.setAdapter(adapter);
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
