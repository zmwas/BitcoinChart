package com.zack.data.repository

import com.zack.data.api.BitcoinChartApiService
import com.zack.data.model.ChartData
import io.reactivex.Single

class BitcoinChartRepository constructor(private val apiService: BitcoinChartApiService) {
    fun fetchChartData(): Single<ChartData> {
        return apiService.fetchBitCoinChartData()
    }
}