package sa.thiqah.emanbasahel.popularmovies_1.views;

import android.os.Parcelable;
import android.support.annotation.NonNull;
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

public class MainActivity extends AppCompatActivity {

    private ImageButton actionSort;
    private List<Result> movieList;
    private SortDialog sortDialog;
    boolean mIsLargeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        actionSort = findViewById(R.id.action_sort);
        movieList = new ArrayList<>();
        sortDialog = new SortDialog();
        mIsLargeLayout = getResources().getBoolean(R.bool.large_layout);

        actionSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        getPopularMovies();
    }

    public void showDialog() {
        FragmentManager fragmentManager = getSupportFragmentManager();


        if (mIsLargeLayout) {
            // The device is using a large layout, so show the fragment as a dialog
            sortDialog.show(fragmentManager, "dialog");
        } else {
            // The device is smaller, so show the fragment fullscreen
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            // For a little polish, specify a transition animation
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            // To make it fullscreen, use the 'content' root view as the container
            // for the fragment, which is always the root view for the activity
            transaction.add(android.R.id.content, sortDialog)
                    .addToBackStack(null).commit();
        }
    }

    public void getPopularMovies() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<MovieModel> call = apiService.getPopularMovie(getResources().getString(R.string.api_key));
        call.enqueue(new Callback<MovieModel>() {
            @Override
            public void onResponse(@NonNull Call<MovieModel> call, @NonNull Response<MovieModel> response) {
                movieList = response.body().getResults();
                addFragment(movieList, getResources().getString(R.string.popular_movie));

            }

            @Override
            public void onFailure(@NonNull Call<MovieModel> call, @NonNull Throwable t) {
                Toast.makeText(MainActivity.this, getString(R.string.noData), Toast.LENGTH_LONG).show();
                finish();
            }
        });

    }

    public void getTopRatedMovies() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<MovieModel> call = apiService.getTopRatedMovies(getResources().getString(R.string.api_key));
        call.enqueue(new Callback<MovieModel>() {
            @Override
            public void onResponse(@NonNull Call<MovieModel> call, @NonNull Response<MovieModel> response) {

                movieList = response.body().getResults();
                addFragment(movieList, getResources().getString(R.string.toprated_movie));
            }

            @Override
            public void onFailure(@NonNull Call<MovieModel> call, @NonNull Throwable t) {
                Toast.makeText(MainActivity.this, getString(R.string.noData), Toast.LENGTH_LONG).show();
                finish();
            }
        });

    }

    public void addFragment(List<Result> list, String sortValue) {
        MoviesListFragment moviesListFragment = new MoviesListFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(sortValue, (ArrayList<? extends Parcelable>) list);
        bundle.putString(getString(R.string.sortValue), sortValue);
        moviesListFragment.setArguments(bundle);
        fragmentTransaction.add(R.id.container, moviesListFragment);
        fragmentTransaction.commit();
    }
}
