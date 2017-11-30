package sa.thiqah.emanbasahel.popularmovies_1.data.model

import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by emanbasahel on 30/11/2017 AD.
 */


@SuppressLint("ParcelCreator")
@Parcelize
class MovieModel : Parcelable {
    @SerializedName("page")
    @Expose
    var page: Int? = null
    @SerializedName("total_results")
    @Expose
    var totalResults: Int? = null
    @SerializedName("total_pages")
    @Expose
    var totalPages: Int? = null
    @SerializedName("results")
    @Expose
    var results: List<Result>? = null
}
