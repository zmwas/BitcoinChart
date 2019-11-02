package com.zack.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Value(
    @SerializedName("x")
    @Expose
    var x: Double,
    @SerializedName("y")
    @Expose
    var y: Double
)
