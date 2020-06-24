package com.example.delivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import com.example.delivery.Model.User;

public class DeliveryCalendar extends AppCompatActivity {

    private Button plan;
    private Button past;
    User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_calendar);

        Intent intent = getIntent();

        //get the attached bundle from the intent
        Bundle extras = intent.getExtras();

        //Extracting the stored data from the bundle
        this.user.setPastDelivery(extras.getString("PAST_DELIVERY"));
        this.user.setNextDelivery(extras.getString("NEXT_DELIVERY"));
        this.user.setEmail(extras.getString("USER_NAME"));
        this.user.setDeliveries(extras.getStringArrayList("DELIVERIES"));

        plan = findViewById(R.id.plan);
        plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPlanDelivery();
            }
        });

        past = findViewById(R.id.past);
        past.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPastDeliveries();
            }
        });
    }

    public void openPlanDelivery() {
        Bundle extras = new Bundle();

        //passing data to the next Activity
        extras.putString("PAST_DELIVERY", user.getPastDelivery());
        extras.putString("NEXT_DELIVERY", user.getNextDelivery());
        extras.putString("USER_NAME",user.getEmail());

        Intent intent = new Intent(this, PlanDelivery.class);
        intent.putExtras(extras);
        startActivity(intent);
    }

    public void openPastDeliveries() {
        Bundle extras = new Bundle();

        extras.putStringArrayList("DELIVERIES", user.getDeliveries());
        extras.putString("USER_NAME",user.getEmail());

        Intent intent = new Intent(this, PastDeliveries.class);
        intent.putExtras(extras);
        startActivity(intent);
    }
}
