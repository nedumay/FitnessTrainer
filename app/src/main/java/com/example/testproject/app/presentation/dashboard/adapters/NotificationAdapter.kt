package com.example.testproject.app.presentation.dashboard.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.testproject.app.domain.model.notification.NotificationDashboard
import com.example.testproject.databinding.ListItemDashboardBinding

/**
 * @author Nedumayy (Samim)
 * Адаптер списка для панели уведомлений о работе
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

    @SuppressLint("DefaultLocale")
    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        val notification = getItem(position)
        holder.bind(notification)
        holder.itemView.setOnClickListener {
            onNotificationClickListener?.invoke(notification)
        }
    }
}