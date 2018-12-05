package com.example.lenovo.eats.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.example.lenovo.eats.ClassModel.Bill_Feedback;
import com.example.lenovo.eats.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Bill_FeedbackActivity extends AppCompatActivity {

    private DatabaseReference feedbackRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        feedbackRef = FirebaseDatabase.getInstance().getReference().child("Feedbacks");

        Button submit =  findViewById(R.id.submitFeedbackBtn);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText name = findViewById(R.id.nameField);
                EditText phone = findViewById(R.id.phoneField);
                EditText comments = findViewById(R.id.commentsField);
                RatingBar ratings = findViewById(R.id.ratingBar);

                String nameString, phoneString, commentsString;
                nameString = name.getText().toString();
                phoneString = phone.getText().toString();
                commentsString = comments.getText().toString();
                int rating = ratings.getNumStars();
                Bill_Feedback feedback = new Bill_Feedback(nameString, phoneString, commentsString, rating);

                //store feedback to db
                feedbackRef.push().setValue(feedback);
                //hall management action
            }
        });
    }
}
