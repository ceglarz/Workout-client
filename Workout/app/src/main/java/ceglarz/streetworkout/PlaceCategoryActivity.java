package ceglarz.streetworkout;

import android.app.ListActivity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.widget.ListView;
import android.view.View;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

public class PlaceCategoryActivity extends ListActivity {
    private List<Place> placesList = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        PlacesDownloader placesDownloader = new PlacesDownloader(this);
        placesDownloader.execute();
    }

    @Override
    public void onListItemClick(ListView listView, View itemView, int position, long id) {
        Intent intent = new Intent(PlaceCategoryActivity.this, PlaceActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("place", placesList.get(position));
        intent.putExtra("bundle", bundle);

        startActivity(intent);
    }

    public Place getPlace(int id) {
        return placesList.get(id);
    }

    public void setPlacesList(List<Place> placesList) {
        this.placesList = placesList;
    }

    public void setList(){
        ListView listPlaces = getListView();
        MyPlacesListAdapter adapter = new MyPlacesListAdapter(placesList, R.layout.list_element_place, this );
        listPlaces.setAdapter(adapter);
    }
}
