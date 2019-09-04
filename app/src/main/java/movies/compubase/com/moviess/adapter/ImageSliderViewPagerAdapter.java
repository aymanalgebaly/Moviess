package movies.compubase.com.moviess.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import movies.compubase.com.moviess.R;
import movies.compubase.com.moviess.model.HomeModel;
import movies.compubase.com.moviess.model.ListOfMoviesModel;
import movies.compubase.com.moviess.ui.activities.MovieActivity;

public class ImageSliderViewPagerAdapter extends PagerAdapter {

    private Context context;

    private int[] images;
    private ArrayList<ListOfMoviesModel>listOfMoviesModelList;


    public ImageSliderViewPagerAdapter() {
    }

    public ImageSliderViewPagerAdapter(ArrayList<ListOfMoviesModel> list) {
        this.listOfMoviesModelList = list;
    }


//    public ImageSliderViewPagerAdapter(Context context, int[] images) {
//        this.context = context;
//        this.images = images;
//    }

    @Override
    public int getCount() {
        return listOfMoviesModelList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
        }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        context = container.getContext();

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.rcv_home_design,null);

        ImageView image = view.findViewById(R.id.img_home_rcv_design);
        TextView text = view.findViewById(R.id.num_home_rcv_design);

        text.setText(listOfMoviesModelList.get(position).getRate());

        Glide.with(context).load(listOfMoviesModelList.get(position).getImg1()).placeholder(R.drawable.titanic).into(image);

//        Picasso.get().load(listOfMoviesModelList.get(position).getImg1()).into(image);

        container.addView(view);

//        ImageView imageView = new ImageView(context);
//        Picasso.get().load(images[position]).fit().centerCrop().into(imageView);
//
//        container.addView(imageView);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}

