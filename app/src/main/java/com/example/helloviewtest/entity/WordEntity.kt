package com.example.helloviewtest.entity

import com.google.gson.annotations.SerializedName

data class WordEntity(
    @SerializedName("raw_word") val rawWord:String,
    @SerializedName("target") val target:String,
    @SerializedName("uk_phone") val ukPhone:String,
    ) {



}