package movies.compubase.com.moviess.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
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
import movies.compubase.com.moviess.adapter.CommentAdapter;
import movies.compubase.com.moviess.adapter.HomeAdapter;
import movies.compubase.com.moviess.model.CommentModel;
import movies.compubase.com.moviess.model.HomeModel;

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
        adapter = new CommentAdapter(getActivity());
        rcvComments.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    private void fetchData() {
        List<CommentModel> commentModels = new ArrayList<>();

        img = new int[]{R.drawable.titanic, R.drawable.anti_man, R.drawable.avengers,
                R.drawable.titanic, R.drawable.anti_man, R.drawable.avengers,
                R.drawable.titanic, R.drawable.anti_man, R.drawable.avengers};
        num = new int[]{1, 2, 3, 4, 5, 1, 2, 3, 4};

        comments = new String []{"Nice Movie","Nice Movie","Nice Movie","Nice Movie","Nice Movie","Nice Movie"
        ,"Nice Movie","Nice Movie","Nice Movie"};

        for (int i = 0; i < img.length; i++) {
            commentModels.add(new CommentModel(img[i], num[i],comments[i]));
        }
        adapter.setData(commentModels);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
