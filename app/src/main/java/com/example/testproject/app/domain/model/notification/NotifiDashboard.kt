package com.example.testproject.app.domain.model.notification

data class NotifiDashboard(
    var id: Int,
    var time: String,
    var countDay: String,
    var tag: String,
    var Monday: Boolean,
    var Tuesday: Boolean,
    var Wednesday: Boolean,
    var Thursday: Boolean,
    var Friday: Boolean,
    var Saturday: Boolean,
    var Sunday: Boolean

)
