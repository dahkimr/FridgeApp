package dahkim.com.fridgeapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void goToAddItemView(View view) {
        Intent intent = new Intent(this, FormActivity.class);
        startActivity(intent);
    }

    public void goToFoodView(View view) {
        Intent intent = new Intent(this, FoodActivity.class);
        startActivity(intent);
    }
}
