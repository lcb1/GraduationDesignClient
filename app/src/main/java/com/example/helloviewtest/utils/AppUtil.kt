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
import com.google.gson.Gson
import com.google.gson.JsonElement
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.StringBuilder

object AppUtil {






    private var cacheDensity:Float?=null

    lateinit var applicationContext: Context

    private const val SERVER_ADDR="http://1.116.71.60:18080"
    private const val DEFAULT_FILE_NAME="default"
    private val sp by lazy {
        applicationContext.getSharedPreferences(DEFAULT_FILE_NAME,Context.MODE_PRIVATE)
    }
    private val cacheSp by lazy {
        HashMap<String,SharedPreferences>().apply {
            put(DEFAULT_FILE_NAME,sp)
        }
    }

    val retrofit by lazy {
        Retrofit.Builder().baseUrl(SERVER_ADDR).addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    val gson by lazy { Gson() }

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
        return if(sp.contains(key)) sp.getString(key,"") else null
    }
    fun getSp(filename: String= DEFAULT_FILE_NAME):SharedPreferences{
        return if(cacheSp.containsKey(filename)){
            cacheSp[filename]!!
        }else{
            val sp=applicationContext.getSharedPreferences(filename,Context.MODE_PRIVATE)
            cacheSp[filename]=sp
            sp
        }



    }

    fun getDensity():Float{
        cacheDensity?.let {
            return it
        }

        val density= applicationContext.resources.displayMetrics.density
        cacheDensity=density
        return density
    }

    fun isEnglish(lang:String):Boolean{
        return lang.length==lang.toByteArray().size
    }


    fun storeHasKey(key:String,filename: String= DEFAULT_FILE_NAME):Boolean{
        return getSp().contains(key)
    }

    fun isLogin():Boolean{
        return storeHasKey(StoreConfig.LOGIN_STATE)&& storeGet(StoreConfig.LOGIN_STATE)== StoreConfig.IS_OK
    }

    fun storeLoginState(state:String=StoreConfig.IS_OK){
        storePut(StoreConfig.LOGIN_STATE,state)
    }

    fun storeUser(account:String,password:String){
        storePut(StoreConfig.ACCOUNT,account)
        storePut(StoreConfig.PASSWORD,password)
    }



}

object StoreConfig{
    const val IS_OK="is_ok"
    const val LOGIN_STATE="login_state"
    const val ACCOUNT="account"
    const val PASSWORD="password"
}


data class Result(val code:Int,val data:Any){

    fun isOk():Boolean{
        return code==200
    }

    fun getDataString():String{
        return data as String
    }
}

data class ResultObj(val code:Int,val data:JsonElement){
    fun isOk():Boolean{
        return code==200
    }

    fun getDataString():String{
        return data.toString()
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

fun Int.dp2px():Int{
    return (this.toFloat()*AppUtil.getDensity()).toInt()
}

fun Int.px2dp():Int{
    return (this.toFloat()/AppUtil.getDensity()).toInt()
}

fun Any?.compact():String{
    val str=this.toString()
    val builder=StringBuilder()
    for(c in str){
        if(!c.isWhitespace()) builder.append(c)
    }
    return builder.toString()
}




