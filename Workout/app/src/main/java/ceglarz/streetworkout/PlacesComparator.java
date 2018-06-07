package ceglarz.streetworkout;

import java.util.Comparator;

/**
 * Created by Oskar on 2018-06-06.
 */

public class PlacesComparator implements Comparator<Place> {


    // Overriding the compare method to sort the distance
    @Override
    public int compare(Place p1, Place p2) {
        return (int)(p1.getOdleglosc() - p2.getOdleglosc());
    }

}
