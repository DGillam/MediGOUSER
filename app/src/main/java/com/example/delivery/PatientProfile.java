package com.example.delivery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.delivery.Model.User;

public class PatientProfile extends AppCompatActivity {

    private User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_profile);

        Intent intent = getIntent();

        //get the attached bundle from the intent
        Bundle extras = intent.getExtras();

        //Extracting the stored data from the bundle
        this.user.setEmail(extras.getString("USER_NAME"));
        this.user.setFirstName(extras.getString("FIRST_NAME"));
        this.user.setLastName(extras.getString("LAST_NAME"));
        this.user.setAddress(extras.getString("ADDRESS"));
        this.user.setPharmacy(extras.getString("PHARMACY"));
        this.user.setBirthday(extras.getString("BIRTHDAY"));

        TextView name = findViewById(R.id.profileName);
        name.setText((CharSequence) user.getFirstName() + " " + user.getLastName());

        TextView email = findViewById(R.id.profileEmail);
        email.setText((CharSequence)"Email: " + user.getEmail());

        TextView birthday = findViewById(R.id.birthday);
        birthday.setText((CharSequence) "Birth Date: " + user.getBirthday());

        TextView address = findViewById(R.id.address);
        address.setText((CharSequence)"Address: " + user.getAddress());

        TextView pharmacy = findViewById(R.id.pharmacy);
        pharmacy.setText((CharSequence)"Pharmacy: " + user.getPharmacy());

    }
}