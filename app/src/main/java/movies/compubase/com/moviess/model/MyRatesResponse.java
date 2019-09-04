
package movies.compubase.com.moviess.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyRatesResponse implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Des")
    @Expose
    private String des;
    @SerializedName("Type")
    @Expose
    private String type;
    @SerializedName("Rate")
    @Expose
    private String rate;
    @SerializedName("img_1")
    @Expose
    private String img1;
    @SerializedName("img_2")
    @Expose
    private String img2;
    @SerializedName("img_3")
    @Expose
    private String img3;
    @SerializedName("img_4")
    @Expose
    private String img4;
    @SerializedName("Datee")
    @Expose
    private String datee;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("release_date")
    @Expose
    private String releaseDate;
    @SerializedName("age_rate")
    @Expose
    private String ageRate;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("duration")
    @Expose
    private String duration;

    protected MyRatesResponse(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        name = in.readString();
        des = in.readString();
        type = in.readString();
        rate = in.readString();
        img1 = in.readString();
        img2 = in.readString();
        img3 = in.readString();
        img4 = in.readString();
        datee = in.readString();
        category = in.readString();
        releaseDate = in.readString();
        ageRate = in.readString();
        language = in.readString();
        duration = in.readString();
    }

    public static final Creator<MyRatesResponse> CREATOR = new Creator<MyRatesResponse>() {
        @Override
        public MyRatesResponse createFromParcel(Parcel in) {
            return new MyRatesResponse(in);
        }

        @Override
        public MyRatesResponse[] newArray(int size) {
            return new MyRatesResponse[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getImg1() {
        return img1;
    }

    public void setImg1(String img1) {
        this.img1 = img1;
    }

    public String getImg2() {
        return img2;
    }

    public void setImg2(String img2) {
        this.img2 = img2;
    }

    public String getImg3() {
        return img3;
    }

    public void setImg3(String img3) {
        this.img3 = img3;
    }

    public String getImg4() {
        return img4;
    }

    public void setImg4(String img4) {
        this.img4 = img4;
    }

    public String getDatee() {
        return datee;
    }

    public void setDatee(String datee) {
        this.datee = datee;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getAgeRate() {
        return ageRate;
    }

    public void setAgeRate(String ageRate) {
        this.ageRate = ageRate;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeString(name);
        dest.writeString(des);
        dest.writeString(type);
        dest.writeString(rate);
        dest.writeString(img1);
        dest.writeString(img2);
        dest.writeString(img3);
        dest.writeString(img4);
        dest.writeString(datee);
        dest.writeString(category);
        dest.writeString(releaseDate);
        dest.writeString(ageRate);
        dest.writeString(language);
        dest.writeString(duration);
    }
}
