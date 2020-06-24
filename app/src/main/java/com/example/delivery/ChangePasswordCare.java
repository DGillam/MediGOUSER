package com.example.delivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.delivery.Model.Caretakers;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class ChangePasswordCare extends AppCompatActivity {

    private Button changePassword;
    private EditText newPwd;
    private EditText oldPwd;
    private EditText confirmPwd;
    String correctOldPassword = "";
    private static final String PASSWORD_KEY = "password";
    public Caretakers caretaker = new Caretakers();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password_care);
        Intent intent = getIntent();

        //get the attached bundle from the intent
        Bundle extras = intent.getExtras();

        //CARETAKER
        this.caretaker.setEmail(extras.getString("NAME"));
        this.caretaker.setFirstName(extras.getString("FIRSTNAME"));
        this.caretaker.setLastName(extras.getString("LASTNAME"));

        changePassword = findViewById(R.id.changePassword);
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    updatePassword();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private void updatePassword() throws InterruptedException {

        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        String newPassword;
        String oldPassword;
        String confirmPassword;

        newPwd = findViewById(R.id.newPassword);
        newPassword = newPwd.getText().toString().trim();

        oldPwd = findViewById(R.id.oldPassword);
        oldPassword = oldPwd.getText().toString().trim();

        confirmPwd = findViewById(R.id.confirmPassword);
        confirmPassword = confirmPwd.getText().toString().trim();

        db.collection("caretakers")
                .whereEqualTo("email", caretaker.getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Caretakers caretakerRetrieved = document.toObject(Caretakers.class);

                                correctOldPassword = caretakerRetrieved.getPassword();
                            }
                        }
                    }
                });

        boolean condition1 = (oldPassword.equals(correctOldPassword));
        boolean condition2 = newPassword.equals(confirmPassword) && !newPassword.equals("");

        if (condition1 && condition2) {
            DocumentReference changepwd = db.collection("caretakers").document(caretaker.getEmail());
            changepwd.update("password", newPassword)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(ChangePasswordCare.this, "Updated Successfully",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        } else if (condition1 && !condition2) {
            Toast.makeText(this, "New Passwords Do Not Match", Toast.LENGTH_LONG).show();
            return;
        } else if (!condition1 && condition2) {
            Toast.makeText(this, "The Old Password is Incorrect", Toast.LENGTH_LONG).show();
            return;
        } else {
            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
            return;
        }
    }
}