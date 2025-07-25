package com.example;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class AdminLoginActivity extends AppCompatActivity
{
    //initialize the Edit Text Variable:
    EditText etAdminUsername, etAdminPassword;

    //Initialize the Button ::
    Button btnAdminLogin;

    //initialize the DB
    DBHelper dbHelper;

    //initialize the View text redirect back on login:
    TextView tvLoginLink;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_adminlogin);

        //find the data form UI
        etAdminUsername = findViewById(R.id.etAdminUsername);
        etAdminPassword = findViewById(R.id.etAdminPassword);

        //get button
        btnAdminLogin = findViewById(R.id.btnAdminLogin);

        //get the Object of DB
        dbHelper = new DBHelper(this);

        //adding onclick btn listener ::
        btnAdminLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //get the Admin username and password ::
                String username = etAdminUsername.getText().toString().trim();
                String password = etAdminPassword.getText().toString().trim();

                if(username.isEmpty() || password.isEmpty())
                {
                    Toast.makeText(AdminLoginActivity.this,"Please Fill All the Fields ", Toast.LENGTH_SHORT).show();
                }else {

                    boolean isValid = dbHelper.checkAdminLogin(username,password);
                    if(isValid)
                    {
                        Toast.makeText(AdminLoginActivity.this, "Admin Login Successful", Toast.LENGTH_SHORT).show();

                        //Show All Employee Data ::
                        Intent intent = new Intent(AdminLoginActivity.this, AllEmpDataActivity.class);
                        startActivity(intent);
                        finish();

                    }else{
                        Toast.makeText(AdminLoginActivity.this, "Invalid User OR Password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // Future: Navigate to Login
        tvLoginLink= findViewById(R.id.tvLoginLink);

        tvLoginLink.setOnClickListener(L -> {
            Toast.makeText(AdminLoginActivity.this, "Login screen link clicked!", Toast.LENGTH_SHORT).show();
            // TODO: Start LoginActivity here
            Intent intent = new Intent(AdminLoginActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
