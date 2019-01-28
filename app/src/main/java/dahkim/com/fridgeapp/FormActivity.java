package dahkim.com.fridgeapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class FormActivity extends AppCompatActivity {

    private static final String TAG = "FormActivity";

    DatabaseHelper mDatabaseHelper;
    private EditText nameEI;
    private Spinner typeSpin;
    private TextView expDateTV;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private Button addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        // initialize variables
        nameEI = (EditText) findViewById(R.id.nameET);
        typeSpin = (Spinner) findViewById(R.id.typeSpin);
        expDateTV = (TextView) findViewById(R.id.expDateTV);
        addBtn = (Button) findViewById(R.id.addBtn);
        mDatabaseHelper = new DatabaseHelper(this);

        // drop down menu for choosing food type
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpin.setAdapter(adapter);

        // date picker for choosing exp date
        expDateTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(FormActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        dateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: date: " + year + "/" + month + "/" + day);

                String date = month + "/" + day + "/" + year;
                expDateTV.setText(date);
            }
        };

        // click on Add button
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // add what's in name field to database
                String name = nameEI.getText().toString();
                String type = typeSpin.getSelectedItem().toString(); ;
                String expDate = expDateTV.getText().toString();
                if (name.length() != 0 && type.length() != 0 && expDate.length() != 0) {
                    AddData(name, type, expDate);
                    nameEI.setText("");
                    // reset spinner and expdate textview
                } else {
                    Toast.makeText(FormActivity.this, "Certain fields are empty.", Toast.LENGTH_SHORT).show();
                }

                // open new activity with list
                Intent intent = new Intent(FormActivity.this, FoodActivity.class);
                startActivity(intent);
            }
        });
    }

    public void AddData(String name, String type, String expDate) {
        boolean insertData = mDatabaseHelper.addData(name, type, expDate);

        if (insertData) {
            Toast.makeText(this, "Data Successfully Inserted!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }
}
