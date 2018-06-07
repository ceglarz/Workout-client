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
        double distance;
        double myLatitude = 51.234396, myLongitude = 22.525969;

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

                distance = countDistance(myLatitude, myLongitude, placeJObject.optDouble("latitude"),placeJObject.optDouble("longitude"));
                place.setOdleglosc(distance);

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

    //liczenie odleglosci na plaskiej Ziemii
    private double countDistance(double myLatitude, double myLongitude, double latitude, double longitude){

        double earthRadius = 3958.75;
        double dLat = Math.toRadians(myLatitude- latitude);
        double dLng = Math.toRadians(myLongitude - longitude);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(myLatitude)) * Math.cos(Math.toRadians(latitude))
                        * Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double dist = earthRadius * c;
        int meterConversion = 1609;
        return (int)(dist * meterConversion);
    }

}//koniec klasy
