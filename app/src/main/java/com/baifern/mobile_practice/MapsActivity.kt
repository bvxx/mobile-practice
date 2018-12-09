package com.baifern.mobile_practice

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private var userLat: Double = 0.00
    private var userLng: Double = 0.00

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        // obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // get username to show at title
        val bundle = intent.extras
        if (bundle != null){
            var email = bundle.getString("Email")
            var lat = bundle.getDouble("Lat")
            var lng = bundle.getDouble("Lng")
            title = email + "'s Location"
            userLat = lat
            userLng = lng
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val position = LatLng(userLat, userLng)
        mMap.addMarker(MarkerOptions().position(position).title("Marker"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(position))
        //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position,6F))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item!!.getItemId()
        if(id == android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}
