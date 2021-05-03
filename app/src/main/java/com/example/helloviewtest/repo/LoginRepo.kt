package com.example.helloviewtest.repo

import com.example.helloviewtest.utils.Result
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface LoginRepo {
    @GET("login/{user_email}/{password}")
    fun login(@Path("user_email") userEmail:String,@Path("password") password:String):Observable<Result>
}