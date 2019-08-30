package id.agis.pkbl.ui.map

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.RelativeLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import id.agis.pkbl.R
import kotlinx.android.synthetic.main.content_map.*
import org.jetbrains.anko.toast


class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    companion object {
        const val DEFAULT_ZOOM = 18
    }

    private lateinit var map: GoogleMap
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var placesClient: PlacesClient

    private var lastKnowLocation: Location? = null
    private lateinit var locationCallback: LocationCallback

    private var mapView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        checkPermission()

    }

    fun initMap() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        mapView = mapFragment.view

        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(this@MapActivity)
        Places.initialize(this@MapActivity, "AIzaSyDUlqTD5YChwIzuPm0fZRGB-Y0vxZwVXx0")
        placesClient = Places.createClient(this)

        btn_find.setOnClickListener {
            val currentMarkerLocation = map.cameraPosition.target
            val intentResult = Intent()
            intentResult.putExtra("result", currentMarkerLocation.toString())
            setResult(Activity.RESULT_OK, intentResult)
            finish()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map.isMyLocationEnabled = true
        map.uiSettings.isMyLocationButtonEnabled = true

        if (mapView != null && mapView?.findViewById<View>("1".toInt()) != null) {
            val locationButton =
                (mapView!!.findViewById<View>(Integer.parseInt("1")).parent as View).findViewById<View>(
                    Integer.parseInt("2")
                )
            val layoutParams = locationButton.layoutParams as RelativeLayout.LayoutParams
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0)
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE)
            layoutParams.setMargins(0, 0, 40, 180)
        }

        //check if gps enabled or not and then request user to enable it
        val locationRequest = LocationRequest.create()
        locationRequest.interval = 10000
        locationRequest.fastestInterval = 5000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)

        val settingsClient = LocationServices.getSettingsClient(this@MapActivity)
        val task = settingsClient.checkLocationSettings(builder.build())

        task.addOnSuccessListener {
            getDeviceLocation()
        }
        task.addOnFailureListener(this@MapActivity) { e ->
            if (e is ResolvableApiException) {
                e.startResolutionForResult(this@MapActivity, 51)
            }
        }

    }

    private fun checkPermission() {
        Dexter.withActivity(this@MapActivity)
            .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                    initMap()
                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest?,
                    token: PermissionToken?
                ) {
                    token?.continuePermissionRequest()
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                    if (response!!.isPermanentlyDenied) {
                        val builder = AlertDialog.Builder(this@MapActivity)
                        builder.setTitle("Permission Denied")
                            .setMessage("Permission to access device location is permanently denied you need to go to the setting to allow the permission")
                            .setNegativeButton("Cancel", null)
                            .setPositiveButton("Ok") { _, _ ->
                                val intent = Intent()
                                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                                intent.data = Uri.fromParts("package", packageName, null)
                            }
                    } else {
                        toast("Permission Denied")
                        finish()
                    }
                }
            }).check()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 51) {
            getDeviceLocation()
        }
    }

    private fun getDeviceLocation() {
        fusedLocationProviderClient.lastLocation
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    lastKnowLocation = it.result
                    if (lastKnowLocation != null) {
                        moveCamera(lastKnowLocation!!.latitude, lastKnowLocation!!.longitude)
                    } else {
                        val locationRequest = LocationRequest.create()
                        locationRequest.interval = 10000
                        locationRequest.fastestInterval = 5000
                        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                        locationCallback = object : LocationCallback() {
                            override fun onLocationResult(locationResult: LocationResult?) {
                                super.onLocationResult(locationResult)
                                if (locationRequest == null) {
                                    return
                                }
                                lastKnowLocation = locationResult?.lastLocation

                                moveCamera(
                                    lastKnowLocation!!.latitude,
                                    lastKnowLocation!!.longitude
                                )

                                fusedLocationProviderClient.removeLocationUpdates(locationCallback)
                            }
                        }
                        fusedLocationProviderClient.requestLocationUpdates(
                            locationRequest,
                            locationCallback,
                            null
                        )
                    }
                } else {
                    toast("unable to get last location")
                }
            }
    }


    fun moveCamera(latitude: Double, longitude: Double) {
        val latLng = LatLng(latitude, longitude)
        val cameraUpdateFactory = CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_ZOOM.toFloat())
        map.moveCamera(cameraUpdateFactory)
    }
}

