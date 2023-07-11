package ca.smartgate.it.automatedbarriergate;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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
        editTextReview = rootView.findViewById(R.id.editTextReview);
        buttonSubmit = rootView.findViewById(R.id.buttonSubmit);
        listViewReviews = rootView.findViewById(R.id.listViewReviews);

        // Initialize reviews list and adapter
        reviewsList = new ArrayList<>();
        reviewsAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, reviewsList);
        listViewReviews.setAdapter(reviewsAdapter);

        // Submit button click listener
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String review = editTextReview.getText().toString();
                if (!review.isEmpty()) {
                    reviewsList.add(review);
                    reviewsAdapter.notifyDataSetChanged();
                    editTextReview.getText().clear();
                }
            }
        });

        return rootView;
    }
}