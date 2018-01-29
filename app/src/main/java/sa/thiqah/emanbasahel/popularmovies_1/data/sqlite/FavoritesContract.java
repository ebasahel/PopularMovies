package sa.thiqah.emanbasahel.popularmovies_1.data.sqlite;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by emanbasahel on 03/01/2018 AD.
 */

public final class FavoritesContract {
    static final String CONTENT_AUTHORITY = "sa.thiqah.emanbasahel.popularmovies_1.data.sqlite";
    static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    private FavoritesContract(){}

    public static class FavoriteMovies implements BaseColumns
    {
        public static final String PATH_FAVORITE = "favorites";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI,PATH_FAVORITE);
        public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FAVORITE;
        static final String CONTENT_ITEM_TYPE = ContentResolver.ANY_CURSOR_ITEM_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FAVORITE;

        public static final String TABLE_NAME ="favorites";
        public static final String COLUMN_NAME_ID = "movie_id";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_IMAGE_PATH = "image";
        public static final String COLUMN_NAME_TIMESTAMP = "timestamp";
    }
}
