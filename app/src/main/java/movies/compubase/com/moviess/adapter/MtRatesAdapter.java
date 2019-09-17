package movies.compubase.com.moviess.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import movies.compubase.com.moviess.R;
import movies.compubase.com.moviess.model.MyRatesResponse;
import movies.compubase.com.moviess.ui.activities.ViewMyRatesActivity;

public class MtRatesAdapter extends RecyclerView.Adapter<MtRatesAdapter.ViewHolder> {

    private Context context;
    private List<MyRatesResponse>myRatesResponseList;

    public MtRatesAdapter(List<MyRatesResponse> myRatesResponseList) {
        this.myRatesResponseList = myRatesResponseList;
    }

    public MtRatesAdapter(Context context) {
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
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {

        final MyRatesResponse myRatesResponse = myRatesResponseList.get(i);
        final Integer id = myRatesResponse.getId();

        Glide.with(context).load(myRatesResponse.getImg1()).placeholder(R.drawable.titanic).into(viewHolder.img);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, ViewMyRatesActivity.class);
                intent.putExtra("id",id);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myRatesResponseList != null ? myRatesResponseList.size():0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.img_movie_rcv_design);

        }
    }
}
