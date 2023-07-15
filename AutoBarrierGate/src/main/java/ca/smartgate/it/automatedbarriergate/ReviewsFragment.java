package ca.smartgate.it.automatedbarriergate;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;
import java.util.List;

public class ReviewsFragment extends Fragment {
    private EditText editTextReview;
    private Button buttonSubmit;
    private ListView listViewReviews;
    private List<String> reviewsList;
    private ArrayAdapter<String> reviewsAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_reviews, container, false);

        // Initialize UI components
        RatingBar ratingBar = rootView.findViewById(R.id.ratingBar);
        editTextReview = rootView.findViewById(R.id.editTextReview);
        buttonSubmit = rootView.findViewById(R.id.buttonSubmit);

        // Submit button click listener
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String review = editTextReview.getText().toString();
                if (review.isEmpty()) {
                    Toast.makeText(getActivity(), "Feel free to write a review", Toast.LENGTH_SHORT).show();
                }
                float rating = ratingBar.getRating();
                Toast.makeText(getActivity(), "Thank you for your review!", Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }
}