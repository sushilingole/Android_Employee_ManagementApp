package com.example;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity
{

    //for login userName and password variable initialize
    EditText etUsername , etPassword;

    //initialize button variable
     Button btnLogin;

    //initialize db variable
    DBHelper dbHelper;

    //initialize view for redirect on Admin Login ::
    TextView tvAdminLoginLink;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        //find with the help od id from UI ::
        etUsername = findViewById(R.id.etUsername);
        etPassword= findViewById(R.id.etPassword);

        //take the button as well
       btnLogin = findViewById(R.id.btnLogin);

       //to Redirection on Admin login
        tvAdminLoginLink = findViewById(R.id.tvAdminLoginLink);

        //take the db ::
        dbHelper = new DBHelper(this);

        //adding OnClick listener
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();


                //get Statues : Approved and Rejected for Admin permission


                if(username.isEmpty() || password.isEmpty())
                {
                    Toast.makeText(LoginActivity.this,"Please Fill All the Fields ", Toast.LENGTH_SHORT).show();
                }else {

                    boolean isValid = dbHelper.checkEmployeeLogin(username, password);
                    if(isValid)
                    {

                            //Get Employees data for checking the Status::
                            Cursor cursor =dbHelper.getEmployeeByUsername(username);

                            if(cursor != null && cursor.moveToFirst()) {
                                String status = cursor.getString(cursor.getColumnIndexOrThrow("status"));
                                if (status.equalsIgnoreCase("Approved")) {

                                    Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();

                                    //after successful Redirect on welcome page::

                                    Intent intent = new Intent(LoginActivity.this, WelcomeActivity.class);
                                    intent.putExtra("username", username);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(LoginActivity.this, "Your Account is not Approved Or Rejected", Toast.LENGTH_SHORT).show();
                                }

                                //close the cursor ::
                                cursor.close();
                            }
                    }else{
                        Toast.makeText(LoginActivity.this, "Invalid Username OR Password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // Future: Navigate to Login
        tvAdminLoginLink.setOnClickListener(L -> {
            Toast.makeText(LoginActivity.this, "Admin Login screen link clicked!", Toast.LENGTH_SHORT).show();
            // TODO: Start LoginActivity here
            Intent intent = new Intent(LoginActivity.this, AdminLoginActivity.class);
            startActivity(intent);
            finish();
        });

    }


}
