package org.agh.pracinz.evog.view.events

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_event_list.*
import org.agh.pracinz.evog.R

class EventListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_list)
        addEventButton.setOnClickListener(this::onAddEventButtonClick)
    }


    private fun onAddEventButtonClick(view: View) {
        val intent = Intent(this, CreateEventActivity::class.java)

        startActivity(intent)
    }
}
