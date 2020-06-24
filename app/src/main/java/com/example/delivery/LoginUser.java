package com.example.delivery;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.delivery.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginUser extends AppCompatActivity {
    private Button LoginUserBtn;
    private EditText username;
    private EditText pwd;
    private User user;
    LoginUser login = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoginUserBtn = (Button) findViewById(R.id.loginuser);
        LoginUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
            }
        });
    }

    public void openMainActivity() {
        String email;
        String password;

        username = findViewById(R.id.username);
        pwd = findViewById(R.id.password);

        email = username.getText().toString().trim();
        password = pwd.getText().toString().trim();

        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        //checking if email and passwords are empty
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }

        db.collection("patients")
                .whereEqualTo("email", email)
                .whereEqualTo("password", password)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                user = document.toObject(User.class);

                                Bundle extras = new Bundle();

                                //passing data to the next Activity
                                extras.putString("USER_NAME",user.getEmail());
                                extras.putString("USER_FIRSTNAME", user.getFirstName());
                                extras.putString("USER_LASTNAME",user.getLastName());
                                extras.putString("USER_BIRTHDAY", user.getBirthday());
                                extras.putStringArrayList("USER_MEDICINE", user.getMedicineCurrent());
                                extras.putString("USER_PASSWORD", user.getPassword());
                                extras.putBoolean("FIRST_LOG", user.getFirstLog());
                                extras.putStringArrayList("USER_PAST_MEDICINE", user.getMedicinePast());
                                extras.putString("PAST_DELIVERY", user.getPastDelivery());
                                extras.putString("NEXT_DELIVERY", user.getNextDelivery());
                                extras.putString("PHARMACY", user.getPharmacy());
                                extras.putString("ADDRESS", user.getAddress());
                                extras.putStringArrayList("DELIVERIES", user.getDeliveries());

                                System.out.println(user.getPastDelivery());

                                Intent intent = new Intent(login, MainActivity.class);
                                intent.putExtras(extras);
                                startActivity(intent);
                                }
                            }
                    }
                });
    }
}
