package movies.compubase.com.moviess.model;

import android.os.Parcel;
import android.os.Parcelable;

public class CommentModel implements Parcelable {

    private int img,rat;
    private String comment;

    public CommentModel() {
    }

    public CommentModel(int img, int rat, String comment) {
        this.img = img;
        this.rat = rat;
        this.comment = comment;
    }

    protected CommentModel(Parcel in) {
        img = in.readInt();
        rat = in.readInt();
        comment = in.readString();
    }

    public static final Creator<CommentModel> CREATOR = new Creator<CommentModel>() {
        @Override
        public CommentModel createFromParcel(Parcel in) {
            return new CommentModel(in);
        }

        @Override
        public CommentModel[] newArray(int size) {
            return new CommentModel[size];
        }
    };

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public int getRat() {
        return rat;
    }

    public void setRat(int rat) {
        this.rat = rat;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(img);
        dest.writeInt(rat);
        dest.writeString(comment);
    }
}
