package movies.compubase.com.moviess.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;

import movies.compubase.com.moviess.ui.fragments.AboutMovieFragment;
import movies.compubase.com.moviess.ui.fragments.CommentsFragment;

public class MovieViewPagerAdapter extends FragmentPagerAdapter {

    Fragment[] fragments ={new AboutMovieFragment(),new CommentsFragment()};

    String title;

    public MovieViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return fragments[i];
    }

    @Override
    public int getCount() {
        return fragments.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        switch (position){
            case 0:
                title = "Info";
                break;
            case 1:
                title =  "Comments";
                break;

        }
        return title;
    }
}
