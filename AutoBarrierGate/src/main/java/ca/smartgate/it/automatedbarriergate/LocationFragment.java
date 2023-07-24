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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LocationFragment extends Fragment {

    private FirebaseFirestore db;
    private RadioGroup radioGroup;
    private RadioButton radio1, radio2, radio3, radio4, radio5;
    private Button selectButton;

    private TimePicker timePicker;

    private NumberPicker numberPicker;
    public int p1, p2, p3, p4, p5;;


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




        // Get references to RadioGroup, RadioButtons, and the select button
        radioGroup = view.findViewById(R.id.radioGroup);
        radio1 = view.findViewById(R.id.radioButton);
        radio2 = view.findViewById(R.id.radioButton2);
        radio3 = view.findViewById(R.id.radioButton3);
        radio4 = view.findViewById(R.id.radioButton4);
        radio5 = view.findViewById(R.id.radioButton5);
        selectButton = view.findViewById(R.id.button7);

        // Retrieve data from Firestore
        DocumentReference docRef = db.collection("ParkingSpots").document("wn2O0AAGhcIUFk1Y328h");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        // Retrieve the string values from Firestore
                        p1 = Integer.parseInt(document.getString("p1"));
                        p2 = Integer.parseInt(document.getString("p2"));
                        p3 = Integer.parseInt(document.getString("p3"));
                        p4 = Integer.parseInt(document.getString("p4"));
                        p5 = Integer.parseInt(document.getString("p5"));

                    } else {
                        // Document does not exist
                        Toast.makeText(requireContext(), "Document does not exist", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Error occurred while retrieving data
                    Toast.makeText(requireContext(), "Error occurred while retrieving data", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Manually set parking spots that are free/taken
        //int p1=1;
        //int p2=1;
        //int p3=1;
        //int p4=0;
        //int p5=0;

        // listener to the RadioGroup to handle the check behavior based off vacant parking spots
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
                if (radioGroup.getCheckedRadioButtonId() != -1) {
                    // At least one RadioButton is checked, open another fragment
                    openAnotherFragment();
                } else {
                    // No RadioButton is checked, display a toast
                    displayToast();
                }

            }
        });

        return view;
    }
    private void openAnotherFragment() {
        PaymentFragment paymentFragment = new PaymentFragment();
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, paymentFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void displayToast() {
        Toast.makeText(requireContext(), "Please select a RadioButton", Toast.LENGTH_SHORT).show();
    }

}