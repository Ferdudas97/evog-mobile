package org.agh.pracinz.evog.view

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.core.widget.doAfterTextChanged
import java.time.LocalDateTime


fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

fun TextView.onTextChange(f: (String) -> Unit) = this.doAfterTextChanged {
    it?.toString()?.let { str -> f(str) }
}

fun TextView.onTextNumberChange(f: (Int?) -> Unit) = this.doAfterTextChanged {
    it?.toString()?.let { str -> if (str.isNotBlank()) f(str.parseToInt()) }
}

fun RadioGroup.onSelectedChange(f: (String) -> Unit) =
    this.setOnCheckedChangeListener { group, checkedId ->
        f(group.findViewById<RadioButton>(checkedId).text.toString().toUpperCase())

    }

fun Editable.toIntOrNull() = this.toString().parseToInt()
fun LocalDateTime.toPrintable() =
    "$year/${monthValue.zeroBefore()}/${dayOfMonth.zeroBefore()} ${hour.zeroBefore()}:${minute.zeroBefore()}"

private fun Number.zeroBefore() = if (this.toInt() < 10) "0$this" else this.toString()
fun Activity.createToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

private fun String.parseToInt() = try {
    this.toInt()
} catch (_: Exception) {
    null
}


fun doAsync(block: () -> Unit) {
    object : AsyncTask<Unit, Unit, Unit>() {
        override fun doInBackground(vararg params: Unit?) {
            block()
        }
    }.execute()
}

fun broadcastReceiver(block: (Intent, Context) -> Unit) = object : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        block(intent!!, context!!)
    }
}




fun Context.getIcons() = fileList()
fun Context.getIcon(fileName: String) =
    resources.getIdentifier(fileName.substringBefore("."), "drawable", packageName)