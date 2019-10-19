package org.agh.pracinz.evog.view.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_notification_list.view.*
import org.agh.pracinz.evog.R
import org.agh.pracinz.evog.di.manual.ViewModels
import org.agh.pracinz.evog.model.data.Notification
import org.agh.pracinz.evog.view.common.RxFragment
import org.agh.pracinz.evog.view.notification.adapter.NotificationAdapter
import org.agh.pracinz.evog.view.notification.adapter.OnNotificationItemClickListener
import org.agh.pracinz.evog.viewmodel.login.NotificationListViewModel

class NotificationListFragment : RxFragment(), OnNotificationItemClickListener {

    private lateinit var viewModel: NotificationListViewModel
    private lateinit var linearLayoutManager: LinearLayoutManager
    private var notifications = mutableListOf<Notification>()
    private lateinit var adapter: NotificationAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notification_list, container, false).also {
            viewModel = ViewModels.notificationListViewModel
            linearLayoutManager = LinearLayoutManager(activity)
            adapter = NotificationAdapter(this@NotificationListFragment, notifications)
            it.notificationsRV.apply {
                layoutManager = linearLayoutManager
                adapter = this@NotificationListFragment.adapter
            }
            viewModel.getAll().observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setNotifications)
        }
    }

    private fun setNotifications(list: List<Notification>) {
        notifications.clear()
        notifications.addAll(list)
        adapter.notifyDataSetChanged()
    }



    override fun onRejectButtonClicked(notificationId: String, position: Int) {
        viewModel.reject(notificationId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { _: Unit ->
                notifications.removeAt(position)
                adapter.notifyItemRemoved(position)
            }

            .apply { disposables.add(this) }
    }

    override fun onAcceptButtonClicked(notificationId: String, position: Int) {
        viewModel.accept(notificationId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { _: Unit ->
                notifications.removeAt(position)
                adapter.notifyItemRemoved(position)
            }
            .apply { disposables.add(this) }

    }

    override fun onDeleteButtonClicked(notificationId: String, position: Int) {
        viewModel.delete(notificationId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { _: Unit ->
                notifications.removeAt(position)
                adapter.notifyItemRemoved(position)
            }.apply { disposables.add(this) }
    }
}
