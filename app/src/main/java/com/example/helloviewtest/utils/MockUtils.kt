package com.example.helloviewtest.utils

import com.example.helloviewtest.entity.WordEntity
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken

object MockUtils {
    private val mockFileName="mock_word_query.json"
    private val RESULT_DATA_TAG="data"

    fun wordQueryMock(mockFileName:String=this.mockFileName):List<WordEntity>{
        val open=AppUtil.applicationContext.assets.open(mockFileName)
        val json=open.readBytes().decodeToString()
        val type=object :TypeToken<List<WordEntity>>(){}.type
        val jsonObject= getJsonEl(json)
        val jsonData= getDataByEl(jsonObject, RESULT_DATA_TAG)
        return AppUtil.gson.fromJson<ArrayList<WordEntity>>(jsonData,type)
    }

    fun getJsonEl(json:String):JsonObject{
        val type=object :TypeToken<JsonElement>(){}.type
        return AppUtil.gson.fromJson<JsonElement>(json,type).asJsonObject
    }

    fun getDataByEl(el:JsonObject,name:String):String{
        return if(el.has(name)) el.get(name).toString() else "null"
    }







}