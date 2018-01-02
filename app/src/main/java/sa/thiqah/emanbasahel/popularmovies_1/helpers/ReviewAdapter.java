package sa.thiqah.emanbasahel.popularmovies_1.helpers;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import sa.thiqah.emanbasahel.popularmovies_1.R;
import sa.thiqah.emanbasahel.popularmovies_1.data.model.ReviewsResponse;

/**
 * Created by emanbasahel on 02/01/2018 AD.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewHolder> {

    private List<ReviewsResponse> reviewsResponse;

    public ReviewAdapter(List<ReviewsResponse> reviewsResponseList)
    {
        reviewsResponse=reviewsResponseList;
    }

    //region ViewHolder class
    class ReviewHolder extends RecyclerView.ViewHolder
    {
        TextView txtAuthor,txtReview;
        public ReviewHolder(View itemView) {
            super(itemView);
            txtAuthor= itemView.findViewById(R.id.txt_author);
            txtReview= itemView.findViewById(R.id.txt_review);
        }

        void bind (final ReviewsResponse reviewsResponseItem)
        {
            txtAuthor.setText(reviewsResponseItem.getAuthor());
            txtReview.setText(reviewsResponseItem.getContent());
        }
    }
    //endregion

    @Override
    public ReviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View reviewHolder= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_reviews,parent,false);
        return new ReviewHolder(reviewHolder);
    }

    @Override
    public void onBindViewHolder(ReviewHolder holder, int position) {
        holder.bind(reviewsResponse.get(position));

    }

    @Override
    public int getItemCount() {
        return reviewsResponse.size();
    }


}
