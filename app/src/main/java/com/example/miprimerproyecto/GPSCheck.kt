package com.example.miprimerproyecto

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.miprimerproyecto.databinding.ActivityGpscheckBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class GPSCheck : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityGpscheckBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGpscheckBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        getlocalizacion()

        if(checkIfGPSIsEnabled()){ run {
            val alertDialog1 = AlertDialog.Builder(this)
                // Establecer ícono
                .setIcon(android.R.drawable.ic_dialog_alert)
                // Establecer título
                .setTitle("Mensaje")
                // Establecer mensaje
                .setMessage("Su dispositivo cuenta con GPS")
                // Establecer botón positivo
                .setPositiveButton("Ok") { dialogInterface, _ ->
                    // Lo que sucederá cuando se haga clic en el botón positivo
                    //finish()
                }.show()
        }}else{ run {
            val alertDialog2 = AlertDialog.Builder(this)
                // Establecer ícono
                .setIcon(android.R.drawable.ic_dialog_alert)
                // Establecer título
                .setTitle("Mensaje")
                // Establecer mensaje
                .setMessage("Su dispositivo NO cuenta con GPS")
                // Establecer botón positivo
                .setPositiveButton("Ok") { dialogInterface, _ ->
                    finish()
                }.show()
        }
        }
    }

    fun checkIfGPSIsEnabled(): Boolean {

        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

    }


    fun getlocalizacion() {
        val permiso =
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
        if (permiso == PackageManager.PERMISSION_DENIED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    1
                )
            }
        }
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        mMap.isMyLocationEnabled = true
        mMap.uiSettings.isMyLocationButtonEnabled = false
        val locationManager =
            this@GPSCheck.getSystemService(LOCATION_SERVICE) as LocationManager
        val locationListener: LocationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                val miUbicacion = LatLng(location.latitude, location.longitude)
                mMap.addMarker(MarkerOptions().position(miUbicacion).title("ubicacion actual"))
                mMap.moveCamera(CameraUpdateFactory.newLatLng(miUbicacion))
                val cameraPosition = CameraPosition.Builder()
                    .target(miUbicacion)
                    .zoom(14f)
                    .bearing(90f)
                    .tilt(45f)
                    .build()
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
            }

            override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
            override fun onProviderEnabled(provider: String) {}
            override fun onProviderDisabled(provider: String) {}
        }
        locationManager.requestLocationUpdates(
            LocationManager.NETWORK_PROVIDER,
            0,
            0f,
            locationListener
        )
    }

}