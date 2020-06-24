package com.example.guestapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.guestapp.model.Machine;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DetailActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference machineReference;
    String generationCode;
    Machine machine;
    TextView company, type, model, serialNo, dateOfInstallation, price;
    Button feedbackButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        company = findViewById(R.id.company);
        type = findViewById(R.id.type);
        model = findViewById(R.id.model);
        serialNo = findViewById(R.id.serial_no);
        dateOfInstallation = findViewById(R.id.installation_date);
        price = findViewById(R.id.price);

        generationCode = getIntent().getStringExtra("generationCode");

        firebaseDatabase = FirebaseDatabase.getInstance();
        machineReference = firebaseDatabase.getReference("Machines").child(generationCode);

        machineReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                machine = dataSnapshot.getValue(Machine.class);

                company.setText(machine.getCompany());
                type.setText(machine.getType());
                model.setText(machine.getModelNumber());
                serialNo.setText(machine.getSerialNumber());
                price.setText(String.valueOf(machine.getPrice()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        feedbackButton = findViewById(R.id.feedback_button);
        feedbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DetailActivity.this,FeedbackActivity.class);
                startActivity(i);
            }
        });
    }
}
