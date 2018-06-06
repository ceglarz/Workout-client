package ceglarz.streetworkout;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class PlacesDownloader extends AsyncTask<Void, Void, String> {

    private String placesData;
    private String url = "http://192.168.0.199:50001/api/Places/";
    private ArrayList<Place> foundPlacesList = new ArrayList<>();

    @SuppressLint("StaticFieldLeak")
    private PlaceCategoryActivity placeCategoryActivity;

    public PlacesDownloader(PlaceCategoryActivity placeCategoryActivity) {
        this.placeCategoryActivity = placeCategoryActivity;
    }


    @Override
    protected String doInBackground(Void... voids) {
        UrlDownloader urlDownloader = new UrlDownloader();

        try {
            placesData = urlDownloader.readUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return placesData;
    }

    @Override
    protected void onPostExecute(String s) {
        DataParser dataParser = new DataParser();
        foundPlacesList = dataParser.parsePlaces(s);
        Collections.sort(foundPlacesList, new PlacesComparator());

        placeCategoryActivity.setPlacesList(foundPlacesList);
        placeCategoryActivity.setList();

        super.onPostExecute(s);
    }
}
