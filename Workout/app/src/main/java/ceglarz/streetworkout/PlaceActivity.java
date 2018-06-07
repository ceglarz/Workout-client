package ceglarz.streetworkout;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


public class PlaceActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    public static final String EXTRA_PLACENO = "placeNo";
    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private double latitude,longitude;
    TextView description;
    Place place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }

        //Check if Google Play Services Available or not
        if (!CheckGooglePlayServices()) {
            Log.d("onCreate", "Finishing test case since Google Play Services are not available");
            finish();
        }
        else {
            Log.d("onCreate","Google Play Services available.");
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Pobieramy plac z intencji
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        if(bundle != null){
            place = bundle.getParcelable("place");
        }
        else{
            place = new Place();
        }


        // Wyświetlamy nazwę placu
        TextView name = (TextView)findViewById(R.id.name);
        name.setText(place.getName());

        // Wyświetlamy opis placu
        description = (TextView)findViewById(R.id.description);
        description.setText(place.getDescription());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }


    }

    private void ustawMarker(){
        mMap.clear();
        mMap.animateCamera(CameraUpdateFactory.zoomBy(0));

        //dodawanie markera na mape:
        MarkerOptions markerOptions = new MarkerOptions();
        LatLng latLng = new LatLng(place.getLatitude(), place.getLongitude());
        markerOptions.position(latLng);
        markerOptions.title(place.getName());
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
        mMap.addMarker(markerOptions);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(12));
    }

    private boolean CheckGooglePlayServices() {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(this);
        if(result != ConnectionResult.SUCCESS) {
            if(googleAPI.isUserResolvableError(result)) {
                googleAPI.getErrorDialog(this, result,
                        0).show();
            }
            return false;
        }
        return true;
    }

    protected synchronized void buildGoogleApiClient(){
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private void checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_LONG).show();
                }
            }

        }
    }
    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();

        //description.setText(description.getText() +  "\n" + latitude + "\n" + longitude);
        description.setText(description.getText());

        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            Log.d("onLocationChanged", "Removing Location Updates");
        }

        ustawMarker();
    }

    /*
    public void parseXML2(){

    }
        InputStream getUrlData(String url) throws URISyntaxException, ClientProtocolException, IOException {

            DefaultHttpClient client = new DefaultHttpClient();
            HttpGet method = new HttpGet(new URI(url));
            HttpResponse res = client.execute(method);
            return res.getEntity().getContent();

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new InputStreamReader(
                    getUrlData("url")));

            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                //Log.i(TAG, "doc started");
                if (xpp.getEventType() == XmlPullParser.START_TAG) {
                    if (xpp.getName().equals("entry")) {
                        items.add(xpp.getAttributeValue(0));
                    }
                }
                xpp.next();
            }
        } catch (Throwable t) {
            Toast.makeText(this, "Request failed: " + t.toString(),
                    Toast.LENGTH_LONG).show();
        }
    }
    */


    public void parseXML1(){

        /* Create a new layout to display the view */
        /*LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(1);
        */

        /* Create a new textview array to display the results */
        TextView name[];
        TextView description[];
        TextView latitude[];
        TextView longitude[];


        try {

            URL url = new URL("http://192.168.0.199:50714/api/places.xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new InputSource(url.openStream()));
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("item");

            /** Assign textview array lenght by arraylist size */
            name = new TextView[nodeList.getLength()];
            description = new TextView[nodeList.getLength()];
            latitude = new TextView[nodeList.getLength()];
            longitude = new TextView[nodeList.getLength()];

            for (int i = 0; i < nodeList.getLength(); i++) {

                Node node = nodeList.item(i);

                name[i] = new TextView(this);
                description[i] = new TextView(this);
                latitude[i] = new TextView(this);
                longitude[i] = new TextView(this);

                Element fstElmnt = (Element) node;

                NodeList nameList = fstElmnt.getElementsByTagName("name");
                Element nameElement = (Element) nameList.item(0);
                nameList = nameElement.getChildNodes();
                name[i].setText("Name = " + ((Node) nameList.item(0)).getNodeValue());

                NodeList descriptionList = fstElmnt.getElementsByTagName("description");
                Element descriptionElement = (Element) descriptionList.item(0);
                descriptionList = descriptionElement.getChildNodes();
                description[i].setText("Description = " + ((Node) descriptionList.item(0)).getNodeValue());

                NodeList latitudeList = fstElmnt.getElementsByTagName("latitude");
                Element latitudeElement = (Element) latitudeList.item(0);
                latitudeList = descriptionElement.getChildNodes();
                //latitude = ((Node) latitudeList.item(0)).getNodeValue()
                description[i].setText("Latitude = " + ((Node) latitudeList.item(0)).getNodeValue());

                NodeList longitudeList = fstElmnt.getElementsByTagName("description");
                Element longitudeElement = (Element) descriptionList.item(0);
                longitudeList = longitudeElement.getChildNodes();
                //longitude = ((Node) longitudeList.item(0)).getNodeValue()
                description[i].setText("Longitude = " + ((Node) longitudeList.item(0)).getNodeValue());

                /*
                layout.addView(name[i]);
                layout.addView(description[i]);
                */
            }
        } catch (Exception e) {
            System.out.println("XML Pasing Excpetion = " + e);
        }

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                    locationRequest, this);

        }
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

}