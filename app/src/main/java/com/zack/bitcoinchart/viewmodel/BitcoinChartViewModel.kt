package com.zack.bitcoinchart.viewmodel

import androidx.lifecycle.ViewModel
import com.zack.data.model.ChartData
import com.zack.domain.usecases.FetchBitcoinChartDataUsecase
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class BitcoinChartViewModel @Inject constructor(private val fetchBitcoinChartDataUsecase: FetchBitcoinChartDataUsecase): ViewModel() {
    fun fetchChartData(timeSpan:String, rollingAverage:String):Single<ChartData> {
        fetchBitcoinChartDataUsecase.rollingAverage = rollingAverage
        fetchBitcoinChartDataUsecase.timeSpan = timeSpan
        return fetchBitcoinChartDataUsecase
            .execute()
    }
}