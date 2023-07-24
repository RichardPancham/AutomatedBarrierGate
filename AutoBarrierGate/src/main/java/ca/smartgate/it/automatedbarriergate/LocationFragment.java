//Jaspreet Heer n01315290, Richard Pancham n01373454, Section 0NA

package ca.smartgate.it.automatedbarriergate;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;

public class LocationFragment extends Fragment {

    private FirebaseFirestore db;
    private RadioGroup radioGroup;
    private Button selectButton;

    private TimePicker timePicker;

    private NumberPicker numberPicker;
    public int x;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get a reference to the Firestore database
        db = FirebaseFirestore.getInstance();

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location, container, false);

        numberPicker = view.findViewById(R.id.numberPicker);
        radioGroup = view.findViewById(R.id.radioGroup);
        selectButton = view.findViewById(R.id.button7);
        timePicker = view.findViewById(R.id.timePicker);

        //set min and max values for numberpicker
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(12);
        numberPicker.setValue(1);

        // Set a listener to update the selected number TextView
        numberPicker.setOnValueChangedListener((picker, oldVal, newVal) -> {
            x=newVal;
        });


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

                //Getting Time
                int hour = timePicker.getCurrentHour();
                int minute = timePicker.getCurrentMinute();

                // Handle the selected time here
                String selectedTime = hour + ":" + minute;
                Toast.makeText(getActivity(), "Selected time: " + selectedTime, Toast.LENGTH_SHORT).show();
                //openPaymentFragment(1, 2, 3);
            }
        });

        return view;
    }

}