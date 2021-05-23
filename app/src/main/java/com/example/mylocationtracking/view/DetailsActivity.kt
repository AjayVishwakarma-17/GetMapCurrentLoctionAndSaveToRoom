package com.example.mylocationtracking.view

import android.location.Location
import android.location.LocationListener
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mylocationtracking.model.Info
import com.example.mylocationtracking.R
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity(),OnMapReadyCallback {


    var data: Info? = null
    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        data = intent.getSerializableExtra("currentInfo") as Info

        tv_start_time.text = getString(R.string.start_time)+ data!!.startTimeStamp
        tv_stop_time.text  = getString(R.string.stop_time)+ data!!.stopTimeStamp
        tv_latitude.text = getString(R.string.latitude)+ data!!.lat
        tv_longitude.text = getString(R.string.longitude)+ data!!.lon

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        googleMap.mapType = GoogleMap.MAP_TYPE_NORMAL
        googleMap.uiSettings.isZoomGesturesEnabled = true

        // Add a temp marker and move the camera
        val temp = LatLng(data!!.lat.toDouble(), data!!.lon.toDouble())
        mMap.addMarker(MarkerOptions().position(temp).title("Your current location is here.."))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(temp))
        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(temp, 10f)
        mMap.animateCamera(cameraUpdate)
    }
}