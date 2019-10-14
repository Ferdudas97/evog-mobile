package org.agh.pracinz.evog.view.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.agh.pracinz.evog.R
import org.agh.pracinz.evog.view.events.list.EventListActivity
import org.agh.pracinz.evog.view.notification.NotificationListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewPager.adapter = TabAdapter(supportFragmentManager).apply {
            addTab(EventListActivity(),"Events")
            addTab(NotificationListFragment(),"notifications")
        }
        mainTabLayout.setupWithViewPager(mainViewPager)
    }
}
