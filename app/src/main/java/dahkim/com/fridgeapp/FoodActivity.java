package dahkim.com.fridgeapp;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

public class FoodActivity extends AppCompatActivity {

    private static final String TAG = "FoodActivity";

    DatabaseHelper mDatabaseHelper;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String name, type, expDate;
        TextView nameTV, typeTV, expDateTV;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        // initialize variables
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mDatabaseHelper = new DatabaseHelper(this);

        populateRecyclerView();
    }

    private void populateRecyclerView() {
        Log.d(TAG, "populateRecyclerView: Displaying data in the RecyclerView");

        // get data and append to list
        Cursor data = mDatabaseHelper.getData();
        ArrayList<String> mNames = new ArrayList<>();
        ArrayList<String> mTypes = new ArrayList<>();
        ArrayList<String> mExpDates = new ArrayList<>();

        while(data.moveToNext()){
            // get value from database in column 1 and add to arraylist
            mNames.add(data.getString(1));
            mTypes.add(data.getString(2));
            mExpDates.add(data.getString(2));
        }

        // create recyclerview adapter and set adapter
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, mNames, mTypes, mExpDates);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
