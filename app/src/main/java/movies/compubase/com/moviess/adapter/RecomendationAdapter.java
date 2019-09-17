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

import me.zhanghai.android.materialratingbar.MaterialRatingBar;
import movies.compubase.com.moviess.R;
import movies.compubase.com.moviess.model.ListMoviesByRate;

public class RecomendationAdapter extends RecyclerView.Adapter<RecomendationAdapter.ViewHolder> {
    private Context context;
    private List<ListMoviesByRate>listMoviesByRateList;

    public RecomendationAdapter(List<ListMoviesByRate> listMoviesByRateList) {
        this.listMoviesByRateList = listMoviesByRateList;
    }

    public RecomendationAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();

        View view = LayoutInflater.from(context).inflate(R.layout.rcv_home_design, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        ListMoviesByRate listMoviesByRate = listMoviesByRateList.get(i);

        viewHolder.num.setText(listMoviesByRate.getRate());
        viewHolder.ratingBar.setRating(Float.parseFloat(listMoviesByRate.getRate()));

        Glide.with(context).load(listMoviesByRate.getImg1()).placeholder(R.drawable.titanic).into(viewHolder.img);
    }

    @Override
    public int getItemCount() {
        return listMoviesByRateList != null ? listMoviesByRateList.size():0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView num;
        LinearLayout linearLayout;
        MaterialRatingBar ratingBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.img_home_rcv_design);
            num = itemView.findViewById(R.id.num_home_rcv_design);
            linearLayout = itemView.findViewById(R.id.parent_layout);
            ratingBar = itemView.findViewById(R.id.rateBar_home);
        }
    }
}
