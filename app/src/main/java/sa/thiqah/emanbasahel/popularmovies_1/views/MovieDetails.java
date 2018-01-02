package sa.thiqah.emanbasahel.popularmovies_1.views;

import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sa.thiqah.emanbasahel.popularmovies_1.BuildConfig;
import sa.thiqah.emanbasahel.popularmovies_1.R;
import sa.thiqah.emanbasahel.popularmovies_1.data.model.MovieDetailsModel;
import sa.thiqah.emanbasahel.popularmovies_1.data.model.Result;
import sa.thiqah.emanbasahel.popularmovies_1.data.webservice.ApiClient;
import sa.thiqah.emanbasahel.popularmovies_1.data.webservice.ApiInterface;

public class MovieDetails extends AppCompatActivity {

    //region variables
    private int movieId;
    private ImageView imgMovie;
    private TextView txtTitle,txtDate,txtRating,txtplot;
    private static final String API_KEY = BuildConfig.api_key;
    private Toolbar toolbar;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        //region init
        Intent intent = getIntent();
        movieId= intent.getIntExtra(getString(R.string.movieId),0);
        imgMovie = findViewById(R.id.img_movie);
        txtTitle = findViewById(R.id.txt_movie_title);
        txtDate =findViewById(R.id.txt_date);
        txtRating = findViewById(R.id.txt_rating);
        txtplot=findViewById(R.id.txt_plot);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               NavUtils.navigateUpTo(MovieDetails.this,new Intent(MovieDetails.this,MainActivity.class));
            }
        });

        txtRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //region add reviews fragment
                ReviewsFragment reviewsFragment = new ReviewsFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putInt(getString(R.string.movieId),movieId);
                reviewsFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.container, reviewsFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                //endregion
            }
        });
        //endregion
        getMovieDetails();
    }

    //region call getMovie Details API
    public void getMovieDetails() {

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<MovieDetailsModel> call = apiService.getMovieDetails(movieId,API_KEY);
        call.enqueue(new Callback<MovieDetailsModel>() {
            @Override
            public void onResponse(@NonNull Call<MovieDetailsModel> call, @NonNull Response<MovieDetailsModel> response) {
                String imgURL = "http://image.tmdb.org/t/p/w185//"+ response.body().getPosterPath();
                Picasso.with(MovieDetails.this).load(imgURL).into(imgMovie);
                txtTitle.setText(String.format(getString(R.string.movie_title),response.body().getTitle()));
                txtDate.setText(String.format(getString(R.string.release_date),response.body().getReleaseDate()));
                txtRating.setText(String.format(getString(R.string.rating),response.body().getVoteAverage()));
                txtplot.setText(String.format(getString(R.string.plot),response.body().getOverview()));

            }

            @Override
            public void onFailure(@NonNull Call<MovieDetailsModel> call, @NonNull Throwable t) {
                Toast.makeText(MovieDetails.this, getString(R.string.noData), Toast.LENGTH_LONG).show();
                finish();
            }
        });

    }
    //endregion


}
