package sa.thiqah.emanbasahel.popularmovies_1.views;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sa.thiqah.emanbasahel.popularmovies_1.BuildConfig;
import sa.thiqah.emanbasahel.popularmovies_1.R;
import sa.thiqah.emanbasahel.popularmovies_1.data.model.MovieReviews;
import sa.thiqah.emanbasahel.popularmovies_1.data.model.ReviewsResponse;
import sa.thiqah.emanbasahel.popularmovies_1.data.webservice.ApiClient;
import sa.thiqah.emanbasahel.popularmovies_1.data.webservice.ApiInterface;
import sa.thiqah.emanbasahel.popularmovies_1.helpers.ReviewAdapter;


public class ReviewsFragment extends Fragment {


    //region variables
    View RootView;
    private static final String API_KEY = BuildConfig.api_key;
    private int movieId;
    private RecyclerView reviewRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ReviewAdapter reviewAdapter;
    //endregion

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RootView =inflater.inflate(R.layout.fragment_reviews, container, false);
        reviewRecyclerView= RootView.findViewById(R.id.review_recycler_view);
        Bundle bundle = getArguments();
        if (bundle!=null)
        {
            movieId=bundle.getInt(getString(R.string.movieId),0);
            getReviews();
        }

        return RootView;
    }

    //region call reviews
    private void getReviews()
    {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<MovieReviews> call = apiService.getMovieReviews(movieId,API_KEY);
        call.enqueue(new Callback<MovieReviews>() {
            @Override
            public void onResponse(@NonNull Call<MovieReviews> call, @NonNull Response<MovieReviews> response) {
                List<ReviewsResponse> reviewsList=response.body().getResults();
                //region add layout if there're no reviews
                if (reviewsList.size()==0)
                {
                    NoReviews fragmentNoReviews = new NoReviews();
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentManager.popBackStack();
                    fragmentTransaction.replace(R.id.container,fragmentNoReviews);
                    fragmentTransaction.commit();

                }//endregion

                //if there'are reviews show them in a list
                else
                    createRecyclerView(reviewsList);
            }

            @Override
            public void onFailure(@NonNull Call<MovieReviews> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), getString(R.string.noData), Toast.LENGTH_LONG).show();
                getActivity().finish();
            }
        });
    }
    //endregion

    //region init RecyclerView
    private void createRecyclerView(List<ReviewsResponse> reviewsList)
    {
        mLayoutManager = new LinearLayoutManager(getActivity());
        reviewRecyclerView.setLayoutManager(mLayoutManager);
        reviewRecyclerView.setItemAnimator(new DefaultItemAnimator());
        reviewAdapter= new ReviewAdapter(reviewsList);
        reviewRecyclerView.setAdapter(reviewAdapter);
    }
    //endregion

}
