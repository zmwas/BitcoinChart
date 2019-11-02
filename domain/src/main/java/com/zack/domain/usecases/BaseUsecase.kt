package com.zack.domain.usecases

import io.reactivex.Single

abstract class BaseUsecase<T> {

    abstract fun buildSingle():Single<T>

    fun execute():Single<T> {
        return buildSingle()
    }
}