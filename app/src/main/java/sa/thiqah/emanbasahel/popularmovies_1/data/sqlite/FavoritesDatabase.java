package sa.thiqah.emanbasahel.popularmovies_1.data.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by emanbasahel on 03/01/2018 AD.
 */

public class FavoritesDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="favoritemovies.db";
    private static final int DATABASE_VERSION=1;

    public FavoritesDatabase (Context context)
    {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_FAVORITE_MOVIES_TABLE="CREATE TABLE "+
                FavoritesContract.FavoriteMovies.TABLE_NAME+ " ("+
                FavoritesContract.FavoriteMovies._ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                FavoritesContract.FavoriteMovies.COLUMN_NAME_ID+ " INTEGER NOT NULL,"+
                FavoritesContract.FavoriteMovies.COLUMN_NAME_TITLE+ " TEXT NOT NULL,"+
                FavoritesContract.FavoriteMovies.COLUMN_NAME_IMAGE_PATH+ " TEXT NOT NULL,"+
                FavoritesContract.FavoriteMovies.COLUMN_NAME_TIMESTAMP+ " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ");";

        sqLiteDatabase.execSQL(SQL_FAVORITE_MOVIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " +FavoritesContract.FavoriteMovies.TABLE_NAME);
        onCreate(sqLiteDatabase);

    }
}
