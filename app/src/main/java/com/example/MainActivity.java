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
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    //Initialize
    EditText etName , etAddress , etMobile , etUsername , etPassword;

    //initialize button
    Button btnRegister;

    //initialize Text view
    TextView tvLoginLink;

    //initialize DB
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        //bind views(GetData ids name from UI)
        etName = findViewById(R.id.etName);
        etAddress = findViewById(R.id.etAddress);
        etMobile = findViewById(R.id.etMobile);
        etUsername =findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);

        //take Register btn
        btnRegister= findViewById(R.id.btnRegister);

        // take login redirection view
        tvLoginLink = findViewById(R.id.tvLoginLink);

        dbHelper =  new DBHelper(this);

        //now set the OnClick listner ::
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name= etName.getText().toString().trim();
                String address = etAddress.getText().toString().trim();
                String mobile= etMobile.getText().toString().trim();
                String username= etUsername.getText().toString().trim();
                String password= etPassword.getText().toString().trim();

                // Validate
                if (name.isEmpty() || address.isEmpty() || mobile.isEmpty() || username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Insert Data into the DB
                boolean isInserted = dbHelper.insertEmployee(name, address, mobile, username, password);

                if (isInserted) {
                    Toast.makeText(MainActivity.this, "Registration Successful!", Toast.LENGTH_LONG).show();
                    etName.setText(""); etAddress.setText(""); etMobile.setText("");
                    etUsername.setText(""); etPassword.setText("");

                    //Redirect Activity on loginActivity
                    Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(MainActivity.this, "Registration Failed!", Toast.LENGTH_LONG).show();
                }
            }
        });

        // Future: Navigate to Login
        tvLoginLink.setOnClickListener(L -> {
            Toast.makeText(MainActivity.this, "Login screen link clicked!", Toast.LENGTH_SHORT).show();
            // TODO: Start LoginActivity here
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }
}