package org.agh.pracinz.evog.view.events.list.details

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_event_details.*
import org.agh.pracinz.evog.R
import org.agh.pracinz.evog.di.manual.ViewModels
import org.agh.pracinz.evog.model.data.Event
import org.agh.pracinz.evog.model.data.Message
import org.agh.pracinz.evog.view.common.EVENT_ID
import org.agh.pracinz.evog.view.common.RxActivity
import org.agh.pracinz.evog.view.createToast
import org.agh.pracinz.evog.view.events.list.details.disscusion.MessageAdapter
import org.agh.pracinz.evog.view.events.list.details.disscusion.MessageItemListener
import org.agh.pracinz.evog.view.toPrintable
import org.agh.pracinz.evog.view.user.UserDetailsActivity
import kotlin.random.Random

class EventDetailsActivity : RxActivity(), OnMapReadyCallback, MessageItemListener {

    private val viewModel = ViewModels.eventDetailsViewModel
    private lateinit var mMap: GoogleMap
    lateinit var messageAdapter: MessageAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_details)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.eventlocationDetailsMap) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun setUi(event: Event) {
        viewModel.getIcon(event.imageName)
            .into(eventDetailsIcon)
        eventNameDetailsTV.text = event.name
        event.details.apply {
            descriptionDetailTV.text = description
            categoryDetailsTV.text = category.toString()
            startTimeDetailsTv.text = "Starts at ${startDate.toPrintable()}"
            enddTimeDetailsTV.text = "Ends at ${endTime.toPrintable()}"
            setLocation(event)
        }
        guestsButton.setOnClickListener { this.onGuestButtonClick(event) }
        when (event.assigned) {
            true -> assignToEventButton.visibility = View.GONE
            else -> assignToEventButton.visibility = View.VISIBLE
        }

        sendMessageButton.setOnClickListener { sendMessage() }
        setMessageList(event.discussion.messages)
        assignToEventButton.setOnClickListener(this::assign)
    }

    private fun sendMessage() {
        viewModel.sendMessage(messageInput.text.toString())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess { getEvent() }
            .subscribe()
    }


    private fun setMessageList(messages: List<Message>) {
        messageAdapter = MessageAdapter(this, messages)
        discussionRV.apply {
            adapter = messageAdapter
            layoutManager = LinearLayoutManager(this@EventDetailsActivity)
        }
    }

    private fun assign(view: View) {
        val id = this.intent.extras!![EVENT_ID] as String
        this.disposables.add(viewModel.assign(id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy({ this.createToast("Cannot assing to event") }) {
                this.createToast("Request was send")
                finish()
            }
        )
    }

    private fun onGuestButtonClick(event: Event) {
        GuestsListDialog((event.guest + event.organizers).toMutableSet(), viewModel, this).apply {
            create()
            show()
        }
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

    override fun onClick(message: Message) {
        UserDetailsActivity.open(this, message.creator.id)
    }

    override fun loadPhoto(imageView: ImageView, photoId: String?) {
        photoId?.let {
            viewModel.loadGuestPhoto(imageView, photoId)
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
