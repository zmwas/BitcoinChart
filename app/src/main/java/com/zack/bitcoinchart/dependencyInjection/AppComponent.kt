package com.zack.bitcoinchart.dependencyInjection

import com.zack.bitcoinchart.BitCoinChart
import com.zack.domain.dependencyInjection.DomainModule
import com.zack.data.dependencyInjection.DataModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule

import javax.inject.Singleton


@Singleton
@Component(
    modules = [AndroidInjectionModule::class,
        AndroidSupportInjectionModule::class,
        DataModule::class, DomainModule::class,
        ViewModelModule::class,
        ActivityModule::class]
)
interface AppComponent {
    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(app: BitCoinChart): Builder

        fun build(): AppComponent

    }

    fun inject(app: BitCoinChart)

}