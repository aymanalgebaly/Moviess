package movies.compubase.com.moviess.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;
import movies.compubase.com.moviess.R;
import movies.compubase.com.moviess.helper.TinyDB;
import movies.compubase.com.moviess.model.HomeModel;
import movies.compubase.com.moviess.model.ListOfMoviesModel;
import movies.compubase.com.moviess.ui.activities.MovieActivity;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolderHomeAdapter> {

    private Context context;
    private List<ListOfMoviesModel> listOfMoviesModelList;

    public HomeAdapter() {
    }

    TinyDB tinyDB;


    public HomeAdapter(Context context) {
        this.context = context;
    }

    public HomeAdapter(List<ListOfMoviesModel> listOfMoviesModelArrayList) {
        this.listOfMoviesModelList = listOfMoviesModelArrayList;
    }

    @NonNull
    @Override
    public ViewHolderHomeAdapter onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();

        View view = LayoutInflater.from(context).inflate(R.layout.rcv_home_design, viewGroup, false);
        return new ViewHolderHomeAdapter(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolderHomeAdapter viewHolderHomeAdapter, final int i) {


        final ListOfMoviesModel listOfMoviesModel = listOfMoviesModelList.get(i);

        viewHolderHomeAdapter.num.setText(listOfMoviesModel.getRate());

        viewHolderHomeAdapter.ratingBar.setRating(Float.parseFloat(listOfMoviesModel.getRate()));

        Glide.with(context).load(listOfMoviesModel.getImg1()).placeholder(R.drawable.titanic).into(viewHolderHomeAdapter.img);


        viewHolderHomeAdapter.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListOfMoviesModel listOfMoviesModel1 = listOfMoviesModelList.get(i);


                String img1 = listOfMoviesModel1.getImg1();
                String rate = listOfMoviesModel1.getRate();
                String category = listOfMoviesModel1.getCategory();
                String ageRate = listOfMoviesModel1.getAgeRate();
                String duration = listOfMoviesModel1.getDuration();
                String des = listOfMoviesModel1.getDes();
                String img2 = listOfMoviesModel1.getImg2();
                String img3 = listOfMoviesModel1.getImg3();
                String img4 = listOfMoviesModel1.getImg4();
                String language = listOfMoviesModel1.getLanguage();
                String name = listOfMoviesModel1.getName();
                String releaseDate = listOfMoviesModel1.getReleaseDate();
                String type = listOfMoviesModel1.getType();
                Integer id = listOfMoviesModel1.getId();

//                Toast.makeText(context, String.valueOf(id), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(context,MovieActivity.class);

                intent.putExtra("num",rate);
                intent.putExtra("img1",img1);
                intent.putExtra("img2",img2);
                intent.putExtra("img3",img3);
                intent.putExtra("img4",img4);
                intent.putExtra("id",id);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listOfMoviesModelList != null ? listOfMoviesModelList.size():0;
    }

    public class ViewHolderHomeAdapter extends RecyclerView.ViewHolder {

        ImageView img;
        TextView num;
        LinearLayout linearLayout;
        MaterialRatingBar ratingBar;

        public ViewHolderHomeAdapter(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.img_home_rcv_design);
            num = itemView.findViewById(R.id.num_home_rcv_design);
            linearLayout = itemView.findViewById(R.id.parent_layout);
            ratingBar = itemView.findViewById(R.id.rateBar_home);

        }
    }
}
