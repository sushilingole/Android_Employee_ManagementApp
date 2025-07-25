package com.example;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.Button;
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
        setContentView(R.layout.activity_all_emp_data);

        tableLayout = findViewById(R.id.tableLayout);
        dbHelper = new DBHelper(this);

        displayAllEmployees();
    }

    private void displayAllEmployees() {
        Cursor cursor = dbHelper.getAllEmployees();

        tableLayout.removeAllViews(); // Clear previous rows

        // Header Row
        TableRow header = new TableRow(this);
        header.setBackgroundColor(Color.parseColor("#007BFF"));

        String[] columns = {"ID", "Name", "Address", "Mobile", "Username", "Status"};
        for (String col : columns) {
            TextView tv = new TextView(this);
            tv.setText(col);
            tv.setTextColor(Color.WHITE);
            tv.setTextSize(16);
            tv.setPadding(24, 16, 24, 16);

            //column set on single line
            tv.setSingleLine(true);
            //set ellipsize
          //  tv.setEllipsize(TextUtils.TruncateAt.END);

            tv.setLayoutParams(new TableRow.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));

            header.addView(tv);
        }
        tableLayout.addView(header);

        if (cursor.moveToFirst())
        {
            int rowIndex = 0;
            int colID = cursor.getColumnIndexOrThrow("id");
            int colName = cursor.getColumnIndex("name");
            int colAddress = cursor.getColumnIndex("address");
            int colMobile = cursor.getColumnIndex("mobile");
            int colUsername = cursor.getColumnIndex("username");
            int colStatus = cursor.getColumnIndex("status");


            do {

                //Take Values to display

                        String id = cursor.getString(colID);
                        String name = cursor.getString(colName);
                        String address = cursor.getString(colAddress);
                        String mobile = cursor.getString(colMobile);
                        String username = cursor.getString(colUsername);
                        String status = cursor.getString(colStatus);


                TableRow row = new TableRow(this);
                row.setBackgroundColor(rowIndex % 2 == 0 ? Color.parseColor("#F8F9FA") : Color.WHITE);

                //put the values :
                String[] values = {id,name,address, mobile, username};

                for (String val : values) {
                    TextView tv = new TextView(this);
                    tv.setText(val != null ? val : "");
                    tv.setTextColor(Color.parseColor("#333333"));
                    tv.setTextSize(14);
                    tv.setPadding(24, 16, 24, 16);
                    tv.setSingleLine(true);
                   // tv.setEllipsize(TextUtils.TruncateAt.END);

                    row.addView(tv);

                    tv.setLayoutParams(new TableRow.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    ));


                }

                //status Column ::
                TableRow.LayoutParams statusParams = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f);

                if("Pending".equalsIgnoreCase(status))
                {
                        //Approve Button ::
                    Button approveBtn= new Button(this);
                    approveBtn.setText("Approved");//set the value to approve button
                    approveBtn.setTextSize(12);
                    approveBtn.setTextColor(Color.WHITE);
                    approveBtn.setBackgroundColor(Color.parseColor("#28a745"));
                    approveBtn.setLayoutParams(statusParams);
                    approveBtn.setPadding(10, 10, 10, 10);


                    //Reject Button::
                    Button rejectBtn= new Button(this);
                    rejectBtn.setText("Rejected");
                    rejectBtn.setTextSize(12);
                    rejectBtn.setTextColor(Color.WHITE);
                    rejectBtn.setBackgroundColor(Color.parseColor("#dc3545"));
                    rejectBtn.setLayoutParams(statusParams);
                    rejectBtn.setPadding(10, 10, 10, 10);

                    //add button to row::
                    row.addView(approveBtn);
                    row.addView(rejectBtn);

                    //OnClick Action for Approve btn::
                    approveBtn.setOnClickListener(v->{
                        dbHelper.updateStatus(Integer.parseInt(id),"Approved");
                        Toast.makeText(this, id + " Approved Successfully", Toast.LENGTH_SHORT).show();
                        displayAllEmployees();//refresh after show all records ::
                    });

                    //Onlick Action for Reject Button:
                    rejectBtn.setOnClickListener(v->{
                        dbHelper.updateStatus(Integer.parseInt(id),"Rejected");
                        Toast.makeText(this,id+" Rejected Successful" ,Toast.LENGTH_SHORT).show();
                        displayAllEmployees();//refresh after show all records ::
                    });

                }else {
                    TextView tvStatus = new TextView(this);
                    tvStatus.setText(status);
                    tvStatus.setTextSize(14);
                    tvStatus.setPadding(24, 16, 24, 16);
                    tvStatus.setSingleLine(true);
                    tvStatus.setLayoutParams(new TableRow.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    ));

                    if("Approved".equalsIgnoreCase(status))
                    {
                        tvStatus.setTextColor(Color.parseColor("#28a745"));
                    } else if ("Rejected".equalsIgnoreCase(status)) {
                        tvStatus.setTextColor(Color.parseColor("#dc3545"));
                    } else {
                        tvStatus.setTextColor(Color.parseColor("#ffc107"));
                    }
                    row.addView(tvStatus);

                    // Add empty TextView to keep column alignment
                    TextView empty = new TextView(this);
                    empty.setText("");
                    empty.setLayoutParams(new TableRow.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    ));
                    row.addView(empty); // ensures total cells match column count
                }
                tableLayout.addView(row);
                rowIndex++;
            } while (cursor.moveToNext());
        }

        cursor.close();
    }
}
