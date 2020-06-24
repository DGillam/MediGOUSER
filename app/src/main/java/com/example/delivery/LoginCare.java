package com.example.delivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.delivery.Model.Caretakers;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginCare extends AppCompatActivity {

    private Button button;
    private EditText username;
    private EditText pwd;
    private Caretakers caretaker;
    LoginCare login = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_care);

        button = (Button) findViewById(R.id.loginuser);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddAccount();
            }
        });
    }

    public void openAddAccount() {
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

        db.collection("caretakers")
                .whereEqualTo("email", email)
                .whereEqualTo("password", password)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                caretaker = document.toObject(Caretakers.class);

                                Bundle extras = new Bundle();

                                //passing data to the next Activity
                                extras.putString("NAME", caretaker.getEmail());
                                extras.putString("FIRSTNAME", caretaker.getFirstName());
                                extras.putString("LASTNAME",caretaker.getLastName());
                                extras.putString("BIRTHDAY", caretaker.getBirthday());
                                extras.putString("PASSWORD", caretaker.getPassword());
                                extras.putStringArrayList("PATIENTS", caretaker.getPatients());

                                Intent intent = new Intent(login, ChooseAccount.class);
                                intent.putExtras(extras);
                                startActivity(intent);
                            }
                        }
                    }
                });
    }
}
