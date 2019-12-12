package org.agh.pracinz.evog.tasks

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast


class InternetReciever : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent!!.extras != null) {

            if (intent.action == Intent.ACTION_POWER_CONNECTED) {
                Toast.makeText(context, "Podłączono ładowarkę", Toast.LENGTH_SHORT).show()
            }
            if (intent.action == Intent.ACTION_POWER_DISCONNECTED) {
                Toast.makeText(context, "Odłączono ładowarkę", Toast.LENGTH_SHORT).show()
            }

        }
    }
}