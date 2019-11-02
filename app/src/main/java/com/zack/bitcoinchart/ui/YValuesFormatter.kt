package com.zack.bitcoinchart.ui

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.ValueFormatter


class YValuesFormatter : ValueFormatter() {
    private val amount = arrayOf(3000, 4000, 5000, 6000, 7000, 8000, 9000, 10000, 11000, 12000)

    override fun getAxisLabel(value: Float, axis: AxisBase?): String {
        return amount.getOrNull(value.toInt()).toString()
    }
}