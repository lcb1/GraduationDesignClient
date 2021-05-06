package com.example.helloviewtest.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.helloviewtest.repo.TranslationRepo
import com.example.helloviewtest.utils.AppUtil
import com.example.helloviewtest.utils.Result
import io.reactivex.Observable

class TranslationViewModel:ViewModel() {

    private val translationRepo by lazy {
        AppUtil.retrofit.create(TranslationRepo::class.java)
    }

     val mldTranslationButton=MutableLiveData<Boolean>()
     val mldTargetText=MutableLiveData<String>()


    fun translationRawText(rawText:String):Observable<Result>{

        return translationRepo.translationRawText(rawText)

    }



    fun postTranslationButton(isClick:Boolean=true){
        if(isClick) mldTranslationButton.postValue(true)
    }

    fun postTargetText(target:String){
        mldTargetText.postValue(target)
    }





}