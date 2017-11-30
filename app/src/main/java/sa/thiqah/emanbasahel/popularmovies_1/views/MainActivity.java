package sa.thiqah.emanbasahel.popularmovies_1.views;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sa.thiqah.emanbasahel.popularmovies_1.R;
import sa.thiqah.emanbasahel.popularmovies_1.data.model.MovieModel;
import sa.thiqah.emanbasahel.popularmovies_1.data.model.Result;
import sa.thiqah.emanbasahel.popularmovies_1.data.webservice.ApiClient;
import sa.thiqah.emanbasahel.popularmovies_1.data.webservice.ApiInterface;

public class MainActivity extends AppCompatActivity {

    ImageButton actionSort;
    List<Result> movieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        actionSort = findViewById(R.id.action_sort);
        movieList = new ArrayList<>();
        //ToDo show dialog with sorting options when user clicks sort button
        //ToDo create sort function
        getPopularMovies();
    }

    public void getPopularMovies() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<MovieModel> call = apiService.getPopularMovie(getResources().getString(R.string.api_key));
        call.enqueue(new Callback<MovieModel>() {
            @Override
            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
                movieList = response.body().getResults();
            }

            @Override
            public void onFailure(Call<MovieModel> call, Throwable t) {
                //ToDo handle failure
            }
        });

    }

    public void getTopRatedMovies() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<MovieModel> call = apiService.getTopRatedMovies(getResources().getString(R.string.api_key));
        call.enqueue(new Callback<MovieModel>() {
            @Override
            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {

                movieList = response.body().getResults();
            }

            @Override
            public void onFailure(Call<MovieModel> call, Throwable t) {
                //ToDo handle failure
            }
        });

    }

    public void addFragment (List<Result> list, String sortValue)
    {
        MoviesListFragment moviesListFragment = new MoviesListFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        //ToDo parcel list
//        bundle.putParcelableArrayList(sortValue,movieList);
        moviesListFragment.setArguments(bundle);
        fragmentTransaction.add(R.id.container, moviesListFragment);
        fragmentTransaction.commit();
    }
}
