package ceglarz.streetworkout;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TrainingCategoryActivity extends ListActivity {
  private List<Training> trainingsList = new ArrayList<>();


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    TrainingDownloader trainingDownloader = new TrainingDownloader(this);
    trainingDownloader.execute();
  }

  @Override
  public void onListItemClick(ListView listView, View itemView, int position,  long id) {
    Intent intent = new Intent(TrainingCategoryActivity.this, TrainingActivity.class);
    Bundle bundle = new Bundle();
    bundle.putParcelable("training", trainingsList.get(position));
    intent.putExtra("bundle2", bundle);

    startActivity(intent);
  }

  public void setList(){
    ListView listTrainings = getListView();
    MyTrainingsListAdapter adapter = new MyTrainingsListAdapter(trainingsList,
            R.layout.list_element_training, this );
    listTrainings.setAdapter(adapter);
  }

  public void setTrainingsList(List<Training> trainingsList) {
    this.trainingsList = trainingsList;
  }
}