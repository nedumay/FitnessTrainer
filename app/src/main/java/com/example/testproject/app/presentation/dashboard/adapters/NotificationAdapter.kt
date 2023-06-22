package com.example.testproject.app.presentation.dashboard.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.testproject.app.domain.model.notification.NotificationDashboard
import com.example.testproject.databinding.ListItemDashboardBinding
import java.util.UUID

/**
 * @author Nedumayy (Samim)
 * List adapter for work notification dashboard
 */
class NotificationAdapter :
    ListAdapter<NotificationDashboard, NotificationViewHolder>(NotificationDiffCallback) {

    var onNotificationClickListener: ((NotificationDashboard) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val binding = ListItemDashboardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return NotificationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        val notification = getItem(position)
        with(holder.binding) {
            with(notification) {
                textViewCountDay.text = countDay
                textViewTime.text = time
                if (Monday != DEFAULT_NOTIFICATION_ID) {
                    chipMo.isChecked = true
                }
                if (Tuesday != DEFAULT_NOTIFICATION_ID) {
                    chipTu.isChecked = true
                }
                if (Wednesday != DEFAULT_NOTIFICATION_ID) {
                    chipWc.isChecked = true
                }
                if (Thursday != DEFAULT_NOTIFICATION_ID) {
                    chipTh.isChecked = true
                }
                if (Friday != DEFAULT_NOTIFICATION_ID) {
                    chipFr.isChecked = true
                }
                if (Saturday != DEFAULT_NOTIFICATION_ID) {
                    chipSa.isChecked = true
                }
                if (Sunday != DEFAULT_NOTIFICATION_ID) {
                    chipSu.isChecked = true
                }
            }
            root.setOnClickListener {
                onNotificationClickListener?.invoke(notification)
            }
        }
    }

    companion object {

        private const val DEFAULT_NOTIFICATION_ID = 0
    }

}