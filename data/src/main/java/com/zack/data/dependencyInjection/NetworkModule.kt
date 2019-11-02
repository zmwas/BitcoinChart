package com.zack.data.dependencyInjection

import com.zack.data.api.BitcoinChartApiService
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        val queryParamInterceptor: Interceptor = Interceptor {
            val request = it.request()
            val url = request.url()
            val queryUrl = url.newBuilder()
                .addQueryParameter("timespan", "5weeks")
                .addQueryParameter("rollingAverage", "8hours")
                .addQueryParameter("format", "json")
                .build()
            val requestBuilder: Request.Builder = request.newBuilder().url(queryUrl)
            it.proceed(requestBuilder.build())
        }
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient().newBuilder().addInterceptor(loggingInterceptor).addInterceptor(queryParamInterceptor)
            .build()
    }


    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("")
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideBitcoinChartApiService(retrofit: Retrofit): BitcoinChartApiService {
        return retrofit.create(BitcoinChartApiService::class.java)
    }
}