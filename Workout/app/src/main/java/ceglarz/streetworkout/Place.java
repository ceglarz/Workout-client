package ceglarz.streetworkout;


import android.os.Parcel;
import android.os.Parcelable;

public class Place implements Parcelable{

    private int id;
    private String name;
    private String description;
    private double latitude;
    private double longitude;
    private int iloscOsob;
    private double odleglosc;


//    public Place(String name, String description, double latitude, double longitude, int iloscOsob, double odleglosc) {
//        this.name = name;
//        this.description = description;
//        this.latitude = latitude;
//        this.longitude = longitude;
//        this.iloscOsob = iloscOsob;
//        this.odleglosc = odleglosc;
//    }

    public Place() {
    }

    // Każdy Place ma nazwę, opis oraz położenie geograficzne
    Place(String name, String description, double latitude, double longitude) {
        this.name = name;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    protected Place(Parcel in) {
        id = in.readInt();
        name = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
        description = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeDouble(latitude);
        parcel.writeDouble(longitude);
        parcel.writeString(description);
    }

    public static final Creator<Place> CREATOR = new Creator<Place>() {
        @Override
        public Place createFromParcel(Parcel in) {
            return new Place(in);
        }

        @Override
        public Place[] newArray(int size) {
            return new Place[size];
        }
    };

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String toString() {
        return this.name;
    }

    public int getIloscOsob() {
        return iloscOsob;
    }

    public double getOdleglosc() {
        return odleglosc;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setIloscOsob(int iloscOsob) {
        this.iloscOsob = iloscOsob;
    }

    public void setOdleglosc(double odleglosc) {
        this.odleglosc = odleglosc;
    }
}
