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
                textViewCountDay.text = countDay.toString()
                val timeFormat = String.format(
                    "%02d:%02d",
                    hour,
                    minute
                )
                textViewTime.text = timeFormat
                if (days[0] != DEFAULT_NOTIFICATION_ID) {
                    chipSu.isChecked = true
                }
                if (days[1] != DEFAULT_NOTIFICATION_ID) {
                    chipMo.isChecked = true
                }
                if (days[2] != DEFAULT_NOTIFICATION_ID) {
                    chipTu.isChecked = true
                }
                if (days[3] != DEFAULT_NOTIFICATION_ID) {
                    chipWc.isChecked = true
                }
                if (days[4] != DEFAULT_NOTIFICATION_ID) {
                    chipTh.isChecked = true
                }
                if (days[5] != DEFAULT_NOTIFICATION_ID) {
                    chipFr.isChecked = true
                }
                if (days[6] != DEFAULT_NOTIFICATION_ID) {
                    chipSa.isChecked = true
                }
            }
            root.setOnClickListener {
                onNotificationClickListener?.invoke(notification)
            }
        }
    }

    companion object {

        private const val DEFAULT_NOTIFICATION_ID = ""
    }

}