package com.example.delivery;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.example.delivery.ChangePassword;
import com.example.delivery.Model.Caretakers;
import com.example.delivery.Model.User;
import com.example.delivery.R;
import com.example.delivery.UserSelect;

public class ProfileCare extends AppCompatActivity {

    private Button logout;
    private Button openChangeP;
    private Button patientProfile;
    private User user = new User();
    private Caretakers caretaker = new Caretakers();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_care);

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

        //CARETAKER
        this.caretaker.setEmail(extras.getString("NAME"));
        this.caretaker.setFirstName(extras.getString("FIRSTNAME"));
        this.caretaker.setLastName(extras.getString("LASTNAME"));
        this.caretaker.setBirthday(extras.getString("BIRTHDAY"));
        this.caretaker.setPassword(extras.getString("PASSWORD"));
        this.caretaker.setPatients(extras.getStringArrayList("PATIENTS"));

        logout = findViewById(R.id.Logout);
        logout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                LogOut();
            }
        });

        openChangeP = findViewById(R.id.changeP);
        openChangeP.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                openChangePwd();
            }
        });

        patientProfile = findViewById(R.id.patientProfile);
        patientProfile.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                openPatientProfile();
            }
        });

        TextView name = findViewById(R.id.profileName);
        name.setText((CharSequence) caretaker.getFirstName() + " " + caretaker.getLastName());

        TextView email = findViewById(R.id.profileEmail);
        email.setText((CharSequence)"Email: " + caretaker.getEmail());

        TextView birthday = findViewById(R.id.birthday);
        birthday.setText((CharSequence) "Birth Date: " + caretaker.getBirthday());
    }

    public void LogOut() {
        Intent intent = new Intent(this, UserSelect.class);
        startActivity(intent);
    }

    public void openChangePwd() {
        Bundle extras = new Bundle();

        //passing data to the next Activity
        extras.putString("NAME", caretaker.getEmail());
        extras.putString("FIRSTNAME", caretaker.getFirstName());
        extras.putString("LASTNAME",caretaker.getLastName());
        extras.putString("BIRTHDAY", caretaker.getBirthday());
        extras.putString("PASSWORD", caretaker.getPassword());
        extras.putStringArrayList("PATIENTS", caretaker.getPatients());

        Intent intent = new Intent(this, ChangePasswordCare.class);
        intent.putExtras(extras);
        startActivity(intent);
    }

    public void openPatientProfile() {
        Bundle extras = new Bundle();

        //passing data to the next Activity
        extras.putString("USER_NAME",user.getEmail());
        extras.putString("FIRST_NAME", user.getFirstName());
        extras.putString("LAST_NAME", user.getLastName());
        extras.putString("PHARMACY", user.getPharmacy());
        extras.putString("ADDRESS", user.getAddress());
        extras.putString("BIRTHDAY", user.getBirthday());

        Intent intent = new Intent(this, PatientProfile.class);
        intent.putExtras(extras);
        startActivity(intent);
    }
}