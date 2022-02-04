package com.pranav.acemoneyml.activity;

import static com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.CancellationTokenSource;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.pranav.acemoneyml.R;
import com.pranav.acemoneyml.adapter.RvItemAdapter;
import com.pranav.acemoneyml.model.ActorsModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity implements RvItemAdapter.ActorsDetailsInterface {
    Context context;
    TextView tvCustomerName;
    TextView tvAddress;
    TextView tvLatitude;
    Intent intent;
    String email;
    List<ActorsModel> actorsModels = new ArrayList<>();
    RecyclerView rvActors;
    RvItemAdapter rvItemAdapter;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private CancellationTokenSource cancellationTokenSource;

    private boolean hasInternetConnection(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Network nw = connectivityManager.getActiveNetwork();
            if (nw == null) return false;
            NetworkCapabilities actNw = connectivityManager.getNetworkCapabilities(nw);
            return actNw != null && (actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) || actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH));
        } else {
            NetworkInfo nwInfo = connectivityManager.getActiveNetworkInfo();
            return nwInfo != null && nwInfo.isConnected();
        }
    }

    private void initActivity() {
        tvCustomerName = findViewById(R.id.tvCustomerName);
        tvAddress = findViewById(R.id.tvAddress);
        tvLatitude = findViewById(R.id.tvLatitude);
        rvActors = findViewById(R.id.rvActors);
        rvItemAdapter = new RvItemAdapter(context, actorsModels, this);
    }

    private void checkAndRequestPermissions() {

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            List<String> permissions = new ArrayList<>();
            permissions.add(android.Manifest.permission.ACCESS_COARSE_LOCATION);
            permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
            permissions.add(Manifest.permission.SYSTEM_ALERT_WINDOW);
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                permissions.add(Manifest.permission.ACCESS_BACKGROUND_LOCATION);
            }
            List<String> listPermissionsNeeded = new ArrayList<>();
            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                    listPermissionsNeeded.add(permission);
                }
            }
            try {
                if (!listPermissionsNeeded.isEmpty()) {
                    ActivityCompat.requestPermissions((Activity) context, listPermissionsNeeded.toArray(new String[0]), 1001);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_home);
        initActivity();
        hasInternetConnection(context);
        cancellationTokenSource = new CancellationTokenSource();
        intent = getIntent();
        if (intent.getExtras().getString("email") != null) {
            email = intent.getExtras().getString("email");
        }
        tvCustomerName.setText(email);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        if (isLocationEnabled()) {
            getTheLocation();
        } else {
            Log.e("onCreate: ", "Enable permission");
            enableLocationPermission();
        }
        checkAndRequestPermissions();
        rvActors.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        rvActors.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        rvActors.setAdapter(rvItemAdapter);
        actorsModels.add(new ActorsModel("Mohanlal",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/c/cb/Mohanlal_Viswanathan_Nair_BNC.jpg/330px-Mohanlal_Viswanathan_Nair_BNC.jpg",
                "Mohanlal Viswanathan (born 21 May 1960), known mononymously as Mohanlal, is an Indian actor, film producer, playback singer, television host and film distributor who predominantly works in Malayalam cinema besides also having sporadically appeared in Tamil, Telugu, Kannada and Hindi-language films"));
        actorsModels.add(new ActorsModel("Prithviraj Sukumaran",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/3/3b/Prithviraj_oil_paint_2019.jpg/330px-Prithviraj_oil_paint_2019.jpg",
                "Prithviraj Sukumaran (born 16 October 1982) is an Indian actor, director, producer, and playback singer known for his works primarily in Malayalam cinema.[2] He has also appeared in Tamil and Hindi language films"));
        actorsModels.add(new ActorsModel("Dileep",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/7/71/Dileep2016.jpg/330px-Dileep2016.jpg",
                "Gopalakrishnan Padmanabhan (born 27 October 1967), better known by his stage name Dileep, is an Indian actor, producer, and businessman who predominantly works in the Malayalam film industry"));
        actorsModels.add(new ActorsModel("Tovino Thomas",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/4/49/Tovino_1_Purple_CG_-_Full_Size.jpg/353px-Tovino_1_Purple_CG_-_Full_Size.jpg",
                "Tovino Thomas (born 21 January 1989) is an Indian actor, model, and film producer who is active in Malayalam films.[2] He made his debut in 28 January[3] 2012 with the Malayalam film Prabhuvinte Makkal"));
        actorsModels.add(new ActorsModel("Fahadh Faasil",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/4/4c/Fahadhfaasil.jpg/330px-Fahadhfaasil.jpg",
                "Fahadh Faasil (born Abdul Hameed Mohammed Fahad Fazil; 8 August 1982) is an Indian actor and producer who predominantly works in Malayalam and a few Tamil films"));
        actorsModels.add(new ActorsModel("Suresh Gopi",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b6/Suresh_Gopi.jpg/330px-Suresh_Gopi.jpg",
                "Suresh Gopi (born 26 June 1958) is an Indian actor, politician, playback singer and television presenter. He works predominantly in Malayalam cinema and has also appeared in some Tamil, Telugu, Kannada and Bollywood films"));
        actorsModels.add(new ActorsModel("Nivin Pauly",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/4/43/Nivin_Pauly_at_the_Filmfare_Middle_East_Achievers_Night.jpg/330px-Nivin_Pauly_at_the_Filmfare_Middle_East_Achievers_Night.jpg",
                "Nivin Pauly (born 11 October 1984) is an Indian actor and producer who works predominantly in Malayalam film industry. He is a recipient of several awards including two Kerala State Film Awards and three Filmfare South awards"));
        actorsModels.add(new ActorsModel("Murali",
                "https://upload.wikimedia.org/wikipedia/en/c/c1/Murali_%28actor%29.jpg",
                "Muraleedharan Pillai, popularly known as Murali (Malayalam: മുരളി, 25 May 1954 – 6 August 2009) was an Indian film, stage and television actor and author"));
        actorsModels.add(new ActorsModel("Asif Ali",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/1/18/Asif_Ali_Malayalam_Actor.jpg/330px-Asif_Ali_Malayalam_Actor.jpg",
                "Asif Ali is an Indian actor and producer, who predominantly works in the Mollywood film industry.[3] He started his film career with Shyamaprasad's 2009 film, Ritu"));
        actorsModels.add(new ActorsModel("Kunchacko Boban",
                "https://upload.wikimedia.org/wikipedia/en/thumb/1/12/Kunchackoboban002.jpg/330px-Kunchackoboban002.jpg",
                "Kunchacko Boban (born 2 November 1976) is an Indian actor and producer.[2] Sometimes referred by Chackochan, he works in Malayalam film industry and has acted in more than 90 films over the period of more than two decades. He has received several awards, including the Kerala State Film Award"));
        actorsModels.add(new ActorsModel("Jayaram",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/0/06/Jayaram_2008.jpg/330px-Jayaram_2008.jpg",
                "Jayaram Subramaniam, (born 10 December 1965) known mononymously as Jayaram, is an Indian actor who predominantly appears in Malayalam and Tamil films along with a few Telugu films. He is also a chenda percussionist, mimicry artist, and occasional playback singer"));


        rvItemAdapter.updateAdapter(actorsModels);
    }
    private void getTheLocation() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationProviderClient.getCurrentLocation(PRIORITY_HIGH_ACCURACY, cancellationTokenSource.getToken()).addOnCompleteListener(task -> {
            try {
                Location location = task.getResult();
                if (location != null) {
                    Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                    List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    tvLatitude.setText("LAT=" + location.getLatitude() + "LONG=" + location.getLongitude());
                    tvAddress.setText(addresses.get(0).getAddressLine(0));
                } else {
                    Toast.makeText(context, "Location Not Get", Toast.LENGTH_SHORT).show();
                    finish();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(context, "Location Not Get", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean isLocationEnabled() {
        final LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        //            buildAlertMessageNoGps();
        return manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public void enableLocationPermission() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        // Check the location settings of the user and create the callback to react to the different possibilities
        LocationSettingsRequest.Builder locationSettingsRequestBuilder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        SettingsClient client = LocationServices.getSettingsClient(context);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(locationSettingsRequestBuilder.build());
        task.addOnSuccessListener((Activity) context, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                // All location settings are satisfied. The client can initialize
                // location requests here.
                // ...
            }
        });
        final int REQUEST_CHECK_SETTINGS = 0x1;
        task.addOnFailureListener((Activity) context, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    // Location settings are not satisfied, but this can be fixed
                    // by showing the user a dialog.
                    try {
                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult().
                        ResolvableApiException resolvable = (ResolvableApiException) e;
                        resolvable.startResolutionForResult((Activity) context,
                                REQUEST_CHECK_SETTINGS);
                    } catch (IntentSender.SendIntentException sendEx) {
                        // Ignore the error.
                    }
                }
            }
        });
    }

    @Override
    public void getDetails(ActorsModel actorsModel) {
        Intent intent = new Intent(context, ActorDetailsActivity.class);
        intent.putExtra("Details", actorsModel);
        startActivity(intent);
    }
}