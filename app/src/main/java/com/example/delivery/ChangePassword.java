package com.example.delivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.delivery.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class ChangePassword extends AppCompatActivity {

    private Button changePassword;
    private EditText newPwd;
    private EditText oldPwd;
    private EditText confirmPwd;
    String correctOldPassword = "";
    private static final String PASSWORD_KEY = "password";
    private User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        Intent intent = getIntent();

        //get the attached bundle from the intent
        Bundle extras = intent.getExtras();

        //Extracting the stored data from the bundle
        this.user.setEmail(extras.getString("USER_NAME"));
        this.user.setFirstName(extras.getString("FIRST_NAME"));
        this.user.setLastName(extras.getString("LAST_NAME"));

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

        db.collection("patients")
                .whereEqualTo("email", user.getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                User userRetrieved = document.toObject(User.class);

                                correctOldPassword = userRetrieved.getPassword();
                            }
                        }
                    }
                });

        boolean condition1 = (oldPassword.equals(correctOldPassword) && !oldPassword.equals(""));
        boolean condition2 = newPassword.equals(confirmPassword) && !newPassword.equals("");

        if (condition1 && condition2) {
            DocumentReference changepwd = db.collection("patients").document(user.getEmail());
            changepwd.update(PASSWORD_KEY, newPassword)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(ChangePassword.this, "Updated Successfully",
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