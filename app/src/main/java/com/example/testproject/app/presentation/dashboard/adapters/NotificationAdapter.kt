package com.example.testproject.app.presentation.dashboard.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.testproject.app.domain.model.notification.NotifiDashboard
import com.example.testproject.databinding.ListItemDashboardBinding

/**
 * @author Nedumayy (Samim)
 * List adapter for work notification dashboard
 */
class NotificationAdapter : ListAdapter<NotifiDashboard, NotificationViewHolder>(NotificationDiffCallback) {

    var onNotificationClickListener: ((NotifiDashboard) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val binding = ListItemDashboardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return NotificationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        var notification = getItem(position)
        with(holder.binding) {
            with(notification) {
                textViewCountDay.text = countDay
                textViewTime.text = time
                chipMo.isChecked = Monday
                chipTu.isChecked = Tuesday
                chipWc.isChecked = Wednesday
                chipTh.isChecked = Thursday
                chipFr.isChecked = Friday
                chipSa.isChecked = Saturday
                chipSu.isChecked = Sunday
            }
            root.setOnClickListener {
                onNotificationClickListener?.invoke(notification)
            }
        }
    }
}