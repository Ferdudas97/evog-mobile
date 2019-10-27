package org.agh.pracinz.evog.view.events.list

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_event_list.view.*
import org.agh.pracinz.evog.R
import org.agh.pracinz.evog.di.manual.ViewModels
import org.agh.pracinz.evog.model.data.EventSnapshot
import org.agh.pracinz.evog.model.data.Localization
import org.agh.pracinz.evog.view.common.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
import org.agh.pracinz.evog.view.common.RxFragment
import org.agh.pracinz.evog.view.events.CreateEventActivity
import org.agh.pracinz.evog.view.events.list.adapter.EventListAdapter
import java.time.LocalDateTime

class EventListActivity : RxFragment() {

    private val viewModel = ViewModels.eventListViewModel
    private var events = mutableListOf<EventSnapshot>()
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var mLocationPermissionGranted = false
    private lateinit var eventAdapter: EventListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_event_list, container, false)
            .also {
                it.addEventButton.setOnClickListener(this::onAddEventButtonClick)
                linearLayoutManager = LinearLayoutManager(activity)
                getLocationPermission()
                this.eventAdapter = EventListAdapter(events, viewModel)
                it.eventsRV.apply {
                    adapter = eventAdapter
                    layoutManager = linearLayoutManager
                }
                it.searchEventButton.setOnClickListener(this::onSearchButtonClicked)
                it.currentEventsButton.setOnClickListener { onCurrentButtonClicked() }
                it.pastEventsButton.setOnClickListener { onPastButtonClicked() }
                it.presentEventsButton.setOnClickListener { onMyEventsButtonClicked() }

            }
    }

    private fun setEvents(events: List<EventSnapshot>) {
        this.events.clear()
        this.events.addAll(events)
        this.eventAdapter.notifyDataSetChanged()
    }

    private fun onError(e: Throwable) {
        Log.e("EventListActivity", e.message)
        Toast.makeText(activity, "Change filter ", Toast.LENGTH_SHORT).show()
    }

    private fun onMyEventsButtonClicked() {
        viewModel.state.apply {
            startTime = LocalDateTime.now()
            endTime = LocalDateTime.MAX
            isMy = true
        }
        showEvents()

    }
    private fun onPastButtonClicked() {
        viewModel.state.apply {
            endTime = LocalDateTime.now()
            startTime = LocalDateTime.MIN
            isMy = true
        }
        showEvents()
    }

    private fun onCurrentButtonClicked() {
        viewModel.state.apply {
            startTime = LocalDateTime.now()
            endTime = LocalDateTime.now().plusDays(7)
            isMy = false
        }
        showEvents()
    }
    private fun onAddEventButtonClick(view: View) {
        val intent = Intent(activity, CreateEventActivity::class.java)
        startActivity(intent)
    }


    private fun getLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                activity!!,
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
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity!!)
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
        EventFilterDialog(viewModel, activity!!).apply {
            setOnDismissListener {
                showEvents()
            }
            create()
            show()
        }
    }


}
