//Jaspreet Heer n01315290, Richard Pancham n01373454, Section 0NA

package ca.smartgate.it.automatedbarriergate;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class PaymentFragment extends Fragment {

    private EditText cardNumberEditText;
    private EditText expiryDateEditText;
    private EditText cvvEditText;
    private Button validateButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment, container, false);

        cardNumberEditText = view.findViewById(R.id.editTextNumber);
        expiryDateEditText = view.findViewById(R.id.editTextTextPassword2);
        cvvEditText = view.findViewById(R.id.editTextNumberPassword);
        validateButton = view.findViewById(R.id.button3);

        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cardNumber = cardNumberEditText.getText().toString().trim();
                String expiryDate = expiryDateEditText.getText().toString().trim();
                String cvv = cvvEditText.getText().toString().trim();

                // Perform credit card validation
                if (isValidCard(cardNumber) && isValidExpiryDate(expiryDate) && isValidCVV(cvv)) {
                    Toast.makeText(getActivity(), "Credit card is valid!", Toast.LENGTH_SHORT).show();
                    BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView);
                    bottomNavigationView.setSelectedItemId(R.id.open_barrier);

                } else {
                    Toast.makeText(getActivity(), "Invalid credit card details!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private boolean isValidCard(String cardNumber) {
        // Add your credit card validation logic here
        // Return true if the card number is valid, otherwise false
        // Example validation logic:
        return cardNumber.length() == 16;
    }

    private boolean isValidExpiryDate(String expiryDate) {
        // Add your expiry date validation logic here
        // Return true if the expiry date is valid, otherwise false
        // Example validation logic:
        return expiryDate.matches("\\d{2}/\\d{2}"); // Matches the pattern MM/YY
    }

    private boolean isValidCVV(String cvv) {
        // Add your CVV validation logic here
        // Return true if the CVV is valid, otherwise false
        // Example validation logic:
        return cvv.length() == 3;


    }
}