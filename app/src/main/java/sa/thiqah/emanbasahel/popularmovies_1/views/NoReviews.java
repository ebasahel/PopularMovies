package sa.thiqah.emanbasahel.popularmovies_1.views;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sa.thiqah.emanbasahel.popularmovies_1.R;

public class NoReviews extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_no_reviews, container, false);
    }

}
