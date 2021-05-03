package com.example.helloviewtest.utils

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.view.Gravity
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.example.helloviewtest.MainApplication
import com.example.helloviewtest.activity.BaseActivity
import com.example.helloviewtest.activity.MainActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object AppUtil {

    lateinit var applicationContext: Context

    private const val SERVER_ADDR="http://1.116.71.60:18080"
    private const val DEFAULT_FILE_NAME="default"
    private val sp by lazy {
        applicationContext.getSharedPreferences(DEFAULT_FILE_NAME,Context.MODE_PRIVATE)
    }
    val retrofit by lazy {
        Retrofit.Builder().baseUrl(SERVER_ADDR).addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create()).build()
    }
    fun toastShow(showTime:Int=Toast.LENGTH_SHORT, callBack:()->String){
        Toast.makeText(applicationContext,callBack(),showTime).apply {
            setGravity(Gravity.CENTER,0,0)
        }.show()
    }



    fun storePut(key:String,value:String,filename:String= DEFAULT_FILE_NAME){
        val sp= getSp(filename)
        val edit=sp.edit()
        edit.putString(key,value)
        edit.apply()
    }
    fun storeGet(key:String,filename:String= DEFAULT_FILE_NAME):String?{
        val sp= getSp(filename)
        return sp.getString(key,"")
    }
    fun getSp(filename: String= DEFAULT_FILE_NAME):SharedPreferences{
        return if(filename== DEFAULT_FILE_NAME){
            sp
        }else{
            applicationContext.getSharedPreferences(filename,Context.MODE_PRIVATE)
        }
    }

}

data class Result(val code:Int,val data:Any){

    fun isOk():Boolean{
        return code==200
    }

    fun getDataString():String{
        return data as String
    }
}

fun Observable<Result>.toSubscribe(callBack: (result:Result) -> Unit):Disposable{
    return subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe ({
        callBack(it)
    },{callBack(Result(-1,it.message.toString()))})
}

fun Disposable.addTo(baseActivity: BaseActivity){
    baseActivity.compositeDisposable.add(this)
}




