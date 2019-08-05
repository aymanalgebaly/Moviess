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

import com.squareup.picasso.Picasso;

import java.util.List;

import movies.compubase.com.moviess.R;
import movies.compubase.com.moviess.model.HomeModel;
import movies.compubase.com.moviess.ui.activities.MovieActivity;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolderHomeAdapter> {

    private Context context;
    private List<HomeModel> homeModelList;
    onItemClickListner onItemClickedListner;

    public HomeAdapter(Context context, List<HomeModel> homeModelList) {
        this.context = context;
        this.homeModelList = homeModelList;
    }

    public HomeAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<HomeModel> homeModels) {
        this.homeModelList = homeModels;
    }

    public interface onItemClickListner {
        void onClick(HomeModel homeModel);//pass your object types.
    }

    public void onItemClickedListner(HomeAdapter.onItemClickListner onItemClickListner) {
        this.onItemClickedListner = onItemClickListner;
    }
    @NonNull
    @Override
    public ViewHolderHomeAdapter onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.rcv_home_design, viewGroup, false);
        return new ViewHolderHomeAdapter(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolderHomeAdapter viewHolderHomeAdapter, final int i) {

        final HomeModel homeModel = homeModelList.get(i);

        viewHolderHomeAdapter.num.setText(Integer.toString(homeModel.getNum()));

        Picasso.get().load(homeModel.getImg()).into(viewHolderHomeAdapter.img);

        viewHolderHomeAdapter.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeModel homeModel1 = homeModelList.get(i);
//                onItemClickedListner.onClick(homeModel1);

                //Toast.makeText(context, String.valueOf(homeModel1.getNum()), Toast.LENGTH_SHORT).show();

                int img = homeModel1.getImg();
                int num = homeModel1.getNum();
                Intent intent = new Intent(context,MovieActivity.class);
                //Bundle bundle = new Bundle();
                //bundle.putSerializable("data",homeModelList.get(i));
//                intent.putExtra("img",homeModel1.getImg());
                intent.putExtra("num",num);
                intent.putExtra("img",img);
                intent.putExtra("homeModel", (Parcelable) homeModel1);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return homeModelList != null ? homeModelList.size():0;
    }

    public class ViewHolderHomeAdapter extends RecyclerView.ViewHolder {

        ImageView img;
        TextView num;
        LinearLayout linearLayout;

        public ViewHolderHomeAdapter(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.img_home_rcv_design);
            num = itemView.findViewById(R.id.num_home_rcv_design);
            linearLayout = itemView.findViewById(R.id.parent_layout);

        }
    }
}
