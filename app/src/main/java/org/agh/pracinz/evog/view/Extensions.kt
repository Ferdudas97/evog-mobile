package org.agh.pracinz.evog.view

import android.app.Activity
import android.content.Context
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
fun LocalDateTime.toPrintable() = "$year/${monthValue + 1}/$dayOfMonth $hour:$minute"

fun Activity.createToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

private fun String.parseToInt() = try {
    this.toInt()
} catch (_: Exception) {
    null
}

fun Context.getIcons() = fileList()
fun Context.getIcon(fileName: String) =
    resources.getIdentifier(fileName.substringBefore("."), "drawable", packageName)