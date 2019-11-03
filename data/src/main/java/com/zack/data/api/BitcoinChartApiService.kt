package com.zack.data.api

import com.zack.data.model.ChartData
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface BitcoinChartApiService {
    @GET("/charts/market-price")
    fun fetchBitCoinChartData(@Query("timespan") timeSpan:String, @Query("rollingAverage")rollinAverage:String): Single<ChartData>
}