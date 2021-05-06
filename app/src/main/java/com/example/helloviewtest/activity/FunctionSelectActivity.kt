package com.example.helloviewtest.activity

import android.content.Intent
import android.widget.Button
import com.example.helloviewtest.R
import com.example.helloviewtest.utils.AppUtil

class FunctionSelectActivity:BaseActivity() {
    override fun getLayout(): Int = R.layout.function_select_layout

    private var mTranslationView:Button?=null
    private var mWordQueryView:Button?=null


    companion object{
        fun startActivity(){
            val intent = Intent(AppUtil.applicationContext,FunctionSelectActivity::class.java)
            intent.flags=Intent.FLAG_ACTIVITY_NEW_TASK
            AppUtil.applicationContext.startActivity(intent)
        }
    }

    override fun initView() {
        mTranslationView=findViewById<Button>(R.id.function_translation_text_button).apply {
            setOnClickListener { handleTranslationViewClicked() }
        }
        mWordQueryView=findViewById<Button>(R.id.function_word_select_button).apply {
            setOnClickListener { handleWordQueryViewClicked() }
        }
    }

    fun handleTranslationViewClicked(){
        jumpToTranslationActivity()
    }

    fun jumpToTranslationActivity(){
        TranslationActivity.startActivity()
    }

    fun handleWordQueryViewClicked(){

    }

    override fun observeLiveData() {

    }

    override fun initViewModel() {

    }
}