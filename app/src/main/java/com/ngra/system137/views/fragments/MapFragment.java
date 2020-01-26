package com.ngra.system137.views.fragments;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cunoraz.gifview.library.GifView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.ngra.system137.R;
import com.ngra.system137.backgroung.BackgroundServiceLocation;
import com.ngra.system137.databinding.FragmentMapBinding;
import com.ngra.system137.viewmodels.fragments.VM_MapFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback,
        BackgroundServiceLocation.GetResult {

    private Context context;
    private VM_MapFragment vm_mapFragment;
    private View view;
    private GoogleMap mMap;
    public LocationManager locationManager;
    public MyLocationListener listener;
    private final int TWO_MINUTES = 1000 * 60 * 2;
    public Location previousBestLocation = null;
    public Location GPSLocation = null;
    private Location NetworkLocation = null;
    private boolean getLocation = false;
    private DisposableObserver<String> observer;

    @BindView(R.id.textChoose)
    TextView textChoose;

    @BindView(R.id.MarkerGif)
    GifView MarkerGif;

    @BindView(R.id.LayoutChoose)
    RelativeLayout LayoutChoose;

    public MapFragment() {//________________________________________________________________________ Start MapFragment

    }//_____________________________________________________________________________________________ End MapFragment


    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ Start onCreateView
        context = getContext();
        vm_mapFragment = new VM_MapFragment(context);
        FragmentMapBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_map, container, false
        );
        binding.setMap(vm_mapFragment);
        view = binding.getRoot();
        ButterKnife.bind(this, view);
        return view;
    }//_____________________________________________________________________________________________ End onCreateView


    @Override
    public void onStart() {//_______________________________________________________________________ Start onStart
        super.onStart();
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.fpraMap);
        mapFragment.getMapAsync(this);
        textChoose.setVisibility(View.GONE);
        MarkerGif.setVisibility(View.VISIBLE);
        SetOnClick();
        GetCurrentLocation();
        if(observer != null)
            observer.dispose();
        observer = null;
        ObserverObservable();
    }//_____________________________________________________________________________________________ End onStart


    private void GetCurrentLocation() {//___________________________________________________________ Start GetCurrentLocation
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

        } else {
            locationManager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
            listener = new MyLocationListener();
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 0, (LocationListener) listener);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, listener);
            getLocation = false;

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textChoose.setVisibility(View.VISIBLE);
                    MarkerGif.setVisibility(View.GONE);
                    locationManager.removeUpdates(listener);
                    getLocation = true;
                    if (GPSLocation != null) {
                        LatLng current = new LatLng(GPSLocation.getLatitude(), GPSLocation.getLongitude());
                        float zoom = (float) 16;
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current, zoom));
                    } else if (NetworkLocation != null) {
                        LatLng current = new LatLng(NetworkLocation.getLatitude(), NetworkLocation.getLongitude());
                        float zoom = (float) 16;
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current, zoom));
                    }
                }
            }, 15 * 1000);
        }
    }//_____________________________________________________________________________________________ End GetCurrentLocation


    private void ObserverObservable() {//___________________________________________________________ Start ObserverObservable

        observer = new DisposableObserver<String>() {
            @Override
            public void onNext(final String s) {
                getActivity()
                        .runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                textChoose.setVisibility(View.VISIBLE);
                                MarkerGif.setVisibility(View.GONE);
                                if(observer != null)
                                    observer.dispose();
                                observer = null;
                                getActivity().onBackPressed();
                            }
                        });
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };


        vm_mapFragment
                .getObservables()
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }//_____________________________________________________________________________________________ End ObserverObservable


    private void SetOnClick() {//___________________________________________________________________ Start SetOnClick

        LayoutChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textChoose.setVisibility(View.GONE);
                MarkerGif.setVisibility(View.VISIBLE);
                LatLng negra = mMap.getCameraPosition().target;
                vm_mapFragment.GetAddress(negra.latitude , negra.longitude);
//                vm_mapFragment.GetAddress(29.479993 , -139.565491);
//                LatLng negra = mMap.getCameraPosition().target;
//                Intent intent = new Intent(context, BackgroundServiceLocation.class);
//                BackgroundServiceLocation.getResult = MapFragment.this;
//                intent.putExtra("jobtype", "TextAddress");
//                intent.putExtra("lat", negra.latitude);
//                intent.putExtra("log", negra.longitude);
//                context.startService(intent);
            }
        });


    }//_____________________________________________________________________________________________ End SetOnClick


    @Override
    public void onMapReady(GoogleMap googleMap) {//_________________________________________________ Start Void onMapReady
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(false);


        mMap.setOnCameraMoveStartedListener(new GoogleMap.OnCameraMoveStartedListener() {
            @Override
            public void onCameraMoveStarted(int i) {
                textChoose.setVisibility(View.GONE);
                MarkerGif.setVisibility(View.VISIBLE);
            }
        });

        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                if (getLocation) {
                    textChoose.setVisibility(View.VISIBLE);
                    MarkerGif.setVisibility(View.GONE);
                }
            }
        });


        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                return false;
            }
        });

    }//_____________________________________________________________________________________________ End Void onMapReady


    @Override
    public void GetAddress() {//____________________________________________________________________ Start GetAddress
        getActivity().onBackPressed();
    }//_____________________________________________________________________________________________ End GetAddress


    public class MyLocationListener implements LocationListener {//_________________________________ Start MyLocationListener

        public void onLocationChanged(final Location loc) {

            if (isBetterLocation(loc, previousBestLocation)) {
                if (loc.getProvider().equalsIgnoreCase("gps"))
                    GPSLocation = loc;
                else
                    NetworkLocation = loc;
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        public void onProviderDisabled(String provider) {

        }


        public void onProviderEnabled(String provider) {

        }
    }//_____________________________________________________________________________________________ End MyLocationListener


    protected boolean isBetterLocation(Location location, Location currentBestLocation) {//_________ Start isBetterLocation
        if (currentBestLocation == null) {
            // A new location is always better than no location
            return true;
        }

        // Check whether the new location fix is newer or older
        long timeDelta = location.getTime() - currentBestLocation.getTime();
        boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
        boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
        boolean isNewer = timeDelta > 0;

        // If it's been more than two minutes since the current location, use the new location
        // because the user has likely moved
        if (isSignificantlyNewer) {
            return true;
            // If the new location is more than two minutes older, it must be worse
        } else if (isSignificantlyOlder) {
            return false;
        }

        // Check whether the new location fix is more or less accurate
        int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
        boolean isLessAccurate = accuracyDelta > 0;
        boolean isMoreAccurate = accuracyDelta < 0;
        boolean isSignificantlyLessAccurate = accuracyDelta > 200;

        // Check if the old and new location are from the same provider
        boolean isFromSameProvider = isSameProvider(location.getProvider(),
                currentBestLocation.getProvider());

        // Determine location quality using a combination of timeliness and accuracy
        if (isMoreAccurate) {
            return true;
        } else if (isNewer && !isLessAccurate) {
            return true;
        } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
            return true;
        }
        return false;
    }//_____________________________________________________________________________________________ End isBetterLocation


    private boolean isSameProvider(String provider1, String provider2) {//__________________________ Start isSameProvider
        if (provider1 == null) {
            return provider2 == null;
        }
        return provider1.equals(provider2);
    }//_____________________________________________________________________________________________ End isSameProvider


}
