package ceglarz.streetworkout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class TopLevelActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_level);
        // Tworzymy obiekt nasłuchujący OnItemClickListener
        AdapterView.OnItemClickListener itemClickListener =
                new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> listView,
                                            View v,
                                            int position,
                                            long id) {
                        Intent intent;
                        switch (position){
                            case 0:
                                intent = new Intent(TopLevelActivity.this,
                                        PlaceCategoryActivity.class);
                                startActivity(intent);
                                break;
                            case 1:
                                intent = new Intent(TopLevelActivity.this,
                                        TrainingCategoryActivity.class);
                                startActivity(intent);
                                break;
                        }

                    }
                };
        // Dodajemy obiekt nasłuchujący do widoku listy
        ListView listView = findViewById(R.id.list_options);
        listView.setOnItemClickListener(itemClickListener);
    }
}
