package com.zack.data.repository

import com.zack.data.api.BitcoinChartApiService
import com.zack.data.model.ChartData
import com.zack.data.model.Value
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class BitcoinChartRepositoryImplTest {

    private val apiService = mock(BitcoinChartApiService::class.java)
    private lateinit var bitcoinChartRepositoryImpl: BitcoinChartRepositoryImpl
    private lateinit var chartData: ChartData

    @Before
    fun setUp() {
        bitcoinChartRepositoryImpl = BitcoinChartRepositoryImpl(apiService)
        val values = ArrayList<Value>()
        chartData = ChartData(
            "ok", "Market Price (USD)", "USD", "day",
            "Average USD market price across major bitcoin exchanges.", values
        )

    }

    @Test
    fun testFetchingOfDataFromApi() {
        Mockito.`when`(apiService.fetchBitCoinChartData(ArgumentMatchers.anyString(), ArgumentMatchers.anyString()))
            .thenReturn(Single.just(chartData))
        bitcoinChartRepositoryImpl.fetchChartData("1year", "8hours")
        verify(apiService).fetchBitCoinChartData(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())
    }
}