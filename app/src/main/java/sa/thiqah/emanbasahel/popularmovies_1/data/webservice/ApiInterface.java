package sa.thiqah.emanbasahel.popularmovies_1.data.webservice;

/**
 * Created by emanbasahel on 30/11/2017 AD.
 */

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import sa.thiqah.emanbasahel.popularmovies_1.data.model.MovieModel;


public interface ApiInterface {
    @GET ("movie/popular")
    Call <MovieModel> getPopularMovie (@Query("api_key") String apiKey);

    @GET("movie/top_rated")
    Call<MovieModel> getTopRatedMovies(@Query("api_key") String apiKey);

//    @GET("movie/{id}")
//    Call<MovieModel> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);
}