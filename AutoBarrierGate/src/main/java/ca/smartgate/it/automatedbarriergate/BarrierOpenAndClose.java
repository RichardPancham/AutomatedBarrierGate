package ca.smartgate.it.automatedbarriergate;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BarrierOpenAndClose#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BarrierOpenAndClose extends Fragment {
    private static final int DELAY_MILLIS = 5000; // 5 seconds

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    String openBreakBeam = null;
    String closeBreakBeam = null;

    public BarrierOpenAndClose() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BarrierOpenAndClose.
     */
    // TODO: Rename and change types and number of parameters
    public static BarrierOpenAndClose newInstance(String param1, String param2) {
        BarrierOpenAndClose fragment = new BarrierOpenAndClose();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }




    private FirebaseFirestore db;
    private TextView textView;
    private TextView textView2;
    private ImageView imageView;
    private boolean isImage1Shown = true;

    private Handler handler;
    private HandlerThread handlerThread;
    private boolean isFetching = true; // To control the continuous data fetch

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_barrier_open_and_close, container, false);
        imageView = view.findViewById(R.id.imageView4);

        Button myButton = view.findViewById(R.id.button2);
        db = FirebaseFirestore.getInstance();
        textView = view.findViewById(R.id.textView3);
        textView2 = view.findViewById(R.id.textView2);

        // Create a background thread and handler for continuous fetch
        handlerThread = new HandlerThread("DataFetchThread");
        handlerThread.start();
        handler = new Handler(handlerThread.getLooper());

        // Start the continuous fetch
        handler.post(fetchDataRunnable);

        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toggle the image and start a 5-second delay
                if(openBreakBeam.equals("BreakBeam 1 Broken")||closeBreakBeam.equals("BreakBeam 2 Broken") ) {
                    //sendDataToFirebase();
                    toggleImage();
                    Toast.makeText(requireContext(), "gate open", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(requireContext(), "Please readjust your vehicle ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private Runnable fetchDataRunnable = new Runnable() {
        @Override
        public void run() {
            if (isFetching) {
                fetchDataFromFirestore();
                // Continue the data fetch loop
                handler.postDelayed(this, 1);
            }
        }
    };
    private void sendDataToFirebase(){
        String data = "status:open";
        db.collection("BarrierGate")
                .document("MtjpzgC4fsB9Kym0CoGQ ")
                .set(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Data was successfully added
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle errors
                    }
                });


    }

    private void fetchDataFromFirestore() {
        //BreakBeamEntry
        db.collection("BreakBeamEntry")
                .document("XHSxFPz9fVCtEaxMl6v5")
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            String data = documentSnapshot.getString("sensor_status");
                            openBreakBeam = data;
                            textView.setText(data);

                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle any errors that occurred during the fetching process
                    }
                });

        //BreakBeamExit
        db.collection("BreakBeamExit")
                .document("78abDHeKCx2OFvLC9WEa")
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            String data = documentSnapshot.getString("sensor_status");
                            closeBreakBeam=data;
                            textView2.setText(data);

                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle any errors that occurred during the fetching process
                    }
                });


    }

    private void toggleImage() {
        // Simulate a 10-second delay and show a toast after the delay
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                // Code to be executed after the 10-second delay
                Toast.makeText(requireContext(), "5 seconds delay completed!", Toast.LENGTH_SHORT).show();
                imageView.setImageResource(R.drawable.screenshot_2023_07_23_at_10_17_22_pm);
            }
        }, DELAY_MILLIS);
        //Switch Picture back to Closed gate
        //toggleImage();
        if (isImage1Shown) {
            imageView.setImageResource(R.drawable.screenshot_2023_07_23_at_10_17_28_pm);
        } else {
            imageView.setImageResource(R.drawable.screenshot_2023_07_23_at_10_17_22_pm);
            isImage1Shown = !isImage1Shown;
        }

    }


}
