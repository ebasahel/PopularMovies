package sa.thiqah.emanbasahel.popularmovies_1.views;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import sa.thiqah.emanbasahel.popularmovies_1.R;
import sa.thiqah.emanbasahel.popularmovies_1.data.model.Result;
import sa.thiqah.emanbasahel.popularmovies_1.helpers.MovieAdapter;


public class MovieListFragment extends Fragment {


    private View RootView;
    private RecyclerView recyclerView;
    private List<Result> movieList;
    private String sortValue="";
    private MovieAdapter movieAdapter;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //region init
        RootView = inflater.inflate(R.layout.fragment_movie_list, container, false);
        recyclerView = RootView.findViewById(R.id.recycler_view);
        //region init RecyclerView
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //endregion

        //region getArguments
        if (getArguments() != null) {
            sortValue= getArguments().getString(getString(R.string.sortValue));
            if (sortValue.equals(getString(R.string.popular_movie))) {
                movieList = getArguments().getParcelableArrayList(getString(R.string.popular_movie));
                createRecyclerList(recyclerView,movieList);
            } else {
                movieList = getArguments().getParcelableArrayList(getString(R.string.toprated_movie));
                createRecyclerList(recyclerView,movieList);
            }
        }
        //endregion
        //endregion
        return RootView;
    }

    private void createRecyclerList(RecyclerView _recyclerView,List<Result> mList)
    {
        movieAdapter= new MovieAdapter(getActivity(),mList);
        _recyclerView.setAdapter(movieAdapter);
    }
}
