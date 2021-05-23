package com.example.mylocationtracking.view

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.mylocationtracking.model.Info
import com.example.mylocationtracking.viewmodel.InfoViewModel
import com.example.mylocationtracking.R
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_maps.*
import java.sql.Timestamp
import java.text.SimpleDateFormat


open class MapsActivity : AppCompatActivity(), OnMapReadyCallback, LocationListener,GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener{

    private val TAG = "MapsActivity"
    private lateinit var infoViewModel: InfoViewModel
    private lateinit var mMap: GoogleMap
    private lateinit var mGoogleApiClient: GoogleApiClient
    private lateinit var locationManager: LocationManager
    private val locationPermissionCode = 2
    val sdf = SimpleDateFormat("yyyy.MM.dd.HH.mm.ss")

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        infoViewModel = ViewModelProvider.AndroidViewModelFactory
            .getInstance(application).create(InfoViewModel::class.java)

        btnStartTracking.setOnClickListener {
                getLocation()
        }

        btnStopTracking.setOnClickListener {
            // insert stop time stamp
            val info = Info("","","",getTimeStamp())
            infoViewModel.insert(info)
            // stopping location updates
            locationManager.removeUpdates(this)
            Toast.makeText(this,"Location updates are removed!!",Toast.LENGTH_LONG).show()
        }

        fabLog.setOnClickListener{
            val intent = Intent(this, InfoLogsActivity::class.java)
            startActivity(intent)
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        googleMap.mapType = GoogleMap.MAP_TYPE_NORMAL
        googleMap.uiSettings.isZoomGesturesEnabled = true
        // Add a temp marker and move the camera
        val temp = LatLng(28.58694291994979, 77.37549662575692)
        mMap.addMarker(MarkerOptions().position(temp).title("You are here"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(temp))
        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(temp, 10f)
        mMap.animateCamera(cameraUpdate)
    }

    private fun getLocation() {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), locationPermissionCode)
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 30000, 5f, this)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == locationPermissionCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onLocationChanged(location: Location) {
        val strLocation = "Latitude: " + location.latitude + " , Longitude: " + location.longitude
        Log.e("$TAG onLocationChanged - ",strLocation)
        // clear marker
        mMap.clear()
        // Add a marker in current and move the camera
        val currentLocation = LatLng(location.latitude, location.longitude)
        mMap.addMarker(MarkerOptions().position(currentLocation).title("Current marker"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation))
        // prepare data for insert
        val info = Info(getTimeStamp(),location.latitude.toString(),location.longitude.toString(),"")
        infoViewModel.insert(info)
        // enable the stop button
        btnStopTracking.isEnabled=true
        Toast.makeText(this,"Location changed and data inserted",Toast.LENGTH_LONG).show()
    }

    fun getTimeStamp(): String {
        val timestamp = Timestamp(System.currentTimeMillis())
        Log.e(TAG, sdf.format(timestamp))
        return sdf.format(timestamp)
    }
    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        Log.e(TAG, "onStatusChanged")
    }

    override fun onProviderEnabled(provider: String?) {
        Log.e(TAG,"onProviderEnabled")
    }

    override fun onProviderDisabled(provider: String?) {
        Log.e(TAG,"onProviderDisabled")
    }

    override fun onConnected(p0: Bundle?) {
        Log.e(TAG,"onConnected")
    }

    override fun onConnectionSuspended(p0: Int) {
        Log.e(TAG,"onConnectionSuspended")
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        Log.e(TAG,"onConnectionFailed")
    }
}