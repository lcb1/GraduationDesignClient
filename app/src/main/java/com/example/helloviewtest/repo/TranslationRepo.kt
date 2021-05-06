package com.example.helloviewtest.repo

import com.example.helloviewtest.utils.Result
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface TranslationRepo {

    @GET("translation/{raw_text}")
    fun translationRawText(@Path("raw_text") rawText:String): Observable<Result>


}