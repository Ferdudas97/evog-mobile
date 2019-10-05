package org.agh.pracinz.evog.view.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxbinding3.recyclerview.dataChanges
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_notification_list.*

import org.agh.pracinz.evog.R
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        linearLayoutManager = LinearLayoutManager(activity)
        adapter = NotificationAdapter(this@NotificationListFragment, notifications)
        notificationsRV.apply {
            layoutManager = layoutManager
            adapter = this@NotificationListFragment.adapter
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notification_list, container, false)
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

}
