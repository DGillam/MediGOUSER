package com.example.delivery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.delivery.Model.User;

public class Profile extends AppCompatActivity {

    private Button logout;
    private Button openChangeP;
    private User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

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

        logout = findViewById(R.id.Logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogOut();
            }
        });

        openChangeP = findViewById(R.id.changeP);
        openChangeP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openChangePwd();
            }
        });

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

    public void LogOut() {
        Intent intent = new Intent(this, UserSelect.class);
        startActivity(intent);
    }

    public void openChangePwd() {
        Bundle extras = new Bundle();

        //passing data to the next Activity
        extras.putString("USER_NAME", user.getEmail());
        extras.putString("FIRST_NAME", user.getFirstName());
        extras.putString("LAST_NAME", user.getLastName());

        Intent intent = new Intent(this, ChangePassword.class);
        intent.putExtras(extras);
        startActivity(intent);
    }
}




