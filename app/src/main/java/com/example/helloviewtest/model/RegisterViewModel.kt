package com.example.helloviewtest.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.helloviewtest.repo.RegisterRepo
import com.example.helloviewtest.utils.AppUtil
import com.example.helloviewtest.utils.Result
import io.reactivex.Observable
import retrofit2.create
import java.lang.StringBuilder

class RegisterViewModel:ViewModel() {

    private val repo by lazy {
        AppUtil.retrofit.create(RegisterRepo::class.java)
    }

    val mldSendEmail = MutableLiveData<Result>()
    val mldRegister=MutableLiveData<Result>()


    fun postSendEmail(value:Result){
        mldSendEmail.postValue(value)
    }

    fun postRegister(value: Result){
        mldRegister.postValue(value)
    }


    fun sendEmail(email:String):Observable<Result>{
        return repo.sendEmail(email)
    }

    fun register(email:String,password:String,verifyCode:String):Observable<Result>{
        return repo.register(email,password,verifyCode)
    }




}