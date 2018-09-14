package fz.bvritbustracker;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    public static Location myLocation;
    private static double busLatitude,busLongitude;
    private LocationManager locationManager;
    private ProgressDialog progressDialog;
    protected ImageView busLocation, showMyLocation, getDirections;
    List<Address> addresses;
    private DatabaseReference databaseReference;
    static String code;
    private TextView routeCodeBus,nearbyplaces,presentLocationBus,distanceBtwBus,lastUpdated;
    ArrayList<LatLng> markerPoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        myLocation = null;
//        busLocationDataFromServer = null;
        busLocation = findViewById(R.id.busLocation);
        showMyLocation = findViewById(R.id.myLocation);
        getDirections = findViewById(R.id.getDirection);
        routeCodeBus = findViewById(R.id.busRouteCode);
        presentLocationBus = findViewById(R.id.presentLocationBus);
        distanceBtwBus = findViewById(R.id.distanceBtwBus);
        lastUpdated = findViewById(R.id.lastUpdated);
        nearbyplaces = findViewById(R.id.nearbyPlaces);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (ContextCompat.checkSelfPermission(MapsActivity.this, "android.permission.ACCESS_FINE_LOCATION")!= PackageManager.PERMISSION_GRANTED) {
           if (ActivityCompat.shouldShowRequestPermissionRationale(MapsActivity.this,
                    "android.permission.ACCESS_FINE_LOCATION")) {

            } else {
               ActivityCompat.requestPermissions(MapsActivity.this,
                        new String[]{"android.permission.ACCESS_FINE_LOCATION","android.permission.ACCESS_COARSE_LOCATION"},
                        1);
           }
        }else {
//            Toast.makeText(getApplicationContext(),"GPS Permission Granted",Toast.LENGTH_SHORT).show();
        }

        //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60, 5, mLocationListener);


        Intent intent = getIntent();
        code = intent.getStringExtra("code");
        routeCodeBus.setText(code);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Bus Tracker");

        databaseReference.keepSynced(true);
        checkDataChange();

        showMyLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                    Toast.makeText(MapsActivity.this, "GPS must be enabled", Toast.LENGTH_SHORT).show();
                    return;
                }

                databaseReference.keepSynced(false);
                mMap.clear();

                LatLng sydney = null;
                try {
                    sydney = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
                }catch (NullPointerException e){

                }

                mMap.addMarker(new MarkerOptions().position(sydney).title("Location of bus:" + code ));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(sydney, 10);
                mMap.animateCamera(yourLocation);

                Geocoder gcd = new Geocoder(getApplicationContext(), Locale.getDefault());
                try {
                    addresses = gcd.getFromLocation(myLocation.getLatitude(), myLocation.getLongitude(), 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (addresses.size() > 0) {
                     Toast.makeText(getApplicationContext(),"You are at " + addresses.get(0).getLocality(),Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(),"You are at unknown location",Toast.LENGTH_SHORT).show();
                }
            }
        });

        busLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkDataChange();
            }
        });

        getDirections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.keepSynced(false);
                mMap.clear();
                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                    Toast.makeText(MapsActivity.this, "GPS must be enabled", Toast.LENGTH_SHORT).show();
                    return;
                }

               // if (myLocation.getLatitude() == null || myLocation.getLongitude()!=0.0d || busLocationDataFromServer.getLatitude()!=0.0d ||busLocationDataFromServer.getLongitude()!=0.0d ){
                    LatLng origin = new LatLng(myLocation.getLatitude(),myLocation.getLongitude());
                    LatLng dest = new LatLng(busLatitude,busLongitude);
                    // Getting URL to the Google Directions API
                    String url = getDirectionsUrl(origin, dest);

                    DownloadTask downloadTask = new DownloadTask();

                    // Start downloading json data from Google Directions API
                    downloadTask.execute(url);
                //}
            }
        });
    }

    private void callDistanceCheck(String s){
        LatLng start = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
        if (busLatitude!=0.0d && busLongitude!=0.0d){
            LatLng end = new LatLng(busLatitude,busLongitude);
            CalculationByDistance(start,end);
        }else{
            if (s.equals("initial")){
                distanceBtwBus.setText("Distance: Please wait...");
            }else {
            }
        }
    }

    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location loc) {
            //your code here
            myLocation = loc;
           // Toast.makeText(getApplicationContext(),"Location Changed \n" + myLocation.getLatitude() + "\n" + myLocation.getLongitude() ,Toast.LENGTH_SHORT).show();
            callDistanceCheck("initial");
        }
        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
            // Toast.makeText(getApplicationContext(),"Status changed, " + s,Toast.LENGTH_SHORT).show();
        }
        @Override
        public void onProviderEnabled(String s) {
            Toast.makeText(getApplicationContext(),s+" is enabled",Toast.LENGTH_SHORT).show();
        }
        @Override
        public void onProviderDisabled(String s) {
            Toast.makeText(getApplicationContext(), s+" is disabled",Toast.LENGTH_SHORT).show();
        }
    };

    void checkDataChange() {
        databaseReference.keepSynced(true);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataModel dm = new DataModel();
                for (DataSnapshot d: dataSnapshot.getChildren()){
                    dm = d.getValue(DataModel.class);
                    if (dm.getRouteCode().equals(code))
                        break;
                }
                if (dm.getRouteCode()==null || dm.getLatitude()==null || dm.getLongitude()==null){
                    Toast.makeText(getApplicationContext(),"Something went wrong, please try again",Toast.LENGTH_SHORT).show();
                    return;
                }
//                Toast.makeText(getApplicationContext(),"Last seen"+ dm.getLastSeen() + "\n"+ dm.getLatitude()+"\n"+dm.getLongitude(),Toast.LENGTH_LONG).show();
                double latt = Double.parseDouble(dm.getLatitude());
                busLatitude = Double.parseDouble(dm.getLatitude());
                double longit = Double.parseDouble(dm.getLongitude());
                busLongitude = Double.parseDouble(dm.getLongitude());
                //Toast.makeText(getApplicationContext(), "" + latt + " " +longit, Toast.LENGTH_SHORT).show();

  //              busLocationDataFromServer.setLatitude(latt);

//                Toast.makeText(getApplicationContext(),"Navigating to location of bus",Toast.LENGTH_LONG).show();
//                myLocation.setLatitude(Double.parseDouble(dm.getLatitude()));
//                myLocation.setLongitude(Double.parseDouble(dm.getLongitude()));
//                Toast.makeText(getApplicationContext(),"Location Changed \n" + myLocation.getLatitude() + "\n" + myLocation.getLongitude() ,Toast.LENGTH_SHORT).show();

                nearbyplaces.setText("Nearby Places: " + dm.getNearbyPlaces());
                lastUpdated.setText("Last updated: " + dm.getLastSeen());
                mMap.clear();
                LatLng sydney = new LatLng(latt,longit);
                mMap.addMarker(new MarkerOptions().position(sydney).title("Location of bus:"+code));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(sydney, 10);
                mMap.animateCamera(yourLocation);

                Geocoder gcd = new Geocoder(getApplicationContext(), Locale.getDefault());
                try {
                    addresses = gcd.getFromLocation(latt, longit, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (addresses.size() > 0) {
                    presentLocationBus.setText(addresses.get(0).getLocality()+","+addresses.get(0).getSubLocality());
                    //  System.out.println(addresses.get(0).getLocality());
                    //Toast.makeText(getApplicationContext(),addresses.get(0).getLocality(),Toast.LENGTH_SHORT).show();
                }
                else { }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        progressDialog.dismiss();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ContextCompat.checkSelfPermission(MapsActivity.this, "android.permission.ACCESS_FINE_LOCATION")!= PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MapsActivity.this,
                    "android.permission.ACCESS_FINE_LOCATION")) {

            } else {
                ActivityCompat.requestPermissions(MapsActivity.this,
                        new String[]{"android.permission.ACCESS_FINE_LOCATION","android.permission.ACCESS_COARSE_LOCATION"},
                        1);
            }
        }else {
            Toast.makeText(getApplicationContext(),"GPS Permission Granted",Toast.LENGTH_SHORT).show();
        }
        checkDataChange();

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 50, mLocationListener);
        /*// Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        progressDialog.dismiss();   */
    }

    public void CalculationByDistance(LatLng StartP, LatLng EndP) {
        int Radius = 6371;// radius of earth in Km
        double lat1 = StartP.latitude;
        double lat2 = EndP.latitude;
        double lon1 = StartP.longitude;
        double lon2 = EndP.longitude;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = Radius * c;
        double km = valueResult / 1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec = Integer.valueOf(newFormat.format(km));
        double meter = valueResult % 1000;
        int meterInDec = Integer.valueOf(newFormat.format(meter));
        Log.i("Radius Value", "" + valueResult + "   KM  " + kmInDec
                + " Meter   " + meterInDec);

        double res  = Radius * c;

        DecimalFormat df = new DecimalFormat("####0.00");
        distanceBtwBus.setText("Distance: " + df.format(valueResult) + " KM ");
    }

    private String getDirectionsUrl(LatLng origin,LatLng dest){

        // Origin of route
        String str_origin = "origin="+origin.latitude+","+origin.longitude;

        // Destination of route
        String str_dest = "destination="+dest.latitude+","+dest.longitude;


        // Sensor enabled
        String sensor = "sensor=false";

        // Building the parameters to the web service
        String parameters = str_origin+"&"+str_dest+"&"+sensor;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters;


        return url;
    }

    /** A method to download json data from url */
    private String downloadUrl(String strUrl) throws IOException{
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb  = new StringBuffer();

            String line = "";
            while( ( line = br.readLine())  != null){
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        }catch(Exception e){
            Log.d("Exception in url", e.toString());
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    // Fetches data from url passed
    private class DownloadTask extends AsyncTask<String, Void, String>{

        // Downloading data in non-ui thread
        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try{
                // Fetching the data from web service
                data = downloadUrl(url[0]);
            }catch(Exception e){
                Log.d("Background Task",e.toString());
            }
            return data;
        }

        // Executes in UI thread, after the execution of
        // doInBackground()
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);

        }
    }

    /** A class to parse the Google Places in JSON format */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>> > {

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try{
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                // Starts parsing data
                routes = parser.parse(jObject);
            }catch(Exception e){
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points = null;
            PolylineOptions lineOptions = null;
            MarkerOptions markerOptions = new MarkerOptions();

            // Traversing through all the routes
            for(int i=0;i<result.size();i++){
                points = new ArrayList<LatLng>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for(int j=0;j<path.size();j++){
                    HashMap<String,String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(2);
                lineOptions.color(Color.RED);

            }

            mMap.clear();
            // Drawing polyline in the Google Map for the i-th route
            mMap.addPolyline(lineOptions);
        }
    }


}
