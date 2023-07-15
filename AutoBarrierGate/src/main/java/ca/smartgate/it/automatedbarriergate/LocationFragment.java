//Jaspreet Heer n01315290, Richard Pancham n01373454, Section 0NA

package ca.smartgate.it.automatedbarriergate;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;

public class LocationFragment extends Fragment {

    private FirebaseFirestore db;
    private RadioGroup radioGroup;
    private Button selectButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = FirebaseFirestore.getInstance();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location, container, false);

        radioGroup = view.findViewById(R.id.radioGroup);
        selectButton = view.findViewById(R.id.button7);

        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();
                if (selectedRadioButtonId != -1) {
                    // At least one radio button is selected
                    RadioButton selectedRadioButton = view.findViewById(selectedRadioButtonId);
                    String selectedOption = selectedRadioButton.getText().toString();
                    Toast.makeText(getActivity(), "Selected Option: " + selectedOption, Toast.LENGTH_SHORT).show();
                    BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView);
                    bottomNavigationView.setSelectedItemId(R.id.payment);
                } else {
                    // No radio button is selected
                    Toast.makeText(getActivity(), "Please select an option", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}