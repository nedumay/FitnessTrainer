package com.example.testproject.app.presentation.dashboard.adapters

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import com.example.testproject.app.domain.model.notification.NotificationDashboard
import com.example.testproject.databinding.ListItemDashboardBinding

class NotificationViewHolder(
    val binding: ListItemDashboardBinding
) : RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("DefaultLocale")
    fun bind(notification: NotificationDashboard) {
        with(binding) {
            with(notification) {
                textViewCountDay.text = countDay.toString()
                textCountWeek.text = "$countWeek"
                val timeFormat = String.format(
                    "%02d:%02d",
                    hour,
                    minute
                )
                textViewTime.text = timeFormat
                if (days[0] != DEFAULT_DAY_ADD && days[0] != DEFAULT_DAY_EDIT) {
                    chipSu.isChecked = true
                }
                if (days[1] != DEFAULT_DAY_ADD && days[1] != DEFAULT_DAY_EDIT) {
                    chipMo.isChecked = true
                }
                if (days[2] != DEFAULT_DAY_ADD && days[2] != DEFAULT_DAY_EDIT) {
                    chipTu.isChecked = true
                }
                if (days[3] != DEFAULT_DAY_ADD && days[3] != DEFAULT_DAY_EDIT) {
                    chipWc.isChecked = true
                }
                if (days[4] != DEFAULT_DAY_ADD && days[4] != DEFAULT_DAY_EDIT) {
                    chipTh.isChecked = true
                }
                if (days[5] != DEFAULT_DAY_ADD && days[5] != DEFAULT_DAY_EDIT) {
                    chipFr.isChecked = true
                }
                if (days[6] != DEFAULT_DAY_ADD && days[6] != DEFAULT_DAY_EDIT) {
                    chipSa.isChecked = true
                }
            }
        }
    }

    companion object {

        private const val DEFAULT_DAY_ADD = ""
        private const val DEFAULT_DAY_EDIT = ";"
    }
}