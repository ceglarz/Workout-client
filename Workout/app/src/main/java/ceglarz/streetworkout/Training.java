package ceglarz.streetworkout;


import android.os.Parcel;
import android.os.Parcelable;

public class Training implements Parcelable{
  private int id;
  private String event;
  private int idPlace;
  private String date;
  private String description;

  public Training() {
  }

  protected Training(Parcel in) {
    id = in.readInt();
    event = in.readString();
    idPlace = in.readInt();
    date = in.readString();
    description = in.readString();
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel parcel, int flags) {
    parcel.writeInt(id);
    parcel.writeString(event);
    parcel.writeInt(idPlace);
    parcel.writeString(date);
    parcel.writeString(description);
  }

  public static final Creator<Training> CREATOR = new Creator<Training>() {
    @Override
    public Training createFromParcel(Parcel in) {
      return new Training(in);
    }

    @Override
    public Training[] newArray(int size) {
      return new Training[size];
    }
  };

  public int getId() {
    return id;
  }

  public String getEvent() {
    return event;
  }

  public int getIdPlace() {
    return idPlace;
  }

  public String getDate() {
    return date;
  }

  public String getDescription() {
    return description;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setEvent(String event) {
    this.event = event;
  }

  public void setIdPlace(int idPlace) {
    this.idPlace = idPlace;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}