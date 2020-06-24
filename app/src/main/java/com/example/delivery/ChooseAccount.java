package com.example.delivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.delivery.Model.Caretakers;
import com.example.delivery.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class ChooseAccount extends AppCompatActivity {

    public Caretakers caretaker = new Caretakers();
    public ListView lv;
    User user = new User();
    ChooseAccount choose = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_account);

        Intent intent = getIntent();

        //get the attached bundle from the intent
        Bundle extras = intent.getExtras();

        //Extracting the stored data from the bundle
        this.caretaker.setEmail(extras.getString("NAME"));
        this.caretaker.setFirstName(extras.getString("FIRSTNAME"));
        this.caretaker.setLastName(extras.getString("LASTNAME"));
        this.caretaker.setBirthday(extras.getString("BIRTHDAY"));
        this.caretaker.setPassword(extras.getString("PASSWORD"));
        this.caretaker.setPatients(extras.getStringArrayList("PATIENTS"));

        lv = findViewById(R.id.listView);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, caretaker.getPatients());

        lv.setAdapter(arrayAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item text from ListView
                String selectedItem = (String) parent.getItemAtPosition(position);

                final FirebaseFirestore db = FirebaseFirestore.getInstance();

                db.collection("patients")
                        .whereEqualTo("email", selectedItem)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        user = document.toObject(User.class);

                                        Bundle extras = new Bundle();

                                        //passing data to the next Activity
                                        extras.putString("USER_NAME", user.getEmail());
                                        extras.putString("USER_FIRSTNAME", user.getFirstName());
                                        extras.putString("USER_LASTNAME", user.getLastName());
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

                                        extras.putString("NAME", caretaker.getEmail());
                                        extras.putString("FIRSTNAME", caretaker.getFirstName());
                                        extras.putString("LASTNAME", caretaker.getLastName());
                                        extras.putString("BIRTHDAY", caretaker.getBirthday());
                                        extras.putString("PASSWORD", caretaker.getPassword());
                                        extras.putStringArrayList("PATIENTS", caretaker.getPatients());

                                        System.out.println(user.getPastDelivery());

                                        Intent intent = new Intent(choose, MainActivityCare.class);
                                        intent.putExtras(extras);
                                        startActivity(intent);
                                    }
                                }
                            }
                        });
            }
        });
    }
}
