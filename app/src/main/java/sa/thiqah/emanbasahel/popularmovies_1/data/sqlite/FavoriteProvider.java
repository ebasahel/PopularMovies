package sa.thiqah.emanbasahel.popularmovies_1.data.sqlite;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by emanbasahel on 29/01/2018 AD.
 */

public class FavoriteProvider extends ContentProvider {
    private static final int FAVORITE_MOVIES = 10;
    private static final int FAVORITE_MOVIE_ID = 20;

    private static UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    FavoritesDatabase favoritesDatabase;


    static {
        uriMatcher.addURI(FavoritesContract.CONTENT_AUTHORITY,FavoritesContract.FavoriteMovies.PATH_FAVORITE,FAVORITE_MOVIES);
        uriMatcher.addURI(FavoritesContract.CONTENT_AUTHORITY,FavoritesContract.FavoriteMovies.PATH_FAVORITE+"/#",FAVORITE_MOVIE_ID);
    }

    @Override
    public boolean onCreate() {
        favoritesDatabase = new FavoritesDatabase(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase database = favoritesDatabase.getReadableDatabase();
        Cursor cursor = null;

        int match = uriMatcher.match(uri);

        switch (match){
            case FAVORITE_MOVIES:
                cursor = database.query(FavoritesContract.FavoriteMovies.TABLE_NAME,
                        null,null,null,null,null,null);
                break;

            case FAVORITE_MOVIE_ID:
                cursor = database.query(FavoritesContract.FavoriteMovies.TABLE_NAME,
                        projection,selection,selectionArgs,null,null,sortOrder);

        }

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        int match = uriMatcher.match(uri);
        switch (match){
            case FAVORITE_MOVIES:
                return FavoritesContract.FavoriteMovies.CONTENT_LIST_TYPE;

            case FAVORITE_MOVIE_ID:
                return FavoritesContract.FavoriteMovies.CONTENT_ITEM_TYPE;

            default:
                throw new IllegalStateException("Unknown Uri " + uri + " with match" + match);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        if (uriMatcher.match(uri) != FAVORITE_MOVIES)
            throw new IllegalArgumentException("Unsupported Uri for insertion " + uri);

        SQLiteDatabase database = favoritesDatabase.getWritableDatabase();
        long rowId = database.insert(FavoritesContract.FavoriteMovies.TABLE_NAME,null,contentValues);
        if (rowId != -1)
            return ContentUris.withAppendedId(FavoritesContract.FavoriteMovies.CONTENT_URI,rowId);

        //if there is a problem
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase database = favoritesDatabase.getWritableDatabase();
        int deletedRows = 0;
        int match = uriMatcher.match(uri);

        switch (match){
            case FAVORITE_MOVIES:
                deletedRows = database.delete(FavoritesContract.FavoriteMovies.TABLE_NAME,selection,selectionArgs);
                break;

            case FAVORITE_MOVIE_ID:
                deletedRows = database.delete(FavoritesContract.FavoriteMovies.TABLE_NAME,selection,selectionArgs);
                break;
        }
        return deletedRows;

    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase database = favoritesDatabase.getWritableDatabase();
        int effectedRows = 0;
        effectedRows = database.update(FavoritesContract.FavoriteMovies.TABLE_NAME,contentValues,selection,selectionArgs);
        return effectedRows;
    }
}
