package com.example.helloviewtest.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.helloviewtest.utils.Result

class LoginViewModel:ViewModel() {

    val ldLogin=MutableLiveData<Result>()

    fun postLogin(result: Result){
        ldLogin.postValue(result)
    }

}