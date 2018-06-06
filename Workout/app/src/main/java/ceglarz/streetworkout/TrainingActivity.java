package ceglarz.streetworkout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TrainingActivity extends AppCompatActivity {

    TextView data;
    TextView event;
    TextView description;
    Training training;
    Button showPlaceBuuton;
    Place place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);

        // Pobieramy trening z intencji
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle2");
        if(bundle != null){
            training = bundle.getParcelable("training");
        }
        else{
            training = new Training();
        }


        // Wyświetlamy date i event
        data = findViewById(R.id.data);
        data.setText(training.getDate());

        event = findViewById(R.id.name);
        event.setText(training.getEvent());

        // Wyświetlamy opis treningu
        description = findViewById(R.id.trainingDescription);
        description.setText(training.getDescription());

        SinglePlaceDownloader singlePlaceDownloader = new SinglePlaceDownloader(this, training.getIdPlace());
        singlePlaceDownloader.execute();
    }

    public void onClick(View view) {
        showPlaceBuuton = findViewById(R.id.button_show_place);
        Intent intent = new Intent(TrainingActivity.this, PlaceActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("place", place);
        intent.putExtra("bundle", bundle);

        startActivity(intent);


    }

    public void setPlace(Place place) {
        this.place = place;
    }
}
