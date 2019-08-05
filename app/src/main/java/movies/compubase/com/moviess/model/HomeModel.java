package movies.compubase.com.moviess.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class HomeModel implements Parcelable, Serializable {

    private int img,num;

    public HomeModel() {
    }

    public HomeModel(int img, int num) {
        this.img = img;
        this.num = num;
    }

    protected HomeModel(Parcel in) {
        img = in.readInt();
        num = in.readInt();
    }


    public static final Creator<HomeModel> CREATOR = new Creator<HomeModel>() {
        @Override
        public HomeModel createFromParcel(Parcel in) {
            return new HomeModel(in);
        }

        @Override
        public HomeModel[] newArray(int size) {
            return new HomeModel[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(img);
        dest.writeInt(num);
    }
}

