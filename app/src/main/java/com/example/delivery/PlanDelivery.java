package com.example.delivery;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.delivery.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class PlanDelivery extends AppCompatActivity {

    String Date;
    Button save;
    Button firstTimeslot;
    Button secondTimeslot;
    DatePicker datePicker;
    User user = new User();
    String selectedTime;
    String newDelivery;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_delivery);

        Intent intent = getIntent();

        //get the attached bundle from the intent
        Bundle extras = intent.getExtras();

        //Extracting the stored data from the bundle
        this.user.setPastDelivery(extras.getString("PAST_DELIVERY"));
        this.user.setNextDelivery(extras.getString("NEXT_DELIVERY"));
        this.user.setEmail(extras.getString("USER_NAME"));

        datePicker = findViewById(R.id.date_picker);

        //disabling 3 weeks after the delivery date
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        java.util.Date date = null;
        try {
            date = sdf.parse(user.getPastDelivery());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        assert date != null;
        cal.setTime(date);

        System.out.println(cal);

        Calendar today = cal;
        Calendar minDate = (Calendar) today.clone();
        minDate.add(Calendar.DATE, 21);
        Calendar maxDate = (Calendar) today.clone();
        maxDate.add(Calendar.DATE, 28);
        datePicker.setMinDate(minDate.getTimeInMillis());
        datePicker.setMaxDate(maxDate.getTimeInMillis());

        //Setting the date in the Text View
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {

                    @Override
                    public void onDateChanged(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        Date = dayOfMonth + "-"
                                + (month + 1) + "-" + year;
                        // set this date in TextView for Display
                        TextView date_view = findViewById(R.id.date_view);
                        date_view.setText("Selected Date: " + Date);
                    }
                });

        firstTimeslot = findViewById(R.id.timeslot1);
        firstTimeslot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTime1();
            }
        });

        secondTimeslot = findViewById(R.id.timeslot2);
        secondTimeslot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTime2();
            }
        });


        save = findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDate();
            }
        });
    }

    public void saveTime1() {
        selectedTime = "10:00-14:00";
        TextView time_view = findViewById(R.id.time_view);
        time_view.setText("Selected Timeslot: " + selectedTime);
    }

    public void saveTime2() {
        selectedTime = "17:00-21:00";
        TextView time_view = findViewById(R.id.time_view);
        time_view.setText("Selected Timeslot: " + selectedTime);
    }

    public void saveDate() {
        newDelivery = Date + " " + selectedTime;

        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("patients")
                .whereEqualTo("email", user.getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                User userRetrieved = document.toObject(User.class);
                            }
                        }
                    }
                });
        DocumentReference nextDelivery = db.collection("patients").document(user.getEmail());
        nextDelivery.update("nextDelivery", newDelivery)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(PlanDelivery.this, "Next Delivery: " + Date + " at " + selectedTime,
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}