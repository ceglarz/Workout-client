package ceglarz.streetworkout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class DataParser {

    ArrayList<Place> parsePlaces(String jsonData){
        ArrayList<Place> foundPlaces = new ArrayList<>();
        JSONArray placesArray;
        JSONObject placeJObject;
        Place place;

        try {
            placesArray = new JSONArray(jsonData);

            for(int i = 0; i<placesArray.length(); i++){
                place= new Place();
                placeJObject = placesArray.getJSONObject(i);
                place.setId(placeJObject.optInt("id"));
                place.setName(placeJObject.optString("name"));
                place.setLatitude(placeJObject.optDouble("latitude"));
                place.setLongitude(placeJObject.optDouble("longitude"));
                place.setDescription(placeJObject.optString("description"));
                place.setIloscOsob(placeJObject.optInt("active"));


                foundPlaces.add(place);
            }

        } catch (Exception e){
            e.printStackTrace();
        }

        return foundPlaces;
    }

    Place parsePlace(String jsonData){
        JSONObject placeJObject;
        Place place = new Place();

        try {
            placeJObject = new JSONObject(jsonData);

                place.setId(placeJObject.optInt("id"));
                place.setName(placeJObject.optString("name"));
                place.setLatitude(placeJObject.optDouble("latitude"));
                place.setLongitude(placeJObject.optDouble("longitude"));
                place.setDescription(placeJObject.optString("description"));
                place.setIloscOsob(placeJObject.optInt("active"));


        } catch (Exception e){
            e.printStackTrace();
        }

        return place;
    }

    ArrayList<Training> parseTrainings(String jsonData){
        ArrayList<Training> foundTrainings = new ArrayList<>();
        JSONArray trainingsArray;
        JSONObject trainingJObject;
        Training training;

        try {
            trainingsArray = new JSONArray(jsonData);

            for(int i = 0; i<trainingsArray.length(); i++){
                training = new Training();
                trainingJObject = trainingsArray.getJSONObject(i);
                training.setId(trainingJObject.optInt("id"));
                training.setEvent(trainingJObject.optString("event"));
                training.setIdPlace(trainingJObject.optInt("idPlace"));
                training.setDate(trainingJObject.getString("date"));
                training.setDescription(trainingJObject.optString("description"));

                foundTrainings.add(training);
            }

        } catch (Exception e){
            e.printStackTrace();
        }

        return foundTrainings;
    }

}
