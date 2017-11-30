package sa.thiqah.emanbasahel.popularmovies_1.helpers;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import sa.thiqah.emanbasahel.popularmovies_1.R;

/**
 * Created by emanbasahel on 30/11/2017 AD.
 */


public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    ImageView imgMovie;
    TextView txtMovieTitle;
    public class MovieViewHolder extends RecyclerView.ViewHolder
    {
        public MovieViewHolder (View view)
        {
            super(view);
            imgMovie= view.findViewById(R.id.img_movie);
            txtMovieTitle= view.findViewById(R.id.txt_movie_title);
        }
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View movieViewHolder= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_movie,parent,false);
        MovieViewHolder viewHolder = new MovieViewHolder(movieViewHolder);
        return viewHolder ;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


}
