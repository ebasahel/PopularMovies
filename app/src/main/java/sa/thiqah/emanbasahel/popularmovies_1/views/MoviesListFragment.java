package sa.thiqah.emanbasahel.popularmovies_1.views;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sa.thiqah.emanbasahel.popularmovies_1.R;


public class MoviesListFragment extends Fragment {


   View RootView;
   RecyclerView recyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RootView=inflater.inflate(R.layout.fragment_movies_list, container, false);
        recyclerView= RootView.findViewById(R.id.recycler_view);
        //ToDo show GridView of Movies
        return RootView;
    }

}
