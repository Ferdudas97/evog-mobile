package org.agh.pracinz.evog.view.events


import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import org.agh.pracinz.evog.R
import org.agh.pracinz.evog.di.manual.ViewModels
import org.agh.pracinz.evog.model.data.Localization
import org.agh.pracinz.evog.viewmodel.login.CreateEventViewModel


class LocationPickerFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerDragListener,
    GoogleMap.OnMapLongClickListener {
    private lateinit var mMap: GoogleMap
    private var mLocationPermissionGranted = false
    private val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1
    lateinit var fusedLocationClient: FusedLocationProviderClient
    lateinit var viewModel: CreateEventViewModel
    private val markers = mutableListOf<Marker>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_location_picker, container, false)
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?
        viewModel = ViewModels.createEventViewModel

        mapFragment!!.getMapAsync(this)
        return view
    }


    override fun onMapReady(map: GoogleMap?) {
        val latLong = viewModel.state.localization.run { LatLng(latitude, longitude) }
        map?.let {
            mMap = it
            getLocationPermission()
            mMap.setOnMarkerDragListener(this)
            mMap.setOnMapLongClickListener(this)
            setLocalization()

        }
    }

    override fun onMapLongClick(latLng: LatLng?) {
        latLng?.let {
            viewModel.state.localization = it.toLocalization()
            createMarker(latLng)
            Toast.makeText(activity!!.applicationContext, "Picked new location", Toast.LENGTH_SHORT).show()

        }
    }

    override fun onMarkerDragEnd(marker: Marker?) {
        Toast.makeText(activity!!.applicationContext, "Picked new location", Toast.LENGTH_SHORT).show()
        marker?.let {
            it.position.run {
                viewModel.state.localization = Localization(latitude = latitude, longitude = longitude)
            }
        }
    }

    override fun onMarkerDragStart(marker: Marker?) {
        Toast.makeText(activity!!.applicationContext, "Drag to new location", Toast.LENGTH_SHORT).show()
    }

    override fun onMarkerDrag(marker: Marker?) {
    }

    private fun getLocationPermission() {
        /*
     * Request location permission, so that we can get the location of the
     * device. The result of the permission request is handled by a callback,
     * onRequestPermissionsResult.
     */
        if (ContextCompat.checkSelfPermission(
                context!!,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mLocationPermissionGranted = true
        } else {
            requestPermissions(
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
            )
        }
    }

    @SuppressLint("MissingPermission")
    private fun setLocalization() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity!!)
        fusedLocationClient.lastLocation.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val location = task.result!!
                viewModel.state.localization =
                    Localization(latitude = location.latitude, longitude = location.longitude)
                updateLocationUI()


            } else {
                Log.d("TAG", "Current location is null. Using defaults.");
                Log.e("TAG", "Exception: %s", task.exception);
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        mLocationPermissionGranted = false
        when (requestCode) {
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionGranted = true
            }
        }
        updateLocationUI()
    }


    @SuppressLint("MissingPermission")
    private fun updateLocationUI() {
        try {
            if (mLocationPermissionGranted) {
                mMap.isMyLocationEnabled = true;
                mMap.uiSettings.isMyLocationButtonEnabled = true
                val latLng = viewModel.state.localization.run { LatLng(latitude, longitude) }
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, "15".toFloat()))
                createMarker(latLng)
            } else {
                mMap.isMyLocationEnabled = false
                mMap.uiSettings.isMyLocationButtonEnabled = false
                getLocationPermission()
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message)
        }
    }

    private fun LatLng.toLocalization() = Localization(latitude = latitude, longitude = longitude)

    private fun createMarker(latLng: LatLng) {
        removeMarkers()
        markers.add(mMap.addMarker(MarkerOptions().position(latLng).draggable(true)))
    }

    private fun removeMarkers() {
        markers.forEach(Marker::remove)
        markers.clear()
    }

}
