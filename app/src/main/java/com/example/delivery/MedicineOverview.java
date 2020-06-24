package com.example.delivery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.delivery.Model.User;

public class MedicineOverview extends AppCompatActivity {

    private Button current;
    private Button past;
    User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_overview);

        Intent intent = getIntent();
        //get the attached bundle from the intent
        Bundle extras = intent.getExtras();

        //Extracting the stored data from the bundle
        this.user.setMedicineCurrent(extras.getStringArrayList("USER_MEDICINE"));
        this.user.setMedicinePast(extras.getStringArrayList("USER_PAST_MEDICINE"));

        current = findViewById(R.id.current);
        current.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCurrentMedication();
            }
        });

        past = findViewById(R.id.past);
        past.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPastMedication();
            }
        });
    }

    public void openCurrentMedication() {
        Bundle extras = new Bundle();

        //passing data to the next Activity
        extras.putStringArrayList("USER_MEDICINE",user.getMedicineCurrent());

        Intent intent = new Intent(this, CurrentMedication.class);
        intent.putExtras(extras);
        startActivity(intent);
    }

    public void openPastMedication() {
        Bundle extras = new Bundle();

        //passing data to the next Activity
        extras.putStringArrayList("USER_PAST_MEDICINE",user.getMedicinePast());

        Intent intent = new Intent(this, PastMedication.class);
        intent.putExtras(extras);
        startActivity(intent);
    }
}