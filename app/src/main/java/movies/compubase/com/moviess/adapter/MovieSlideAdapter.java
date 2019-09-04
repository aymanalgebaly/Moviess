package movies.compubase.com.moviess.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import movies.compubase.com.moviess.R;
import movies.compubase.com.moviess.model.ListOfMoviesModel;

public class MovieSlideAdapter extends RecyclerView.Adapter<MovieSlideAdapter.ViewHolder> {

    private Context context;
    private List<ListOfMoviesModel>listOfMoviesModelList;

    public MovieSlideAdapter(List<ListOfMoviesModel> listOfMoviesModelList) {
        this.listOfMoviesModelList = listOfMoviesModelList;
    }

    public MovieSlideAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        context = viewGroup.getContext();

        View view = LayoutInflater.from(context).inflate(R.layout.movie_design, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        ListOfMoviesModel listOfMoviesModel = listOfMoviesModelList.get(i);

        Glide.with(context).load(listOfMoviesModel.getImg1()).placeholder(R.drawable.titanic).into(viewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return listOfMoviesModelList != null ? listOfMoviesModelList.size():0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.img_movie_rcv_design);
        }
    }
}
