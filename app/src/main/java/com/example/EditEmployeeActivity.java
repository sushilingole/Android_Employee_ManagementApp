package com.example;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class EditEmployeeActivity extends AppCompatActivity
{
    //initialized variable :
    EditText etEditName, etEditAddress, etEditMobile, etEditUsername, etEditPassword;

    Button btnUpdate;
    //initialized DB variable
    DBHelper dbHelper;

    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_employee);

         etEditName = findViewById(R.id.etEditName);
        etEditAddress = findViewById(R.id.etEditAddress);
        etEditMobile = findViewById(R.id.etEditMobile);
        etEditUsername = findViewById(R.id.etEditUsername);
        etEditPassword= findViewById(R.id.etEditPassword);

        btnUpdate = findViewById(R.id.btnUpdate);

        dbHelper = new DBHelper(this);

        username = getIntent().getStringExtra("username");

        loadEmployeeDetails();

        btnUpdate.setOnClickListener(v ->
        {
            String name = etEditName.getText().toString().trim();
            String address = etEditAddress.getText().toString().trim();
            String mobile = etEditMobile.getText().toString().trim();
            String username = etEditUsername.getText().toString().trim();
            String password = etEditPassword.getText().toString().trim();

           if(name.isEmpty() || address.isEmpty() || mobile.isEmpty() || username.isEmpty() || password.isEmpty())
           {
               Toast.makeText(this, "All Field Are Required", Toast.LENGTH_SHORT).show();
               return;
           }

           //update query
           boolean update = dbHelper.updateEmployee(username, name, address, mobile, password);
           if(update)
           {
               Toast.makeText(this, "Your Data Updated", Toast.LENGTH_SHORT).show();
               finish();
           }else {
               Toast.makeText(this, "Update Failed", Toast.LENGTH_SHORT).show();
           }

        });

    }

    private void loadEmployeeDetails(){
        Cursor cursor = dbHelper.getEmployeeByUsername(username);
        if (cursor.moveToFirst())
        {
            etEditName.setText(cursor.getString(cursor.getColumnIndexOrThrow("name")));
            etEditAddress.setText(cursor.getString(cursor.getColumnIndexOrThrow("address")));
            etEditMobile.setText(cursor.getString(cursor.getColumnIndexOrThrow("mobile")));
            etEditUsername.setText(cursor.getString(cursor.getColumnIndexOrThrow("username")));
            etEditPassword.setText(cursor.getString(cursor.getColumnIndexOrThrow("password")));
        }
        cursor.close();

    }

}
