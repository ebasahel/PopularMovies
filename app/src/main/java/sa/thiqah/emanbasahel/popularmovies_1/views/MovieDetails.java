package sa.thiqah.emanbasahel.popularmovies_1.views;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import sa.thiqah.emanbasahel.popularmovies_1.data.model.MovieTrailers;
import sa.thiqah.emanbasahel.popularmovies_1.data.model.TrailersResult;
import sa.thiqah.emanbasahel.popularmovies_1.data.sqlite.FavoritesContract;
import sa.thiqah.emanbasahel.popularmovies_1.data.sqlite.FavoritesDatabase;
import sa.thiqah.emanbasahel.popularmovies_1.data.webservice.ApiClient;
import sa.thiqah.emanbasahel.popularmovies_1.data.webservice.ApiInterface;
import sa.thiqah.emanbasahel.popularmovies_1.helpers.TrailersAdapter;

public class MovieDetails extends AppCompatActivity {

    //region variables
    private int movieId;
    private ImageView imgMovie;
    String imgURL;
    private TextView txtTitle, txtDate, txtRating, txtplot;
    private FloatingActionButton btnFav;
    List<TrailersResult> trailersResultList;
    private RecyclerView trailersRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private static final String API_KEY = BuildConfig.api_key;
    private Toolbar toolbar;
    private String movieTitle;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        //region init
        Intent intent = getIntent();
        movieId = intent.getIntExtra(getString(R.string.movieId), 0);
        trailersResultList = new ArrayList<>();
        imgMovie = findViewById(R.id.img_movie);
        txtTitle = findViewById(R.id.txt_movie_title);
        txtDate = findViewById(R.id.txt_date);
        txtRating = findViewById(R.id.txt_rating);
        txtplot = findViewById(R.id.txt_plot);
        trailersRecyclerView = findViewById(R.id.trailers_recyclerview);
        btnFav = findViewById(R.id.btn_fav);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavUtils.navigateUpTo(MovieDetails.this, new Intent(MovieDetails.this, MainActivity.class));
            }
        });

        //region add reviews fragment click listener
        txtRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ReviewsFragment reviewsFragment = new ReviewsFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putInt(getString(R.string.movieId), movieId);
                reviewsFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.container, reviewsFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });
        //endregion

        //region adding movie to Favorite
        btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (addToFavorite() == -1) {
                    Toast.makeText(MovieDetails.this, getString(R.string.favorite_msg_error), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MovieDetails.this, String.format(getString(R.string.favorite_msg), movieTitle), Toast.LENGTH_LONG).show();
                }
            }
        });
        //endregion

        //endregion
        getMovieDetails();
        getVideos();
    }

    //region add movies to favorite database
    private long addToFavorite() {
        String projection[] = {FavoritesContract.FavoriteMovies.COLUMN_NAME_ID};
        String selection = FavoritesContract.FavoriteMovies.COLUMN_NAME_ID + " = ? ";
        String selectionArgs[] = {String.valueOf(movieId)};

        if (getContentResolver().query(Uri.withAppendedPath(FavoritesContract.FavoriteMovies.CONTENT_URI,String.valueOf(movieId)), projection, selection, selectionArgs,null).getCount()==0)
        {

            ContentValues cv = new ContentValues();
            cv.put(FavoritesContract.FavoriteMovies.COLUMN_NAME_TITLE, movieTitle);
            cv.put(FavoritesContract.FavoriteMovies.COLUMN_NAME_ID, movieId);
            cv.put(FavoritesContract.FavoriteMovies.COLUMN_NAME_IMAGE_PATH, imgURL);

            Uri uri = getContentResolver().insert(FavoritesContract.FavoriteMovies.CONTENT_URI,cv);

            return ContentUris.parseId(uri);
        } else
        {
            return -1;
        }

    }
    //endregion

    //region open trailers in youtube app
    private void openTrailer(String videoKey) {
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + videoKey));
        startActivity(appIntent);
    }
    //endregion

    //region call getVideos
    private void getVideos() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<MovieTrailers> call = apiService.getMovieTrailers(movieId, API_KEY);
        call.enqueue(new Callback<MovieTrailers>() {
            @Override
            public void onResponse(@NonNull Call<MovieTrailers> call, @NonNull Response<MovieTrailers> response) {

                trailersResultList = response.body().getResults();
                createRecyclerView(trailersResultList);
            }

            @Override
            public void onFailure(@NonNull Call<MovieTrailers> call, @NonNull Throwable t) {
                Toast.makeText(MovieDetails.this, getString(R.string.noData), Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }
    //endregion

    //region init RecyclerView
    private void createRecyclerView(List<TrailersResult> trailersList) {
        mLayoutManager = new LinearLayoutManager(this);
        trailersRecyclerView.setLayoutManager(mLayoutManager);
        trailersRecyclerView.setItemAnimator(new DefaultItemAnimator());
        TrailersAdapter trailersAdapter = new TrailersAdapter(trailersList, new TrailersAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(TrailersResult itemResult) {
                openTrailer(itemResult.getKey());
            }

        });
        trailersRecyclerView.setAdapter(trailersAdapter);

    }
    //endregion

    //region call getMovie Details API
    public void getMovieDetails() {

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<MovieDetailsModel> call = apiService.getMovieDetails(movieId, API_KEY);
        call.enqueue(new Callback<MovieDetailsModel>() {
            @Override
            public void onResponse(@NonNull Call<MovieDetailsModel> call, @NonNull Response<MovieDetailsModel> response) {
                imgURL = "http://image.tmdb.org/t/p/w185//" + response.body().getPosterPath();
                Picasso.with(MovieDetails.this).load(imgURL).into(imgMovie);
                movieTitle = response.body().getTitle();
                txtTitle.setText(String.format(getString(R.string.movie_title), movieTitle));
                txtDate.setText(String.format(getString(R.string.release_date), response.body().getReleaseDate()));
                txtRating.setText(String.format(getString(R.string.rating), response.body().getVoteAverage()));
                txtplot.setText(String.format(getString(R.string.plot), response.body().getOverview()));

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
