package sa.thiqah.emanbasahel.popularmovies_1.views;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import java.util.List;
import sa.thiqah.emanbasahel.popularmovies_1.R;
import sa.thiqah.emanbasahel.popularmovies_1.data.model.Result;
import sa.thiqah.emanbasahel.popularmovies_1.data.sqlite.FavoritesContract;
import sa.thiqah.emanbasahel.popularmovies_1.data.sqlite.FavoritesDatabase;
import sa.thiqah.emanbasahel.popularmovies_1.helpers.FavoriteMovieAdapter;
import sa.thiqah.emanbasahel.popularmovies_1.helpers.MovieAdapter;
import sa.thiqah.emanbasahel.popularmovies_1.helpers.StatefulRecyclerView;



public class MovieListFragment extends Fragment {

    //region variables
    private View RootView;
    private StatefulRecyclerView recyclerView;
    private List<Result> movieList;
    private String sortValue = "";
    private MovieAdapter movieAdapter;
    private FavoriteMovieAdapter favoriteMovieAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    //endregion

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //region init
        RootView = inflater.inflate(R.layout.fragment_movie_list, container, false);
        recyclerView = RootView.findViewById(R.id.recycler_view);
        //endregion
        //region getArguments
        if (getArguments() != null) {
            sortValue = getArguments().getString(getString(R.string.sortValue));
            if (sortValue.equals(getString(R.string.popular_movie))) {
                movieList = getArguments().getParcelableArrayList(getString(R.string.popular_movie));
                createRecyclerList(movieList);
            } else if (sortValue.equals(getString(R.string.toprated_movie))) {
                movieList = getArguments().getParcelableArrayList(getString(R.string.toprated_movie));
                createRecyclerList(movieList);
            } else if (sortValue.equals(getString(R.string.favorite_movie))) {
                createFavoriteRecyclerList(getFavoritesMovies());
            }
        }
        //endregion

        return RootView;
    }

    //region getting Favorite Movies from local Database
    private Cursor getFavoritesMovies() {
        String sortOrder =
                FavoritesContract.FavoriteMovies.COLUMN_NAME_TIMESTAMP + " DESC";

        Cursor cursor = getActivity().getContentResolver().query(FavoritesContract.FavoriteMovies.CONTENT_URI, null, null, null,
                 sortOrder);

        if (!cursor.moveToFirst()) {

            Toast.makeText(getActivity(),getString(R.string.noFvorite),Toast.LENGTH_SHORT).show();

        }

        return cursor;
    }
    //endregion

    //region create RecyclerView List
    private void createRecyclerList(List<Result> mList) {
        //region init RecyclerView
        mLayoutManager = new GridLayoutManager(getActivity(), 2);
//        firstVisiblePosition = mLayoutManager.findFirstVisibleItemPosition();

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //endregion
        movieAdapter = new MovieAdapter(mList, new MovieAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Result itemResult) {
                Intent intent = new Intent(getActivity(), MovieDetails.class);
                intent.putExtra(getString(R.string.movieId), itemResult.getId());
                getActivity().startActivity(intent);
            }
        });
        recyclerView.setAdapter(movieAdapter);
    }
    //endregion

    //region create RecyclerView List for Favorite Movies
    private void createFavoriteRecyclerList(Cursor cursor) {
        //region init RecyclerView
        mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //endregion
        favoriteMovieAdapter = new FavoriteMovieAdapter(cursor, new FavoriteMovieAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Cursor itemResult) {
                Intent intent = new Intent(getActivity(), MovieDetails.class);
                intent.putExtra(getString(R.string.movieId), itemResult.getInt(itemResult.getColumnIndex(FavoritesContract.FavoriteMovies.COLUMN_NAME_ID)));
                getActivity().startActivity(intent);
            }
        });
        recyclerView.setAdapter(favoriteMovieAdapter);
    }
    //endregion

    //region refresh view after removing movie from favorite list
    @Override
    public void onResume()
    {
        super.onResume();
        if (sortValue.equals(getString(R.string.favorite_movie)))
            createFavoriteRecyclerList(getFavoritesMovies());
    }
    //endregion

    //region retain state (needs work)
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState!=null)
        {
            if (sortValue.equals(getString(R.string.favorite_movie)))
                recyclerView.setAdapter(favoriteMovieAdapter);
            else
                recyclerView.setAdapter(movieAdapter);
        }
    }
    //endregion

}
