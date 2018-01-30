package sa.thiqah.emanbasahel.popularmovies_1.data.model;

/**
 * Created by emanbasahel on 25/01/2018 AD.
 */

public class FavoriteMovieModel {

    public String title;
    public String imgPath;
    public int movieId;

    public void setTitle (String _title)
    {
        title=_title;
    }

    public void setImgPath (String _imgPath)
    {
        imgPath=_imgPath;
    }

    public String getTitle ()
    {
        return title;
    }

    public String getImgPath ()
    {
        return imgPath;
    }

    public void setMovieId(int _movieID)
    {
        movieId=_movieID;
    }

    public int getMovieId()
    {
        return movieId;
    }
}
