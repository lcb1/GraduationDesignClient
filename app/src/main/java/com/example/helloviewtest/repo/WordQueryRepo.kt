package com.example.helloviewtest.repo

import com.example.helloviewtest.utils.Result
import com.example.helloviewtest.utils.ResultObj
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface WordQueryRepo {

    companion object{
        private const val PATH_RAW_WORD="raw_word"
        private const val PAGE_N="page_N"
        private const val TARGET="target"
        private const val WORD_QUERY_RAW_WORD_API11 ="/word/query/raw_word/api11/{${PATH_RAW_WORD}}/{${PAGE_N}}"
        private const val WORD_QUERY_TARGET_API10="/word/query/target/{${TARGET}}/{${PAGE_N}}"
    }

    @GET(WORD_QUERY_RAW_WORD_API11)
    fun wordQueryByRawWordApi11(@Path(PATH_RAW_WORD) rawWord:String,@Path(PAGE_N) pageN:Int):Observable<ResultObj>

    @GET(WORD_QUERY_TARGET_API10)
    fun wordQueryByTargetApi10(@Path(TARGET) target:String,@Path(PAGE_N) pageN: Int):Observable<ResultObj>



}