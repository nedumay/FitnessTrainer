package com.example.testproject.app.presentation.dashboard.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.testproject.app.domain.model.notification.NotifiDashboard

object NotificationDiffCallback : DiffUtil.ItemCallback<NotifiDashboard>() {
    override fun areItemsTheSame(oldItem: NotifiDashboard, newItem: NotifiDashboard): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: NotifiDashboard, newItem: NotifiDashboard): Boolean {
        return oldItem == newItem
    }
}