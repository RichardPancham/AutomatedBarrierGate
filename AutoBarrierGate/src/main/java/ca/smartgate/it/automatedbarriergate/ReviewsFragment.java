package ca.smartgate.it.automatedbarriergate;
import android.annotation.SuppressLint;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ReviewsFragment extends Fragment {
    private EditText editTextReview;
    private EditText editTextName;
    private Button buttonSubmit;

    private ArrayAdapter<String> reviewsAdapter;
    private FirebaseFirestore db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get a reference to the Firebase Realtime Database
        db = FirebaseFirestore.getInstance();
    }

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_reviews, container, false);

        // Initialize UI components
        RatingBar ratingBar = rootView.findViewById(R.id.ratingBar);
        editTextReview = rootView.findViewById(R.id.editTextReview);
        editTextName=rootView.findViewById(R.id.editTextName);
        buttonSubmit = rootView.findViewById(R.id.buttonSubmit);

        // Submit button click listener
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String review1 = editTextReview.getText().toString();
                float rating = ratingBar.getRating();
                String name = editTextName.getText().toString();

                //Database Review Push
                Map<String, Object> reviewData = new HashMap<>();
                reviewData.put("rating", rating);
                reviewData.put("review", review1);
                reviewData.put("name", name);

                db.collection("reviews").add(reviewData)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(getActivity(), "Thank you for your review!", Toast.LENGTH_LONG).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getActivity(), "Failed to submit review.", Toast.LENGTH_SHORT).show();
                            }
                        });
                //Toast.makeText(getActivity(), "Thank you for your review!", Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }
}