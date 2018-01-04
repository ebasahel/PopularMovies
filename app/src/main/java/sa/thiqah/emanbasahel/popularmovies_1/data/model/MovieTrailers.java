package sa.thiqah.emanbasahel.popularmovies_1.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by emanbasahel on 03/01/2018 AD.
 */

public class MovieTrailers {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("results")
    @Expose
    private List<TrailersResult> results = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<TrailersResult> getResults() {
        return results;
    }

    public void setResults(List<TrailersResult> results) {
        this.results = results;
    }
}
