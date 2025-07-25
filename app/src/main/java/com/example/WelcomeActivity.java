package com.example;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity
{
    //Initialize variable for textView :
    TextView tvWelcome, tvAddress, tvMobile, tvUsername ;

    //initialize Edit Button :
    Button btnEdit;

    //initialize DB
    DBHelper dbHelper;

    //Initialized logged username :
    String loggedUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_welcome);

        tvWelcome = findViewById(R.id.tvWelcome);
        tvAddress= findViewById(R.id.tvAddress);
        tvMobile= findViewById(R.id.tvMobile);
        tvUsername= findViewById(R.id.tvUsername);

        btnEdit= findViewById(R.id.btnEdit);

        //Take button
        dbHelper= new DBHelper(this);

        loggedUsername = getIntent().getStringExtra("username");

        loadEmployeeData();

        //lambda expression
        btnEdit.setOnClickListener(v-> {
            Intent intent= new Intent(WelcomeActivity.this, EditEmployeeActivity.class);
            intent.putExtra("username", loggedUsername);
            startActivity(intent);
        });
    }

    private void loadEmployeeData(){
        Cursor cursor = dbHelper.getEmployeeByUsername(loggedUsername);
        if(cursor.moveToFirst())
        {
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            String address = cursor.getString(cursor.getColumnIndexOrThrow("address"));
            String mobile = cursor.getString(cursor.getColumnIndexOrThrow("mobile"));

            tvWelcome.setText("Welcome, "+ name);
            tvAddress.setText("Address, "+ address);
            tvMobile.setText("Mobile, "+ mobile);
            tvUsername.setText("Username, "+ loggedUsername);
        }
        cursor.close();
    }
    protected void onResume(){
        super.onResume();
        loadEmployeeData();//refresh after edit
    }

}
