package movies.compubase.com.moviess.data;

import movies.compubase.com.moviess.model.LoginModel;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface API {

    @FormUrlEncoded
    @POST("register")
    Call<ResponseBody>register(
            @Field("Username") String username,
            @Field("Fname") String Fname,
            @Field("Lname") String Lname,
            @Field("Email") String email,
            @Field("Password") String pass,
            @Field("Mobile") String mobile,
            @Field("Images") String image
    );

    @FormUrlEncoded
    @POST("login_user")
    Call<ResponseBody>UserLogin(
            @Field("emailorusername") String email,
            @Field("pass") String pass
    );

    @GET("select_all_movie")
    Call<ResponseBody>ListOfMovies();

    @FormUrlEncoded
    @POST("insert_moive_watch_list")
    Call<ResponseBody>AddWatchList(
            @Field("id_user")String id_user,
            @Field("id_movie")String id_movie
    );

    @FormUrlEncoded
    @POST("select_movie_watch_list")
    Call<ResponseBody>WatchList(
            @Field("id_user")String id_user
    );

    @FormUrlEncoded
    @POST("insert_moive_comment")
    Call<ResponseBody>InsertComment(
            @Field("id_user") String id_user,
            @Field("id_movie") String id_movie,
            @Field("Comment") String comment,
            @Field("Rate") String rate
    );

    @FormUrlEncoded
    @POST("select_comment_movie_by_user")
    Call<ResponseBody>MyRates(
            @Field("id_user") String id_user
    );

    @FormUrlEncoded
    @POST("update_all_data_user")
    Call<ResponseBody> UpdateData(
            @Field("Username") String username,
            @Field("Fname") String fname,
            @Field("Lname")String lname,
            @Field("Email") String email,
            @Field("Password") String pass,
            @Field("Mobile") String mob,
            @Field("Images") String image,
            @Field("id") String id
    );
}
