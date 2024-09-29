package com.example.finalkotlinapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class SalesRepLocationActivity : AppCompatActivity(), OnMapReadyCallback {
    private var myMap: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_curentdilivery)

        val mapFragment = checkNotNull(
            supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment?
        )
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        myMap = googleMap
        val galle = LatLng(6.0329, 80.2168)
        myMap!!.addMarker(MarkerOptions().position(galle).title("Galle"))
        myMap!!.moveCamera(CameraUpdateFactory.newLatLng(galle))
    }
}
