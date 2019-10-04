package org.agh.pracinz.evog.view.events.list

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_event_list.*
import org.agh.pracinz.evog.R
import org.agh.pracinz.evog.di.manual.ViewModels
import org.agh.pracinz.evog.model.data.EventFilter
import org.agh.pracinz.evog.model.data.EventSnapshot
import org.agh.pracinz.evog.model.data.Localization
import org.agh.pracinz.evog.view.common.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
import org.agh.pracinz.evog.view.common.RxActivity
import org.agh.pracinz.evog.view.events.CreateEventActivity
import org.agh.pracinz.evog.view.events.list.adapter.EventListAdapter

class EventListActivity : RxActivity() {

    private val viewModel = ViewModels.eventListViewModel
    private var events = listOf<EventSnapshot>()
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var mLocationPermissionGranted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_list)
        addEventButton.setOnClickListener(this::onAddEventButtonClick)
        linearLayoutManager = LinearLayoutManager(this)
        getLocationPermission()
        eventsRV.apply {
            adapter = EventListAdapter(events)
            layoutManager = linearLayoutManager
        }
        searchEventButton.setOnClickListener(this::onSearchButtonClicked)
    }

    private fun setEvents(events: List<EventSnapshot>) {
        eventsRV.apply {
            adapter = EventListAdapter(events)
            layoutManager = linearLayoutManager
        }
    }

    private fun onError(e: Throwable) {
        Log.e("EventListActivity", e.message)
        Toast.makeText(this, "Change filter ", Toast.LENGTH_SHORT).show()
    }


    private fun onAddEventButtonClick(view: View) {
        val intent = Intent(this, CreateEventActivity::class.java)
        startActivity(intent)
    }


    private fun getLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mLocationPermissionGranted = true
            setLocalization()
        } else {
            requestPermissions(
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
            )
        }
    }

    @SuppressLint("MissingPermission")
    private fun setLocalization() {
        if (mLocationPermissionGranted) {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
            fusedLocationClient.lastLocation.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val location = task.result!!
                    viewModel.state.localization =
                        Localization(latitude = location.latitude, longitude = location.longitude)
                    showEvents()
                } else {
                    Log.d("TAG", "Current location is null. Using defaults.");
                    Log.e("TAG", "Exception: %s", task.exception);
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        mLocationPermissionGranted = false
        when (requestCode) {
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionGranted = true
                setLocalization()
            }
        }
    }

    private fun showEvents() {
        disposables.add(
            viewModel.getEventsFiltered()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setEvents)
        )
    }

    private fun onSearchButtonClicked(view: View) {
        EventFilterDialog(viewModel, this).apply {
            create()
            show()
            setOnDismissListener {
                showEvents()
            }
        }
    }


}
