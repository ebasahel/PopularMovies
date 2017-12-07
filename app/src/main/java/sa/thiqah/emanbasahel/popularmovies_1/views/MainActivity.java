package sa.thiqah.emanbasahel.popularmovies_1.views;

import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
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
import sa.thiqah.emanbasahel.popularmovies_1.helpers.SortDialog;

public class MainActivity extends AppCompatActivity implements SortDialog.onSortSelected {

    //region variables
    private List<Result> movieList;
    private SortDialog sortDialog;
    boolean mIsLargeLayout;
    MovieListFragment popularListFragment;
    MovieListFragment topRatedListFragment;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //region init
        ImageButton actionSort = findViewById(R.id.action_sort);
        movieList = new ArrayList<>();
        sortDialog = new SortDialog(this);
        mIsLargeLayout = getResources().getBoolean(R.bool.large_layout);
        popularListFragment=new MovieListFragment();
        topRatedListFragment = new MovieListFragment();
        actionSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sortDialog.createDialog().show();
            }
        });
        //endregion
        getPopularMovies();
    }


    //region calling getPopularMovies api
    public void getPopularMovies() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<MovieModel> call = apiService.getPopularMovie(getResources().getString(R.string.api_key));
        call.enqueue(new Callback<MovieModel>() {
            @Override
            public void onResponse(@NonNull Call<MovieModel> call, @NonNull Response<MovieModel> response) {
                movieList = response.body().getResults();
                addFragment(popularListFragment,movieList, getResources().getString(R.string.popular_movie));

            }

            @Override
            public void onFailure(@NonNull Call<MovieModel> call, @NonNull Throwable t) {
                Toast.makeText(MainActivity.this, getString(R.string.noData), Toast.LENGTH_LONG).show();
                finish();
            }
        });

    }
    //endregion

    //region calling getTopRatedMovies api
    public void getTopRatedMovies() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<MovieModel> call = apiService.getTopRatedMovies(getResources().getString(R.string.api_key));
        call.enqueue(new Callback<MovieModel>() {
            @Override
            public void onResponse(@NonNull Call<MovieModel> call, @NonNull Response<MovieModel> response) {

                movieList = response.body().getResults();
                addFragment(topRatedListFragment,movieList, getResources().getString(R.string.popular_movie));

            }

            @Override
            public void onFailure(@NonNull Call<MovieModel> call, @NonNull Throwable t) {
                Toast.makeText(MainActivity.this, getString(R.string.noData), Toast.LENGTH_LONG).show();
                finish();
            }
        });

    }
    //endregion

    //region add fragment whether it's popular or toprated
    public void addFragment(Fragment frag, List<Result> list, String sortValue) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(sortValue, (ArrayList<? extends Parcelable>) list);
        bundle.putString(getString(R.string.sortValue), sortValue);
        frag.setArguments(bundle);
        fragmentTransaction.replace(R.id.container, frag);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    //endregion

    //region sort type action listener
    @Override
    public void onSortTypeSelected(String sortType) {
        if (sortType.equals(getString(R.string.toprated_movie)))
            getTopRatedMovies();
        else
            getPopularMovies();
    }
    //endregion
}
