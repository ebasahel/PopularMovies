package sa.thiqah.emanbasahel.popularmovies_1.helpers;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import sa.thiqah.emanbasahel.popularmovies_1.R;
import sa.thiqah.emanbasahel.popularmovies_1.data.model.FavoriteMovieModel;
import sa.thiqah.emanbasahel.popularmovies_1.data.sqlite.FavoritesContract;

/**
 * Created by emanbasahel on 25/01/2018 AD.
 */

public class FavoriteMovieAdapter extends RecyclerView.Adapter<FavoriteMovieAdapter.FavoriteMovieViewHolder> {

    public interface OnItemClickListener {

        void onItemClick(Cursor itemResult);
    }

    //region variables
    private final OnItemClickListener listener;
    private Cursor mCursor;

    //endregion

    public FavoriteMovieAdapter (Cursor cursor, OnItemClickListener mListener)
    {
        mCursor=cursor;
        listener=mListener;
    }
    //region ViewHolder Class
    class FavoriteMovieViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imgMovie;
        TextView txtMovieTitle;
        FavoriteMovieViewHolder (View view)
        {
            super(view);
            imgMovie= view.findViewById(R.id.img_movie);
            txtMovieTitle= view.findViewById(R.id.txt_movie_title);
        }

        //region bind method
        void bind(final FavoriteMovieModel itemResult, final OnItemClickListener listener) {

            txtMovieTitle.setText(itemResult.getTitle());
            Picasso.with(itemView.getContext()).load(itemResult.getImgPath()).into(imgMovie);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(mCursor);
                }
            });
        }
       //endregion
    }
    //endregion

    @Override
    public FavoriteMovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View movieViewHolder= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_movie,parent,false);
        return new FavoriteMovieViewHolder(movieViewHolder);
    }

    @Override
    public void onBindViewHolder(final FavoriteMovieViewHolder holder, int position) {

        if (!mCursor.moveToPosition(position))
            return;

        FavoriteMovieModel favoriteMovieModel = new FavoriteMovieModel();
        favoriteMovieModel.setTitle(mCursor.getString(mCursor.getColumnIndex(FavoritesContract.FavoriteMovies.COLUMN_NAME_TITLE)));
        favoriteMovieModel.setImgPath(mCursor.getString(mCursor.getColumnIndex(FavoritesContract.FavoriteMovies.COLUMN_NAME_IMAGE_PATH)));
        holder.bind(favoriteMovieModel,listener);

    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }


}
