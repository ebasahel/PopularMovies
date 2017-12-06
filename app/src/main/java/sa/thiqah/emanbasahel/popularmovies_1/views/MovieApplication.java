package sa.thiqah.emanbasahel.popularmovies_1.views;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by emanbasahel on 06/12/2017 AD.
 */

public class MovieApplication extends Application {

    @Override
    public void onCreate ()
    {
        super.onCreate();

        if (!isOnline())
        {
            android.os.Process.killProcess(android.os.Process.myPid());

        }
    }

    public boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
}
