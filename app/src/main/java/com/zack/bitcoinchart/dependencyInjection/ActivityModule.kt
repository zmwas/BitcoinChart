package com.zack.bitcoinchart.dependencyInjection

import com.zack.bitcoinchart.ui.ChartActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector
@Module
abstract class ActivityModule {
    @ContributesAndroidInjector
    abstract fun chartActivity(): ChartActivity

}