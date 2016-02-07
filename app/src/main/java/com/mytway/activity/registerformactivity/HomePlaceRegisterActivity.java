package com.mytway.activity.registerformactivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.OnMapReadyCallback;
import com.mytway.activity.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mytway.pojo.Position;
import com.mytway.pojo.User;
import com.mytway.properties.PropertiesValues;
import com.mytway.utility.permission.PermissionUtil;
import com.mytway.validation.Validation;

public class HomePlaceRegisterActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final int PERMISSION_REQUEST_CODE_LOCATION = 1;

    private int userId = 0;
    private GoogleMap mMap;

    private double latitudeLocalization;
    private double longitudeLocalization;

    protected Button registerHomeLocalizationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_home_place_form_registration);
//        setUpMapIfNeeded();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.homeMapRegistration);
        mapFragment.getMapAsync(this);

        //initialize
        registerHomeLocalizationButton = (Button) findViewById(R.id.buttonRegisterHomeLocalization);

        Intent intent = getIntent();
        final User user = intent.getParcelableExtra("user");

//        Toast.makeText(HomePlaceRegisterActivity.this, "UserTable: "
//                + user.getUserName() +
//                " email:" + user.getEmail() +
//                " start:" + user.getStartStandardTimeWork() +
//                " Work: " + user.getWorkPlace()
//                , Toast.LENGTH_LONG).show();

        registerHomeLocalizationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomePlaceRegisterActivity.this.getApplicationContext(), "Latitude: " + latitudeLocalization +" Longitude: " + longitudeLocalization, Toast.LENGTH_LONG).show();
                Position workPosition = user.getWorkPlace();
                Position homePosition = new Position(longitudeLocalization, latitudeLocalization);

                if (Validation.homePositionIsNotTheSameWorkPosition(homePosition, workPosition, registerHomeLocalizationButton, getString(R.string.home_place_equals_work_place))) {
                    user.setHomePlace(homePosition);
//
//                    UserRepo userRepo = new UserRepo(HomePlaceRegisterActivity.this);
//
//                    UserTable userTable = new UserTable();
//
//                    userTable.userId = userId;
//                    userTable.userName = user.getUserName();
//                    userTable.password = user.getPassword();
//                    userTable.typeWork = user.getTypeWork().getStatusCode();
//                    userTable.lengthTimeWork = user.getLengthTimeWork();
//                    userTable.startStandardTimeWork = user.getStartStandardTimeWork();
//                    userTable.workPlaceLatitude = user.getWorkPlace().getLatitude();
//                    userTable.workPlaceLongitude = user.getWorkPlace().getLongitude();
//                    userTable.homePlaceLatitude = user.getHomePlace().getLatitude();
//                    userTable.homePlaceLongitude = user.getHomePlace().getLongitude();
//                    userTable.workWeek = user.decodeWorkWeekToString(user.getWorkWeek());
//
//                    if (userId == 0) {
//                        userId = userRepo.insert(userTable);
//
//                        DBHelper.copyDatabaseToSdCard(HomePlaceRegisterActivity.this);
//
//                        try {
//                            String destinationPath = Environment.getExternalStorageDirectory().toString();
//                            File file = new File(destinationPath);
//                            if (!file.exists()) {
//                                file.mkdirs();
//                                file.createNewFile();
//                                // ---copy the db from the /data/data/ folder into
//                                // the sdcard databases folder--- here MyDB is database name
//                                DBHelper.CopyDB(new FileInputStream("/data/data/" + getPackageName()
//                                        + "/databases"), new FileOutputStream(destinationPath + "/MyDB"));
//                            }
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//
//                        Toast.makeText(HomePlaceRegisterActivity.this, "New User Insert", Toast.LENGTH_SHORT).show();
//                    } else {
//                        userRepo.update(userTable);
//                        Toast.makeText(HomePlaceRegisterActivity.this, "User Record updated", Toast.LENGTH_SHORT).show();
//                    }
//
//                    Intent intent = new Intent(HomePlaceRegisterActivity.this, MytwayActivity.class);
//                    intent.putExtra("user", user);
//                    if (intent.resolveActivity(getPackageManager()) != null) {
//                        startActivity(intent);
//                    }
//                } else {
//                    Toast.makeText(HomePlaceRegisterActivity.this, "THE SAME", Toast.LENGTH_SHORT).show();
//                }
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (PermissionUtil.checkPermission(Manifest.permission.ACCESS_FINE_LOCATION, getApplicationContext())
                && PermissionUtil.checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION, getApplicationContext())) {
            fetchLocationData();
        }else{
            PermissionUtil.requestPermission(Manifest.permission.ACCESS_FINE_LOCATION,
                                PERMISSION_REQUEST_CODE_LOCATION,
                                getApplicationContext(),
                                HomePlaceRegisterActivity.this,
                                getString(R.string.localization_will_help_with_choose_your_home_place));
        }
    }

    private void fetchLocationData() {
//        Toast.makeText(WorkPlaceRegisterActivity.this.getApplicationContext(), "FETCH DATA",Toast.LENGTH_LONG).show();
        setUpMap();
    }

    private void setUpMap() {
        // Enable MyLocation Layer of Google Map
        mMap.setMyLocationEnabled(true);

        // Get LocationManager object from System Service LOCATION_SERVICE
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Create a criteria object to retrieve provider
        Criteria criteria = new Criteria();

        // Get the name of the best provider
        String provider = locationManager.getBestProvider(criteria, true);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        Location myLocation = locationManager.getLastKnownLocation(provider);

        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // redraw the marker when get location update.
                drawMarker(location);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String provider) {
            }
        };
        // set map type
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        // Get latitude of the current location
        double latitude = myLocation.getLatitude();

        // Get longitude of the current location
        double longitude = myLocation.getLongitude();

        // Create a LatLng object for the current location
        final LatLng latLng = new LatLng(latitude, longitude);

        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, PropertiesValues.WORK_AND_HOME_PLACE_MAP_ZOOM_LEVEL));
            }
        });

        final String markerTitleWhereIsYourHomePlace = getResources().getString(R.string.marker_title_where_is_your_home_place);

        Marker defaultMarker = mMap.addMarker(new MarkerOptions()
//                .snippet("Lat: " + myLocation.getLatitude() + ", Lng: " + myLocation.getLongitude())
                .position(new LatLng(latitude, longitude))
                .title(markerTitleWhereIsYourHomePlace)
                .flat(true)
                .draggable(true));
        defaultMarker.showInfoWindow();

        setLatitudeLocalization(myLocation.getLatitude());
        setLongitudeLocalization(myLocation.getLongitude());

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
                mMap.clear();
                Marker insertedMarkerManually = mMap.addMarker(new MarkerOptions()
                        .snippet("Please press Save button")
                        .position(point)
                        .title(markerTitleWhereIsYourHomePlace)
                        .flat(true)
                        .draggable(true));

                insertedMarkerManually.showInfoWindow();

                setLatitudeLocalization(point.latitude);
                setLongitudeLocalization(point.longitude);
            }
        });
    }

    private void drawMarker(Location location){
        // Remove any existing markers on the map
        mMap.clear();
        LatLng currentPosition = new LatLng(location.getLatitude(),location.getLongitude());
        mMap.addMarker(new MarkerOptions()
                .position(currentPosition)
                .snippet("Lat:" + location.getLatitude() + "Lng:"+ location.getLongitude())
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                .title("ME"));
        setLatitudeLocalization(location.getLatitude());
        setLongitudeLocalization(location.getLongitude());
    }

    public void setLatitudeLocalization(double latitudeLocalization) {
        this.latitudeLocalization = latitudeLocalization;
    }

    public void setLongitudeLocalization(double longitudeLocalization) {
        this.longitudeLocalization = longitudeLocalization;
    }

    private String getStringResourceByName(String aString) {
        String packageName = getPackageName();
        int resId = getResources().getIdentifier(aString, "string", packageName);
        return getString(resId);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {

            case PERMISSION_REQUEST_CODE_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    fetchLocationData();
                } else {
                    Toast.makeText(getApplicationContext(),"Permission Denied, You cannot access location data.",Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

}
