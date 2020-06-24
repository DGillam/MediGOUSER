package com.example.delivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.delivery.Model.Deliveries;
import com.example.delivery.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class PastDeliveries extends AppCompatActivity {

    private User user = new User();
    private ListView lv;
    private ArrayList<Deliveries> listDeliveries = new ArrayList<>();
    ArrayList<String> dates = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_deliveries);

        Intent intent = getIntent();

        //get the attached bundle from the intent
        Bundle extras = intent.getExtras();

        //Extracting the stored data from the bundle
        this.user.setEmail(extras.getString("USER_NAME"));
        this.user.setDeliveries(extras.getStringArrayList("DELIVERIES"));

        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("deliveries")
                .whereArrayContains("emailPatients", user.getEmail())
                .whereEqualTo("current", false)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                listDeliveries.add(document.toObject(Deliveries.class));

                            }
                        }
                        for (Deliveries d: listDeliveries) {
                            String med = d.getMedicines().get(user.getEmail()).toString();
                            String med1= med.replace("[", " ");
                            String med2 = med1.replace("]", " ");

                            dates.add("Date of delivery: " + d.getDeliveryDate() + "\nMedication delivered: " + med2);
                        }
                        System.out.println(dates);
                        printList();
                    }
                });
    }

    public void printList() {
        lv = findViewById(R.id.listView);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, dates);

        lv.setAdapter(arrayAdapter);
    }
}