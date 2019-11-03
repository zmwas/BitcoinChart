package com.zack.data.repository

import com.zack.data.model.ChartData
import io.reactivex.Single

interface BitCoinChartRepository {
    fun fetchChartData(timeSpan: String, rollingAverage: String): Single<ChartData>
}