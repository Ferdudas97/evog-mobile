package org.agh.pracinz.evog.view.events.list.details

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.util.toRange
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_event_details.*
import org.agh.pracinz.evog.R
import org.agh.pracinz.evog.di.manual.ViewModels
import org.agh.pracinz.evog.model.data.Event
import org.agh.pracinz.evog.view.common.EVENT_ID
import org.agh.pracinz.evog.view.common.RxActivity
import org.agh.pracinz.evog.view.createToast
import org.agh.pracinz.evog.view.toPrintable
import kotlin.random.Random

class EventDetailsActivity : RxActivity(), OnMapReadyCallback {

    private val viewModel = ViewModels.eventDetailsViewModel
    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_details)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.eventlocationDetailsMap) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)

    }

    private fun setUi(event: Event) {
        eventNameDetailsTV.text = event.name
        event.details.apply {
            descriptionDetailTV.text = description
            categoryDetailsTV.text = category.toString()
            startTimeDetailsTv.text = "Starts at ${startDate.toPrintable()}"
            enddTimeDetailsTV.text = "Ends at ${endTime.toPrintable()}"
            setLocation(event)
        }
        assignToEventButton.setOnClickListener(this::assign)
    }

    private fun assign(view: View) {
        val id = this.intent.extras!![EVENT_ID] as String
        this.disposables.add(viewModel.assign(id)
            .doOnError { this.createToast("Cannot assing to event") }
            .doOnSuccess { this.createToast("Request was send") }
            .subscribe()
        )
    }

    private fun setLocation(event: Event) {
        val latLng =
            event.details.localization.run { LatLng(latitude.fuzzify(), longitude.fuzzify()) }
        CircleOptions().apply {
            center(latLng)
            radius(1000.toDouble())
            strokeColor(Color.BLACK)
            fillColor(0x220000FF)
            strokeWidth(2f)
            mMap.addCircle(this)
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, "10".toFloat()))
        }
    }

    override fun onMapReady(map: GoogleMap?) {
        map?.let {
            mMap = it
            getEvent()
        }
    }

    private fun getEvent() {
        intent.extras?.get(EVENT_ID)?.let {
            it as String
            viewModel.getEvent(id = it)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setUi)
        }
    }

    private fun Double.fuzzify() = this + Random.nextDouble(-fuzz, fuzz)
}

private const val fuzz = 0.002
