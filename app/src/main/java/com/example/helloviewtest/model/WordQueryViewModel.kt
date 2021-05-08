package com.example.helloviewtest.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.helloviewtest.entity.WordEntity
import com.example.helloviewtest.repo.WordQueryRepo
import com.example.helloviewtest.utils.AppUtil
import com.google.gson.reflect.TypeToken
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.lang.Exception

class WordQueryViewModel:ViewModel() {




    val words=MutableLiveData<List<WordEntity>>()
    companion object{
        private const val PageN=15
        private const val FILENAME="word_query_cache_file"
        private val cacheData=HashMap<String,String>()
    }
    private val repo by lazy {
        AppUtil.retrofit.create(WordQueryRepo::class.java)
    }



//    fun getCandidateWords(subWord:String):Observable<List<WordEntity>>{
//
//    }

    fun postWords(outWords:List<WordEntity> = emptyList()){
        words.postValue(outWords)
    }

    fun query(word:String):Observable<List<WordEntity>>{




        cacheData[word]?.let {
            try {
                return Observable.just(toWordEntity(it))
            }catch (e:Exception){
                cacheData.remove(word)
            }
        }



        storeGet(word)?.let {
            try {
                return Observable.just(toWordEntity(it))
            }catch (e:Exception){
                cacheData.remove(word)
            }
        }


        val rawObservable=if(AppUtil.isEnglish(word)){
            repo.wordQueryByRawWordApi11(word,PageN)
        }else{
            repo.wordQueryByTargetApi10(word, PageN)
        }


        return rawObservable.map {
            if(!it.isOk()) return@map emptyList<WordEntity>()


            val data=it.getDataString()
            if(!data.trim().isNullOrBlank()){
                storePut(word,data)
                cacheData[word]=data
            }

            return@map toWordEntity(data)
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }


    fun toWordEntity(data:String):List<WordEntity>{
        val type=object :TypeToken<List<WordEntity>>(){}.type
        return AppUtil.gson.fromJson(data,type)
    }



    private fun storePut(word:String,data:String){
        AppUtil.storePut(word,data, FILENAME)
    }
    private fun storeGet(word: String):String?{
        return AppUtil.storeGet(word, FILENAME)
    }


}