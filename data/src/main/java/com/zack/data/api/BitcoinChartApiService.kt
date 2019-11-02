package com.zack.data.api

import com.zack.data.model.ChartData
import io.reactivex.Single
import retrofit2.http.GET

interface BitcoinChartApiService {
    @GET("/charts/market-price")
    fun fetchBitCoinChartData(): Single<ChartData>
}