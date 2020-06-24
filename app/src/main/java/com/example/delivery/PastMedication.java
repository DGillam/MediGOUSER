package com.example.delivery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.delivery.Model.User;

import java.util.ArrayList;

public class PastMedication extends AppCompatActivity {

    private User user = new User();
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_medication);

        Intent intent = getIntent();
        //get the attached bundle from the intent
        Bundle extras = intent.getExtras();

        //Extracting the stored data from the bundle
        assert extras != null;
        this.user.setMedicinePast(extras.getStringArrayList("USER_PAST_MEDICINE"));

        lv = findViewById(R.id.list_past_medication);

        if (user.getMedicinePast() == null) {
            ArrayList<String> noMeds = new ArrayList<>();
            noMeds.add("No Past Medication");
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                    this, android.R.layout.simple_list_item_1, noMeds);
            lv.setAdapter(arrayAdapter);
        } else {
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                    this, android.R.layout.simple_list_item_1, user.getMedicinePast());

            lv.setAdapter(arrayAdapter);
        }
    }
}