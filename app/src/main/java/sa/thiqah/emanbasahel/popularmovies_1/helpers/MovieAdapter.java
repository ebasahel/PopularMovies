package sa.thiqah.emanbasahel.popularmovies_1.helpers;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import sa.thiqah.emanbasahel.popularmovies_1.R;
import sa.thiqah.emanbasahel.popularmovies_1.data.model.Result;
import sa.thiqah.emanbasahel.popularmovies_1.views.MovieDetails;

/**
 * Created by emanbasahel on 30/11/2017 AD.
 */


public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {


    private List<Result> movieList;
    private Context mContext;
    private String imgURL;
    private int movieId;

    public MovieAdapter (Context _context, List<Result> mList)
    {
        mContext = _context;
        movieList=mList;
    }
    public class MovieViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imgMovie;
        TextView txtMovieTitle;
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
    public void onBindViewHolder(MovieViewHolder holder, final int position) {
        holder.txtMovieTitle.setText(movieList.get(position).getTitle());
        imgURL = "http://image.tmdb.org/t/p/w185//"+ movieList.get(position).getPosterPath();
        Picasso.with(mContext).load(imgURL).into(holder.imgMovie);
        holder.imgMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                movieId= movieList.get(position).getId();
                Intent intent = new Intent(mContext,MovieDetails.class);
                intent.putExtra(mContext.getString(R.string.movieId),movieId);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }


}
