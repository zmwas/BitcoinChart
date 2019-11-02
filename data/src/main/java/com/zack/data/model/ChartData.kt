package com.zack.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ChartData(
    @SerializedName("status")
    @Expose
    var status: String,
    @SerializedName("name")
    @Expose
    var name: String,
    @SerializedName("unit")
    @Expose
    var unit: String,
    @SerializedName("period")
    @Expose
    var period: String,
    @SerializedName("description")
    @Expose
    var description: String,
    @SerializedName("values")
    @Expose
    var values: List<Value>
)
