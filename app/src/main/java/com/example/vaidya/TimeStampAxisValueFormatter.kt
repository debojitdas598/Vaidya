package com.example.vaidya

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TimestampAxisValueFormatter : ValueFormatter() {
    override fun getAxisLabel(value: Float, axis: AxisBase?): String {
        return SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(value.toLong()))
    }
}