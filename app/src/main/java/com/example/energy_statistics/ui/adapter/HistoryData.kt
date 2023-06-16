package com.example.energy_statistics.ui.adapter

data class HistoryData(
    var electricUsed : Int?    = null,
    var waterUsed    : Int?    = null,
    var temperature  : Int?    = null,
    var dateReport   : String? = null
)
