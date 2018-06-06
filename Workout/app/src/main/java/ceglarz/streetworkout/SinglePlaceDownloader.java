package ceglarz.streetworkout;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import java.io.IOException;

public class SinglePlaceDownloader extends AsyncTask<Void, Void, String> {

    private String placesData;
    private String url = "http://192.168.0.199:50001/api/Places/";
    private Place place;

    @SuppressLint("StaticFieldLeak")
    private TrainingActivity trainingActivity;

    public SinglePlaceDownloader(TrainingActivity trainingActivity, int placeNumber) {
        this.trainingActivity = trainingActivity;
        url = url + placeNumber;
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
        place = dataParser.parsePlace(s);

        trainingActivity.setPlace(place);


        super.onPostExecute(s);
    }
}