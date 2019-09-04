
package movies.compubase.com.moviess.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListOfComment {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("id_user")
    @Expose
    private Integer idUser;
    @SerializedName("id_movie")
    @Expose
    private Integer idMovie;
    @SerializedName("Comment")
    @Expose
    private String comment;
    @SerializedName("Rate")
    @Expose
    private String rate;
    @SerializedName("Datee")
    @Expose
    private String datee;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public Integer getIdMovie() {
        return idMovie;
    }

    public void setIdMovie(Integer idMovie) {
        this.idMovie = idMovie;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getDatee() {
        return datee;
    }

    public void setDatee(String datee) {
        this.datee = datee;
    }

}
