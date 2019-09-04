package movies.compubase.com.moviess.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import movies.compubase.com.moviess.R;
import movies.compubase.com.moviess.model.WatchListResponse;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private Context context;
    private List<WatchListResponse>watchListResponseList;

    public MovieAdapter(List<WatchListResponse> watchListResponseList) {
        this.watchListResponseList = watchListResponseList;
    }

    public MovieAdapter(Context context) {
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

        WatchListResponse watchListResponse = watchListResponseList.get(i);
//        viewHolder.num.setText(watchListResponse.getRate());

        Glide.with(context).load(watchListResponse.getImg1()).placeholder(R.drawable.titanic).into(viewHolder.img);
    }

    @Override
    public int getItemCount() {
        return watchListResponseList != null ? watchListResponseList.size():0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView num;
        LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.img_movie_rcv_design);
//            num = itemView.findViewById(R.id.num_home_rcv_design);
//            linearLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
