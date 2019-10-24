package org.agh.pracinz.evog.view.events

import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import androidx.annotation.RequiresApi
import org.agh.pracinz.evog.viewmodel.login.CreateEventViewModel


interface OnIconClickedListener {

    fun onIconClicked(name: String)
}

class IconAdapter(
    private val icons: List<String>,
    private val listener: OnIconClickedListener,
    private val viewModel: CreateEventViewModel
) :
    BaseAdapter() {
    override fun getCount(): Int {
        return icons.size
    }

    override fun getItem(position: Int): Any {
        return icons[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val imageView = ImageView(parent!!.context)
        viewModel.getIcon(icons[position])
            .into(imageView)
        imageView.setOnClickListener {
            listener.onIconClicked(icons[position])
        }
        return imageView
    }
}