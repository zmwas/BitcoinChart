package com.zack.bitcoinchart.viewmodel

import com.zack.data.model.ChartData
import com.zack.data.model.Value
import com.zack.domain.usecases.FetchBitcoinChartDataUsecase
import io.reactivex.Single
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.CountDownLatch

@RunWith(MockitoJUnitRunner::class)
class BitcoinChartViewModelTest {
    private val fetchBitcoinChartDataUsecase = mock(FetchBitcoinChartDataUsecase::class.java)
    private lateinit var viewModel: BitcoinChartViewModel
    private lateinit var chartData:ChartData

    @Before
    fun setUp() {
        viewModel = BitcoinChartViewModel(fetchBitcoinChartDataUsecase)
        val value1 = Value(10f,123243546f)
        val value2 = Value(10f,123243546f)

        val values = ArrayList<Value>()
        values.add(value1)
        values.add(value2)
        chartData = ChartData("ok","Market Price (USD)","USD","day",
            "Average USD market price across major bitcoin exchanges.", values)

    }

    @Test
    fun testFetchingOfChartData() {
        val latch = CountDownLatch(1)
        var tempChartData: ChartData? = null
        Mockito.`when`(fetchBitcoinChartDataUsecase.execute())
            .thenReturn(Single.just(chartData))
        viewModel.fetchChartData("1year","8hours").subscribeOn(Schedulers.io()).subscribe(
            Consumer {
                tempChartData = it
                latch.countDown()
            }
        )
        latch.await()
        verify(fetchBitcoinChartDataUsecase).execute()
        Assert.assertEquals("ok", tempChartData!!.status)
        Assert.assertEquals("Market Price (USD)", tempChartData!!.name)
        Assert.assertEquals("USD", tempChartData!!.unit)
        Assert.assertEquals("day", tempChartData!!.period)
        Assert.assertEquals("Average USD market price across major bitcoin exchanges.",
            tempChartData!!.description)
        Assert.assertEquals(2,
            tempChartData!!.values.size)


    }
}