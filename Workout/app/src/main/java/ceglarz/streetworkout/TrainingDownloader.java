package ceglarz.streetworkout;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import java.io.IOException;
import java.util.ArrayList;

public class TrainingDownloader extends AsyncTask<Void, Void, String>{
    private String trainingData;
    private String url = "http://192.168.0.199:50002/api/Trainings";
    private ArrayList<Training> foundTrainingsList = new ArrayList<>();

    @SuppressLint("StaticFieldLeak")
    private TrainingCategoryActivity trainingCategoryActivity;

    public TrainingDownloader(TrainingCategoryActivity trainingCategoryActivity) {
        this.trainingCategoryActivity = trainingCategoryActivity;
    }

    @Override
    protected String doInBackground(Void... voids) {
        UrlDownloader urlDownloader = new UrlDownloader();

        try {
            trainingData = urlDownloader.readUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return trainingData;
    }

    @Override
    protected void onPostExecute(String s) {
        DataParser dataParser = new DataParser();
        foundTrainingsList = dataParser.parseTrainings(s);

        trainingCategoryActivity.setTrainingsList(foundTrainingsList);
        trainingCategoryActivity.setList();

        super.onPostExecute(s);
    }
}
