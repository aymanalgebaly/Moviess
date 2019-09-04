package movies.compubase.com.moviess.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class HomeModel{

    private int img,num;

    public HomeModel() {
    }

    public HomeModel(int img, int num) {
        this.img = img;
        this.num = num;
    }


    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}

