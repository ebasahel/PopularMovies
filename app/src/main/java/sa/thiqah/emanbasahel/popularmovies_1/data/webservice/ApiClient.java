package sa.thiqah.emanbasahel.popularmovies_1.data.webservice;

/**
 * Created by emanbasahel on 30/11/2017 AD.
 */

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {


    private static final String BASE_URL = "http://api.themoviedb.org/3/movie/";
    private static Retrofit retrofit = null;


    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}