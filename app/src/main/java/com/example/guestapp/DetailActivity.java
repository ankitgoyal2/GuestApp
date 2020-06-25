package com.example.guestapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.guestapp.model.Machine;
import com.facebook.shimmer.ShimmerFrameLayout;
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
    ShimmerFrameLayout shimmerFrameLayout;
    ConstraintLayout constraintLayoutBox1, constraintLayoutBox2, constraintLayoutBox3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        shimmerFrameLayout = findViewById(R.id.shimmer_container);
        constraintLayoutBox1 = findViewById(R.id.constraint_layout_box_1);
        constraintLayoutBox2 = findViewById(R.id.constraint_layout_box_2);
        constraintLayoutBox3 = findViewById(R.id.constraint_layout_box_3);

        constraintLayoutBox1.setVisibility(View.INVISIBLE);
        constraintLayoutBox2.setVisibility(View.INVISIBLE);
        constraintLayoutBox3.setVisibility(View.INVISIBLE);

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
                dateOfInstallation.setText(machine.getDateOfInstallation());
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                constraintLayoutBox1.setVisibility(View.VISIBLE);
                constraintLayoutBox2.setVisibility(View.VISIBLE);
                constraintLayoutBox3.setVisibility(View.VISIBLE);
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
                overridePendingTransition(R.anim.feedback_enter_anim,R.anim.fade_out);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        shimmerFrameLayout.startShimmer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        shimmerFrameLayout.stopShimmer();
    }
}
