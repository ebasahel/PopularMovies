package sa.thiqah.emanbasahel.popularmovies_1.views.helpers;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by emanbasahel on 29/11/2017 AD.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    public class MovieViewHolder extends RecyclerView.ViewHolder
    {
        public MovieViewHolder (View view)
        {
            super(view);
            //ToDo item view
        }
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


}
