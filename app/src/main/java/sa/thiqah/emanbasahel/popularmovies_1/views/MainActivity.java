package sa.thiqah.emanbasahel.popularmovies_1.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;

import sa.thiqah.emanbasahel.popularmovies_1.R;

public class MainActivity extends AppCompatActivity {

    ImageButton actionSort;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        actionSort = findViewById(R.id.action_sort);
        //ToDo show dialog with sorting options
    }
}
