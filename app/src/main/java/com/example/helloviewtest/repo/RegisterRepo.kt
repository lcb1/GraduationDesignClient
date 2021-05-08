package com.example.helloviewtest.repo

import com.example.helloviewtest.utils.Result
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface RegisterRepo {


    /**
     *     @GetMapping("/register/{${USER_EMAIL}}/{${PASSWORD}}/{${VERIFY_CODE}}")
     *         @GetMapping("send/email/{${USER_EMAIL}}")
     */
    companion object{
        private const val EMAIL_PATH="user_email"
        private const val PASSWORD_PATH="password"
        private const val VERIFY_CODE="verify_code"
        private const val SEND_EMAIL_PAI="/send/email/{${EMAIL_PATH}}"
        private const val REGISTER_PAI="/register/{${EMAIL_PATH}}/{${PASSWORD_PATH}}/{${VERIFY_CODE}}"
    }
    @GET(SEND_EMAIL_PAI)
    fun sendEmail(@Path(EMAIL_PATH) email:String):Observable<Result>

    @GET(REGISTER_PAI)
    fun register(@Path(EMAIL_PATH) email:String,
                 @Path(PASSWORD_PATH) password:String,
                 @Path(VERIFY_CODE) verifyCode:String):Observable<Result>




}