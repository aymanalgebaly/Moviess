package movies.compubase.com.moviess.helper;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    public static Retrofit getInstant(){
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl("http://movie.compu-base.com/movie.asmx/");
        builder.addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();

        return retrofit;
    }
}
