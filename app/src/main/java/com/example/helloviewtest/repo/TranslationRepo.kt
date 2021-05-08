package com.example.helloviewtest.repo

import com.example.helloviewtest.utils.Result
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface TranslationRepo {

    companion object{
        private const val TRANSLATION_API10="translation/{raw_text}"
        private const val TRANSLATION_API11="translation/api11/{raw_text}"
    }


    @GET(TRANSLATION_API11)
    fun translationRawText(@Path("raw_text") rawText:String): Observable<Result>


}