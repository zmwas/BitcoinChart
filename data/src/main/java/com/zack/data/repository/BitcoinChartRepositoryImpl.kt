package com.zack.data.repository

import com.zack.data.api.BitcoinChartApiService
import com.zack.data.model.ChartData
import io.reactivex.Single
import javax.inject.Inject

class BitcoinChartRepositoryImpl @Inject constructor(private val apiService: BitcoinChartApiService) :BitCoinChartRepository{
    override fun fetchChartData(): Single<ChartData> {
        return apiService.fetchBitCoinChartData()
    }
}