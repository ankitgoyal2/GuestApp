package com.example.guestapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.guestapp.DialogBox.ComplaintDescriptionDialog;
import com.example.guestapp.model.Complaint;
import com.example.guestapp.model.Machine;
import com.example.guestapp.model.Manager;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.parceler.Parcels;

import java.util.Calendar;

public class DetailActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference machineReference;
    String generationCode;
    Machine machine;
    TextView company, type, model, serialNo, dateOfInstallation, price, complaintInfo;
    Button feedbackButton;
    ShimmerFrameLayout shimmerFrameLayout;
    ConstraintLayout constraintLayoutBox1, constraintLayoutBox2, constraintLayoutBox3;
    ImageView back;
    TranslateAnimation constraintLayoutBox1Anim,constraintLayoutBox2Anim;
    Complaint complaint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        shimmerFrameLayout = findViewById(R.id.shimmer_container);
        constraintLayoutBox1 = findViewById(R.id.constraint_layout_box_1);
        constraintLayoutBox2 = findViewById(R.id.constraint_layout_box_2);
        constraintLayoutBox3 = findViewById(R.id.constraint_layout_box_3);

        constraintLayoutBox1Anim = new TranslateAnimation(-800,0,0,0);
        constraintLayoutBox2Anim = new TranslateAnimation(-1000,0,0,0);
        constraintLayoutBox1Anim.setInterpolator(new LinearInterpolator());
        constraintLayoutBox2Anim.setInterpolator(new LinearInterpolator());
        constraintLayoutBox1Anim.setDuration(300);
        constraintLayoutBox2Anim.setDuration(300);

        constraintLayoutBox1.setVisibility(View.INVISIBLE);
        constraintLayoutBox2.setVisibility(View.INVISIBLE);
        constraintLayoutBox3.setVisibility(View.INVISIBLE);

        company = findViewById(R.id.company);
        type = findViewById(R.id.type);
        model = findViewById(R.id.model);
        serialNo = findViewById(R.id.serial_no);
        dateOfInstallation = findViewById(R.id.installation_date);
        price = findViewById(R.id.price);
        complaintInfo = findViewById(R.id.textView9);
        feedbackButton = findViewById(R.id.feedback_button);

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
                constraintLayoutBox1.startAnimation(constraintLayoutBox1Anim);
                constraintLayoutBox2.startAnimation(constraintLayoutBox2Anim);

                if(!machine.isWorking())
                {
                    feedbackButton.setEnabled(false);
                    complaintInfo.setText("complaint is already registered on this machine");
                    feedbackButton.setVisibility(View.GONE);
                }
                else
                {
                    feedbackButton.setVisibility(View.VISIBLE);
                    feedbackButton.setEnabled(true);
                    complaintInfo.setText("Any Problem? Please register a complaint");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        feedbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                complaint = new Complaint();
                Manager tempManager = null;
                try {
                    tempManager = (Manager) machine.getManager().clone();
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
                if (tempManager != null) {
                    tempManager.setPendingComplaints(null);
                    tempManager.setPendingApprovalRequest(null);
                    tempManager.setCompletedComplaints(null);
                    tempManager.setMyMachines(null);
                }
                complaint.setManager(tempManager);

                Machine tempMachine = null;
                try {
                    tempMachine = (Machine) machine.clone();
                    tempMachine.setManager(null);
                    tempMachine.setPastRecordList(null);
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }

                complaint.setMachine(tempMachine);
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                month = month+1;
                int day = cal.get(Calendar.DAY_OF_MONTH);

                complaint.setGeneratedDate(day+"/"+month+"/"+year);
                complaint.setStatus(Complaint.generatedOnly);

                ComplaintDescriptionDialog complaintDescriptionDialog = new ComplaintDescriptionDialog(DetailActivity.this, complaint);
                complaintDescriptionDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                complaintDescriptionDialog.show();
            }
        });

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
