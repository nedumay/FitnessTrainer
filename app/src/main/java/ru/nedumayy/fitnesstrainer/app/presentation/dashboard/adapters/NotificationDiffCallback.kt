package ru.nedumayy.fitnesstrainer.app.presentation.dashboard.adapters

import androidx.recyclerview.widget.DiffUtil
import ru.nedumayy.fitnesstrainer.app.domain.model.notification.NotificationDashboard

object NotificationDiffCallback : DiffUtil.ItemCallback<NotificationDashboard>() {
    override fun areItemsTheSame(oldItem: NotificationDashboard, newItem: NotificationDashboard): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: NotificationDashboard, newItem: NotificationDashboard): Boolean {
        return oldItem == newItem
    }
}