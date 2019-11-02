package com.zack.domain.usecases

import com.zack.data.model.ChartData
import com.zack.data.repository.BitCoinChartRepository
import io.reactivex.Single

class FetchBitcoinChartDataUsecase constructor(val bitCoinChartRepository: BitCoinChartRepository): BaseUsecase<ChartData>() {
    override fun buildSingle(): Single<ChartData> {
        return bitCoinChartRepository.fetchChartData()
    }
}