package com.zack.bitcoinchart.viewmodel

import com.zack.data.model.ChartData
import com.zack.data.model.Value
import com.zack.domain.usecases.FetchBitcoinChartDataUsecase
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class BitcoinChartViewModelTest {
    private val fetchBitcoinChartDataUsecase = mock(FetchBitcoinChartDataUsecase::class.java)
    private lateinit var viewModel: BitcoinChartViewModel
    private lateinit var chartData:ChartData

    @Before
    fun setUp() {
        viewModel = BitcoinChartViewModel(fetchBitcoinChartDataUsecase)
        val values = ArrayList<Value>()
        chartData = ChartData("ok","Market Price (USD)","USD","day",
            "Average USD market price across major bitcoin exchanges.", values)

    }

    @Test
    fun testFetchingOfChartData() {

        Mockito.`when`(fetchBitcoinChartDataUsecase.execute())
            .thenReturn(Single.just(chartData))
        viewModel.fetchChartData("1year","8hours")
        verify(fetchBitcoinChartDataUsecase).execute()
    }
}