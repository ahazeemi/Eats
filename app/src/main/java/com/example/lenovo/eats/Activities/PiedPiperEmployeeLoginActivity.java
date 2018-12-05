package com.example.lenovo.eats.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.lenovo.eats.R;;


public class PiedPiperEmployeeLoginActivity extends AppCompatActivity {

    private EditText et_username;
    private EditText et_password;
    private Button btn_login;
    private TextView tv_error;
    public static final String TAG = "Test";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.piedpiper_activity_employee_login);

        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
        btn_login = findViewById(R.id.btn_login);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("Employee");



        btn_login.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                // Read from the database
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        String dbUsername;
                        String dbPassword;
                        String myUsername=et_username.getText().toString();
                        String myPassword=et_password.getText().toString();

                        boolean is_user_found = false;

                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            dbUsername = data.child("username").getValue().toString();
                            dbPassword = data.child("password").getValue().toString();


                            if(myUsername.equals(dbUsername) && myPassword.equals(dbPassword))
                            {
                                is_user_found = true;
                                Toast.makeText(PiedPiperEmployeeLoginActivity.this, "User logged in", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(PiedPiperEmployeeLoginActivity.this , PiedPiperMainMenuActivity.class );
                                startActivity( intent );
                            }
                        }

                        if ( !is_user_found )
                        {
                            Toast.makeText(PiedPiperEmployeeLoginActivity.this, "Bad username and password combination!", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.w(TAG, "Failed to read value.", error.toException());
                    }
                });
            }
        });

    }
}
