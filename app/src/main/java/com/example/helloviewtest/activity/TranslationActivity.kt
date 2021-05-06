package com.example.helloviewtest.activity

import android.content.Intent
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.helloviewtest.R
import com.example.helloviewtest.model.TranslationViewModel
import com.example.helloviewtest.utils.AppUtil
import com.example.helloviewtest.utils.toSubscribe

class TranslationActivity :BaseActivity() {


    private var viewModel:TranslationViewModel?=null

    private var mRawTextInputView:EditText?=null
    private var mTranslationButton: Button?=null
    private var mTargetTextView:TextView?=null


    override fun getLayout(): Int = R.layout.translation_layout


    companion object{

        fun startActivity(){
            val intent= Intent(AppUtil.applicationContext,TranslationActivity::class.java)
            intent.flags=Intent.FLAG_ACTIVITY_NEW_TASK
            AppUtil.applicationContext.startActivity(intent)
        }


    }

    override fun initView() {
        mRawTextInputView=findViewById(R.id.raw_text_input_view)
        mTargetTextView=findViewById(R.id.target_text_view)
        mTranslationButton=findViewById<Button>(R.id.translation_button_view).apply {
            setOnClickListener { handleTranslationButtonClicked() }
        }
    }

    private fun handleTranslationButtonClicked(){
        viewModel?.postTranslationButton()
    }


    override fun observeLiveData() {
        val localViewModel=viewModel?:return
        localViewModel.mldTranslationButton.observe(this, Observer {
            val rawText=mRawTextInputView?.text?.toString()?:return@Observer
            localViewModel.translationRawText(rawText).toSubscribe {
                val dataString=if(it.code==200){
                    it.getDataString()
                }else{
                    "errorCode: ${it.code}, result.data:${it.data}"
                }
                localViewModel.postTargetText(dataString)
            }
        })
        localViewModel.mldTargetText.observe(this, Observer {
            mTargetTextView?.text=it
        })

    }


    override fun initViewModel() {
        viewModel=ViewModelProvider(this).get(TranslationViewModel::class.java)
    }
}