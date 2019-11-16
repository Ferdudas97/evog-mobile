package org.agh.pracinz.evog.view.events.list.details.disscusion

import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.event_meesage_item.view.*
import org.agh.pracinz.evog.R
import org.agh.pracinz.evog.model.data.Message
import org.agh.pracinz.evog.model.data.fullName
import org.agh.pracinz.evog.view.inflate
import org.agh.pracinz.evog.view.toPrintable


class MessageAdapter(
    private val listener: MessageItemListener,
    private val messages: List<Message>
) :
    RecyclerView.Adapter<MessageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val inflatedView = parent.inflate(R.layout.event_meesage_item, false)
        return MessageViewHolder(listener, inflatedView)
    }

    override fun getItemCount(): Int = messages.size

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        messages[position].apply {
            holder.bindView(this)
        }
    }
}

interface MessageItemListener {
    fun onClick(message: Message)
    fun loadPhoto(imageView: ImageView, photoId: String?)
}

class MessageViewHolder(private val listener: MessageItemListener, view: View) :
    RecyclerView.ViewHolder(view) {

    fun bindView(message: Message) {
        itemView.apply {
            messageContentTv.text = message.text
            messageCreatedAtTv.text = message.createdAt.toPrintable()
            messageSenderNameTV.text = message.creator.fullName()
            listener.loadPhoto(messageSenderIV, message.creator.fileId)
            setOnClickListener { listener.onClick(message) }
        }

    }
}
