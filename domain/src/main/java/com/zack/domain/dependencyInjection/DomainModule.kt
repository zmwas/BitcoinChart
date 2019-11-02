package com.zack.domain.dependencyInjection

import com.zack.data.repository.BitCoinChartRepository
import com.zack.domain.usecases.FetchBitcoinChartDataUsecase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DomainModule {
    @Provides
    @Singleton
    fun provideFetchBitCoinChartUseCase(bitCoinChartRepository: BitCoinChartRepository):FetchBitcoinChartDataUsecase {
        return FetchBitcoinChartDataUsecase(bitCoinChartRepository)
    }
}