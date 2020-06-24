package com.example.delivery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.delivery.Model.User;

public class CurrentMedication extends AppCompatActivity {

    private User user = new User();
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_medication);

        Intent intent = getIntent();
        //get the attached bundle from the intent
        Bundle extras = intent.getExtras();

        //Extracting the stored data from the bundle
        assert extras != null;
        this.user.setMedicineCurrent(extras.getStringArrayList("USER_MEDICINE"));
        System.out.println(user.getMedicineCurrent());
        lv = findViewById(R.id.list_medication);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, user.getMedicineCurrent());

        lv.setAdapter(arrayAdapter);
    }
}
