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
    private RadioButton radio1, radio2, radio3, radio4, radio5;
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


        selectButton = view.findViewById(R.id.button7);
        // Get references to RadioGroup and RadioButtons
        radioGroup = view.findViewById(R.id.radioGroup);
        radio1 = view.findViewById(R.id.radioButton);
        radio2 = view.findViewById(R.id.radioButton2);
        radio3 = view.findViewById(R.id.radioButton3);
        radio4 = view.findViewById(R.id.radioButton4);
        radio5 = view.findViewById(R.id.radioButton5);


        int p1=1;
        int p2=1;
        int p3=1;
        int p4=0;
        int p5=0;
        // Set listener to the RadioGroup to handle the check behavior
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioButton && p1 == 0) {
                    radio1.setChecked(false);
                } else if (checkedId == R.id.radioButton2 && p2 == 0) {
                    radio2.setChecked(false);
                } else if (checkedId == R.id.radioButton3 && p3 == 0) {
                    radio3.setChecked(false);
                } else if (checkedId == R.id.radioButton4 && p4 == 0) {
                    radio4.setChecked(false);
                } else if (checkedId == R.id.radioButton5 && p5 == 0) {
                    radio5.setChecked(false);
                }
            }
        });


        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();


                //Getting Time
           //     int hour = timePicker.getCurrentHour();
             //   int minute = timePicker.getCurrentMinute();

                // Handle the selected time here
               // String selectedTime = hour + ":" + minute;
                //Toast.makeText(getActivity(), "Selected time: " + selectedTime, Toast.LENGTH_SHORT).show();
                //openPaymentFragment(1, 2, 3);
            }
        });

        return view;
    }

}