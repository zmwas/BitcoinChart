package com.zack.bitcoinchart.dependencyInjection

import androidx.lifecycle.ViewModel
import com.zack.bitcoinchart.viewmodel.BitcoinChartViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(BitcoinChartViewModel::class)
    internal abstract fun bindBitCoinChartViewModel(viewModel: BitcoinChartViewModel): ViewModel

}