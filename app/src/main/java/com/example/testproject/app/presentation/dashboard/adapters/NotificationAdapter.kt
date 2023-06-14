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
                if (Monday != UUID.fromString(DEFAULT_UUID)) {
                    chipMo.isChecked = true
                }
                if (Tuesday != UUID.fromString(DEFAULT_UUID)) {
                    chipTu.isChecked = true
                }
                if (Wednesday != UUID.fromString(DEFAULT_UUID)) {
                    chipWc.isChecked = true
                }
                if (Thursday != UUID.fromString(DEFAULT_UUID)) {
                    chipTh.isChecked = true
                }
                if (Friday != UUID.fromString(DEFAULT_UUID)) {
                    chipFr.isChecked = true
                }
                if (Saturday != UUID.fromString(DEFAULT_UUID)) {
                    chipSa.isChecked = true
                }
                if (Sunday != UUID.fromString(DEFAULT_UUID)) {
                    chipSu.isChecked = true
                }
            }
            root.setOnClickListener {
                onNotificationClickListener?.invoke(notification)
            }
        }
    }

    companion object {

        private const val DEFAULT_UUID = "d3cfe541-7001-4d6c-aa8a-55f649867d1e"
    }

}