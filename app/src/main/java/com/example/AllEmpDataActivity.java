package com.example;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AllEmpDataActivity extends AppCompatActivity {

    TableLayout tableLayout;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tableLayout = new TableLayout(this);
        tableLayout.setStretchAllColumns(true);
        dbHelper = new DBHelper(this);

        Cursor cursor = dbHelper.getAllEmployees();

        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No employee data found", Toast.LENGTH_SHORT).show();
        } else {
            // Add table header
            TableRow header = new TableRow(this);
            String[] headerTitles = {"ID", "Name", "Email", "Mobile", "Status", "Actions"};
            for (String title : headerTitles) {
                TextView textView = new TextView(this);
                textView.setText(title);
                textView.setPadding(16, 16, 16, 16);
                textView.setTextColor(Color.WHITE);
                textView.setBackgroundColor(Color.DKGRAY);
                header.addView(textView);
            }
            tableLayout.addView(header);

            if (cursor.moveToFirst()) {
                do {
                    TableRow row = new TableRow(this);

                    // Get data from cursor
                    String id = cursor.getString(0);
                    String name = cursor.getString(1);
                    String email = cursor.getString(2);
                    String mobile = cursor.getString(3);
                    String status = cursor.getString(4);

                    // ID
                    TextView idText = new TextView(this);
                    idText.setText(id);
                    idText.setPadding(8, 8, 8, 8);
                    row.addView(idText);

                    // Name
                    TextView nameText = new TextView(this);
                    nameText.setText(name);
                    nameText.setPadding(8, 8, 8, 8);
                    row.addView(nameText);

                    // Email
                    TextView emailText = new TextView(this);
                    emailText.setText(email);
                    emailText.setPadding(8, 8, 8, 8);
                    row.addView(emailText);

                    // Mobile
                    TextView mobileText = new TextView(this);
                    mobileText.setText(mobile);
                    mobileText.setPadding(8, 8, 8, 8);
                    row.addView(mobileText);

                    // Status
                    TextView statusText = new TextView(this);
                    statusText.setText(status);
                    statusText.setPadding(8, 8, 8, 8);
                    row.addView(statusText);

                    // Buttons container
                    TableRow buttonContainer = new TableRow(this);

                    Button approveBtn = new Button(this);
                    approveBtn.setText("Approve");
                    approveBtn.setTextColor(Color.WHITE);
                    approveBtn.setBackgroundColor(Color.parseColor("#4CAF50"));

                    Button rejectBtn = new Button(this);
                    rejectBtn.setText("Reject");
                    rejectBtn.setTextColor(Color.WHITE);
                    rejectBtn.setBackgroundColor(Color.parseColor("#F44336"));

                    // Logic for button visibility
                    if (status.equalsIgnoreCase("Approved")) {
                        approveBtn.setVisibility(View.GONE);
                    } else if (status.equalsIgnoreCase("Rejected")) {
                        rejectBtn.setVisibility(View.GONE);
                    }

                    approveBtn.setOnClickListener(v -> {
                        dbHelper.updateStatus(id, "Approved");
                        statusText.setText("Approved");
                        approveBtn.setVisibility(View.GONE);
                        rejectBtn.setVisibility(View.VISIBLE);
                        Toast.makeText(this, "Approved", Toast.LENGTH_SHORT).show();
                    });

                    rejectBtn.setOnClickListener(v -> {
                        dbHelper.updateStatus(id, "Rejected");
                        statusText.setText("Rejected");
                        rejectBtn.setVisibility(View.GONE);
                        approveBtn.setVisibility(View.VISIBLE);
                        Toast.makeText(this, "Rejected", Toast.LENGTH_SHORT).show();
                    });

                    buttonContainer.addView(approveBtn);
                    buttonContainer.addView(rejectBtn);
                    row.addView(buttonContainer);

                    tableLayout.addView(row);

                } while (cursor.moveToNext());
            }
        }

        HorizontalScrollView scrollView = new HorizontalScrollView(this);
        scrollView.addView(tableLayout);

        setContentView(scrollView);
    }
}
