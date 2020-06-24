package com.example.delivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.delivery.Model.Deliveries;
import com.example.delivery.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class FollowDelivery extends AppCompatActivity {

    private Deliveries deliveries;
    String email;
    private User user = new User();
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_delivery);

        Intent intent = getIntent();

        //get the attached bundle from the intent
        Bundle extras = intent.getExtras();

        //Extracting the stored data from the bundle
        this.user.setEmail(extras.getString("USER_NAME"));


       textView = findViewById(R.id.nextDelivery);
       textView.setText((CharSequence) "There is no delivery Scheduled");

        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("deliveries")
                .whereArrayContains("emailPatients", user.getEmail())
                .whereEqualTo("current", true)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                deliveries = document.toObject(Deliveries.class);

                                textView.setText((CharSequence) "Your next delivery will be: " + deliveries.getDeliveryDate());
                            }
                        }
                    }
                });
    }
}
