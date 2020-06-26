package com.example.guestapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.guestapp.model.Feedback;
import com.example.guestapp.model.Machine;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.parceler.Parcels;

import java.util.Calendar;
import java.util.HashMap;

public class FeedbackActivity extends AppCompatActivity {

    EditText name, email, number, review;
    RadioGroup radioGroup;
    RadioButton radioButton;
    Button submit;
    String status;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference feedbackIdreference;

    long feedbackIdValue;

    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        getWindow().setAllowEnterTransitionOverlap(true);

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        number = findViewById(R.id.number);
        radioGroup = findViewById(R.id.radioGroup);
        review = findViewById(R.id.reviews);
        submit = findViewById(R.id.submit_button);

        firebaseDatabase = FirebaseDatabase.getInstance();
        feedbackIdreference = firebaseDatabase.getReference("FeedbackId");

        feedbackIdreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                feedbackIdValue = (long) snapshot.getValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Machine machine = Parcels.unwrap(getIntent().getParcelableExtra("machine"));

                int selectedId = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(selectedId);
                status = radioButton.getText().toString();

                HashMap<String, Object> hashMap = new HashMap<>();
                Feedback feedback = new Feedback();

                feedback.setName(name.getText().toString());
                feedback.setEmail(email.getText().toString());
                feedback.setNumber(number.getText().toString());
                feedback.setReviews(review.getText().toString());
                feedback.setFeedbackId(feedbackIdValue);

                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                month = month + 1;
                int day = cal.get(Calendar.DAY_OF_MONTH);

                feedback.setDate(day+"/"+month+"/"+year);

                if(status.equals("Yes"))
                    feedback.setWorking(true);
                else if(status.equals("No"))
                    feedback.setWorking(false);

                feedback.setMachine(machine);
                hashMap.put("/Feedbacks/" + feedbackIdValue, feedback);
                hashMap.put("/FeedbackId" , feedbackIdValue+1);

                FirebaseDatabase.getInstance().getReference().updateChildren(hashMap);

            }
        });


        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.fade_in,R.anim.feedback_exit_anim);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in,R.anim.feedback_exit_anim);
    }
}
