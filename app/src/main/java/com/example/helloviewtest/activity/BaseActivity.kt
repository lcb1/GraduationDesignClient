package com.example.helloviewtest.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import io.reactivex.disposables.CompositeDisposable

abstract class BaseActivity:AppCompatActivity() {

    val compositeDisposable by lazy {
        CompositeDisposable()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        interceptInit()
        init()
    }

    open fun interceptInit(){

    }


    private fun init(){
        initLayout()
        initViewModel()
        initView()
        observeLiveData()
    }


    abstract fun getLayout():Int

    abstract fun initView()

    abstract fun observeLiveData()
    abstract fun initViewModel()
    private fun initLayout(){
        setContentView(getLayout())
    }




}