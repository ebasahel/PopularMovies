package sa.thiqah.emanbasahel.popularmovies_1.data.sqlite;

import android.provider.BaseColumns;

/**
 * Created by emanbasahel on 03/01/2018 AD.
 */

public final class FavoritesContract {
    private FavoritesContract(){}

    public static class FavoriteMovies implements BaseColumns
    {
        public static final String TABLE_NAME ="favorites";
        public static final String COLUMN_NAME_ID = "movie_id";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_TIMESTAMP = "timestamp";
    }
}
