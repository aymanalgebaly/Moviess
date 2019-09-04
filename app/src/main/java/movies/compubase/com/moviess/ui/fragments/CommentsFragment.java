package movies.compubase.com.moviess.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
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
import movies.compubase.com.moviess.adapter.CommentAdapter;
import movies.compubase.com.moviess.adapter.HomeAdapter;
import movies.compubase.com.moviess.data.API;
import movies.compubase.com.moviess.helper.RetrofitClient;
import movies.compubase.com.moviess.model.CommentModel;
import movies.compubase.com.moviess.model.HomeModel;
import movies.compubase.com.moviess.model.ListOfComment;
import movies.compubase.com.moviess.model.ListOfMoviesModel;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommentsFragment extends Fragment {


    @BindView(R.id.rcv_comments)
    RecyclerView rcvComments;
    Unbinder unbinder;
    private CommentAdapter adapter;
    private int[] img,num;
    private String[] comments;
    private ListOfComment listOfComment;
    private ArrayList<ListOfComment>listOfCommentArrayList = new ArrayList<>();

    public CommentsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_comments, container, false);
        unbinder = ButterKnife.bind(this, view);

        setupRecycler();
        fetchData();
        return view;


    }

    private void setupRecycler() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rcvComments.setLayoutManager(linearLayoutManager);
//        adapter = new CommentAdapter(getActivity());
//        rcvComments.setAdapter(adapter);
//        adapter.notifyDataSetChanged();

    }

    private void fetchData() {
        Call<ResponseBody> call2 = RetrofitClient.getInstant().create(API.class).ListOfMovies();

        call2.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();


                try {
                    assert response.body() != null;
                    List<ListOfComment> listOfComments = Arrays.asList(gson.fromJson(response.body().string(), ListOfComment[].class));

                    if (response.isSuccessful()) {


                        for (int j = 0; j < listOfComments.size(); j++) {

                            listOfComment = new ListOfComment();

                            listOfComment.setComment(listOfComments.get(j).getComment());
                            listOfComment.setRate(listOfComments.get(j).getRate());
                            listOfComment.setIdMovie(listOfComments.get(j).getIdMovie());
                            listOfComment.setId(listOfComments.get(j).getId());
                            listOfComment.setIdUser(listOfComments.get(j).getIdUser());
                        }

                        listOfCommentArrayList.add(listOfComment);

                    }
                    adapter = new CommentAdapter(listOfCommentArrayList);
                    rcvComments.setAdapter(adapter);
                    adapter.notifyDataSetChanged();


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
