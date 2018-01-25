package sa.thiqah.emanbasahel.popularmovies_1.helpers;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import sa.thiqah.emanbasahel.popularmovies_1.R;
import sa.thiqah.emanbasahel.popularmovies_1.data.model.Result;
import sa.thiqah.emanbasahel.popularmovies_1.views.MovieDetails;

/**
 * Created by emanbasahel on 30/11/2017 AD.
 */


public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    public interface OnItemClickListener {

        void onItemClick(Result itemResult);

    }


    //region variables
    private List<Result> movieList;
    private final OnItemClickListener listener;
    private Cursor mCursor;

    //endregion
    public MovieAdapter ( List<Result> mList,OnItemClickListener mListener)
    {
        movieList=mList;
        listener=mListener;
    }

    public MovieAdapter (Cursor cursor, OnItemClickListener mListener)
    {
        mCursor=cursor;
        listener=mListener;
    }
    //region ViewHolder Class
    class MovieViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imgMovie;
        TextView txtMovieTitle;
         MovieViewHolder (View view)
        {
            super(view);
            imgMovie= view.findViewById(R.id.img_movie);
            txtMovieTitle= view.findViewById(R.id.txt_movie_title);
        }

         void bind(final Result itemResult, final OnItemClickListener listener) {

            txtMovieTitle.setText(itemResult.getTitle());
            String imgURL = "http://image.tmdb.org/t/p/w185//" + itemResult.getPosterPath();
            Picasso.with(itemView.getContext()).load(imgURL).into(imgMovie);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(itemResult);
                }
            });
        }
    }
    //endregion

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View movieViewHolder= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_movie,parent,false);
        return new MovieViewHolder(movieViewHolder);
    }

    @Override
    public void onBindViewHolder(final MovieViewHolder holder, int position) {
        holder.bind(movieList.get(position),listener);

    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }


}
