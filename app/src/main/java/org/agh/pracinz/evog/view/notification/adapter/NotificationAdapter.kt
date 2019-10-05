package org.agh.pracinz.evog.view.notification.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.notification_list_item.view.*
import org.agh.pracinz.evog.R
import org.agh.pracinz.evog.model.data.Notification
import org.agh.pracinz.evog.view.inflate
import org.agh.pracinz.evog.viewmodel.login.NotificationListViewModel


class NotificationAdapter(
    private val listener: OnNotificationItemClickListener,
    private val notifications: List<Notification>
) :
    RecyclerView.Adapter<NotificationViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {

        val inflatedView = parent.inflate(R.layout.notification_list_item, false)
        return NotificationViewHolder(listener, inflatedView)
    }

    override fun getItemCount(): Int = notifications.size

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        notifications[position].apply {
            holder.bindView(this)
        }
    }

}

class NotificationViewHolder(
    private val listener: OnNotificationItemClickListener,
    view: View
) : RecyclerView.ViewHolder(view) {


    fun bindView(notification: Notification) {
        notification.apply {
            itemView.senderNameTV.text = "${guest.firstName} ${guest.lastName}"
            itemView.notificationContentTV.text = content
            itemView.rejectRequestButton.setOnClickListener {
                listener.onRejectButtonClicked(notification.id, adapterPosition)
            }
            itemView.acceptRequestButton.setOnClickListener {
                listener.onAcceptButtonClicked(notification.id, adapterPosition)
            }

        }
    }
}

interface OnNotificationItemClickListener {

    fun onRejectButtonClicked(notificationId: String, position: Int)
    fun onAcceptButtonClicked(notificationId: String, position: Int)
}