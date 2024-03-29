package com.zack.bitcoinchart.ui

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToLong

class XValuesFormatter : ValueFormatter() {

    override fun getAxisLabel(value: Float, axis: AxisBase?): String {
        val date = Date(value.roundToLong() * 1000)
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
        return sdf.format(date)
    }

}