package com.example.delivery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UserSelect extends AppCompatActivity {

    private Button button;
    private Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_select);

        button = (Button) findViewById(R.id.userButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLoginUser();
            }
        });

        button2 = (Button) findViewById(R.id.caregiverButton);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLoginCare();
            }
        });    }

    public void openLoginUser() {
        Intent intent = new Intent(this, LoginUser.class);
        startActivity(intent);
    }

    public void openLoginCare() {
        Intent intent = new Intent(this, LoginCare.class);
        startActivity(intent);
    }
}
