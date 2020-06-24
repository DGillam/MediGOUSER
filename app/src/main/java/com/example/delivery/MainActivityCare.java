package com.example.delivery;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.delivery.Model.Caretakers;
import com.example.delivery.Model.Security;
import com.example.delivery.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

//Encryption imports
import android.widget.Toast;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;

public class MainActivityCare extends AppCompatActivity {
    public Button calendar;
    public Button follow;
    public Button getPassword;
    public Button overview;
    public Button profile;
    public User user = new User();
    public Caretakers caretaker = new Caretakers();
    String pvt;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_care);

        Intent intent = getIntent();

        //get the attached bundle from the intent
        Bundle extras = intent.getExtras();

        //Extracting the stored data from the bundle
        //USER
        this.user.setEmail(extras.getString("USER_NAME"));
        this.user.setFirstName(extras.getString("USER_FIRSTNAME"));
        this.user.setLastName(extras.getString("USER_LASTNAME"));
        this.user.setBirthday(extras.getString("USER_BIRTHDAY"));
        this.user.setMedicineCurrent(extras.getStringArrayList("USER_MEDICINE"));
        this.user.setMedicinePast(extras.getStringArrayList("USER_PAST_MEDICINE"));
        this.user.setFirstLog(extras.getBoolean("FIRST_LOG"));
        this.user.setPastDelivery(extras.getString("PAST_DELIVERY"));
        this.user.setNextDelivery(extras.getString("NEXT_DELIVERY"));
        this.user.setPharmacy(extras.getString("PHARMACY"));
        this.user.setAddress(extras.getString("ADDRESS"));
        this.user.setDeliveries(extras.getStringArrayList("DELIVERIES"));

        //CARETAKER
        this.caretaker.setEmail(extras.getString("NAME"));
        this.caretaker.setFirstName(extras.getString("FIRSTNAME"));
        this.caretaker.setLastName(extras.getString("LASTNAME"));
        this.caretaker.setBirthday(extras.getString("BIRTHDAY"));
        this.caretaker.setPassword(extras.getString("PASSWORD"));
        this.caretaker.setPatients(extras.getStringArrayList("PATIENTS"));


        // Capture the layout's TextView and set the string as its text
        TextView textView = findViewById(R.id.helloUser);
        textView.setText((CharSequence) "Hello, " + caretaker.getLastName());

        calendar = findViewById(R.id.calendar);
        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCalendar();
            }
        });

        follow = findViewById(R.id.followDelivery);
        follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFollowDelivery();
            }
        });

        getPassword = findViewById(R.id.getPassword);
        getPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGetPassword();
            }
        });

        overview = findViewById(R.id.overviewMedication);
        overview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openOverview();
            }
        });

        profile = findViewById(R.id.profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProfile();
            }
        });

        if (user.getFirstLog()) {
            KeyPair key = null;

            try {
                key = Security.generateKeyPair();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

            Security.getPublicKey(key, user.getEmail());

            pvt = Security.getPrivateKey(key);

            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("privateKey", pvt);
            editor.commit();

            final FirebaseFirestore db = FirebaseFirestore.getInstance();

            DocumentReference changeFirstLog = db.collection("patients").document(user.getEmail());
            changeFirstLog.update("firstLog", false);

            Toast.makeText(this, "Please remember to change your password in the Profile tab",
                    Toast.LENGTH_LONG).show();

            user.setFirstLog(false);
        }
        else {
            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
            pvt = settings.getString("privateKey", "0");
        }


        // Get token and save on the database
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {

                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        // Save token
                        final FirebaseFirestore db = FirebaseFirestore.getInstance();
                        DocumentReference tokenSave = db.collection("patients").document(user.getEmail());
                        tokenSave.update("token", token);

                    }
                });
    }

    public void openCalendar() {
        Bundle extras = new Bundle();

        //passing data to the next Activity
        extras.putString("PAST_DELIVERY",user.getPastDelivery());
        extras.putString("NEXT_DELIVERY", user.getNextDelivery());
        extras.putString("USER_NAME",user.getEmail());
        extras.putStringArrayList("DELIVERIES", user.getDeliveries());

        Intent intent1 = new Intent(this, DeliveryCalendar.class);
        intent1.putExtras(extras);
        startActivity(intent1);
    }

    public void openFollowDelivery() {
        Bundle extras = new Bundle();

        //passing data to the next Activity
        extras.putString("USER_NAME",user.getEmail());

        Intent intent2 = new Intent(this, FollowDelivery.class);
        intent2.putExtras(extras);
        startActivity(intent2);
    }

    public void openGetPassword() {
        Bundle extras = new Bundle();

        //passing data to the next Activity
        extras.putString("USER_NAME",user.getEmail());
        extras.putString("PVT_KEY", pvt);

        Intent intent3 = new Intent(this, Password.class);
        intent3.putExtras(extras);
        startActivity(intent3);
    }

    public void openOverview() {
        Bundle extras = new Bundle();

        //passing data to the next Activity
        extras.putStringArrayList("USER_MEDICINE",user.getMedicineCurrent());
        extras.putStringArrayList("USER_PAST_MEDICINE",user.getMedicinePast());

        Intent intent4 = new Intent(this, MedicineOverview.class);
        intent4.putExtras(extras);
        startActivity(intent4);
    }

    public void openProfile() {
        Bundle extras = new Bundle();

        //passing data to the next Activity

        //USER
        extras.putString("USER_NAME",user.getEmail());
        extras.putString("FIRST_NAME", user.getFirstName());
        extras.putString("LAST_NAME", user.getLastName());
        extras.putString("PHARMACY", user.getPharmacy());
        extras.putString("ADDRESS", user.getAddress());
        extras.putString("BIRTHDAY", user.getBirthday());

        //CAREGIVER
        extras.putString("NAME", caretaker.getEmail());
        extras.putString("FIRSTNAME", caretaker.getFirstName());
        extras.putString("LASTNAME",caretaker.getLastName());
        extras.putString("BIRTHDAY", caretaker.getBirthday());
        extras.putString("PASSWORD", caretaker.getPassword());
        extras.putStringArrayList("PATIENTS", caretaker.getPatients());

        Intent intent5 = new Intent(this, ProfileCare.class);
        intent5.putExtras(extras);
        startActivity(intent5);
    }


}
