package sa.thiqah.emanbasahel.popularmovies_1.helpers;


import android.content.Context;
import android.content.SharedPreferences;

import sa.thiqah.emanbasahel.popularmovies_1.R;

/**
 * Created by emanbasahel on 03/01/2018 AD.
 */

public class MoviePreferences  {
    SharedPreferences.Editor editor;
    static MoviePreferences moviePreferences;
    private static Context mContext;
    private int movieID;

    private MoviePreferences (){}


    public static MoviePreferences getInstance(Context context)
    {
        mContext=context;
        if(moviePreferences==null)
            moviePreferences =new MoviePreferences();

        return moviePreferences;
    }

    public SharedPreferences getPreferences(Context context)
    {
        SharedPreferences mPref = context.getApplicationContext().getSharedPreferences(context.getString(R.string.movie_pref),0);
        mContext=context;
        editor = mPref.edit();
        return mPref;
    }
    public void setFavMovie (int movieId)
    {
        editor.putInt(mContext.getString(R.string.favorite_movie), movieId);
    }

//    public int getFavMovie ()
//    {
//        return
//    }
}
