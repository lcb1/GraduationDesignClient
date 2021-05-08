package com.example.helloviewtest.activity

import android.content.Intent
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.helloviewtest.R
import com.example.helloviewtest.model.RegisterViewModel
import com.example.helloviewtest.utils.AppUtil
import com.example.helloviewtest.utils.toSubscribe

class RegisterActivity:BaseActivity() {


    private var mAccountEditText:EditText?=null
    private var mPasswordEditText:EditText?=null
    private var mVerifyCodeEditText:EditText?=null
    private var mSendVerifyCodeButton:Button?=null
    private var mRegisterButton:Button?=null
    private var viewModel:RegisterViewModel?=null



    companion object{
        private const val REGISTER_TIPS="注册成功,已自动跳转"
        private const val EMAIL_TIPS="邮箱发送成功"
        fun startActivity(){
            val intent= Intent(AppUtil.applicationContext,RegisterActivity::class.java)
            intent.flags=Intent.FLAG_ACTIVITY_NEW_TASK
            AppUtil.applicationContext.startActivity(intent)
        }
    }


    override fun getLayout() = R.layout.register_layout

    override fun initView() {
        mAccountEditText=findViewById(R.id.register_account_input_view)
        mPasswordEditText=findViewById(R.id.register_password_input_view)
        mVerifyCodeEditText=findViewById(R.id.register_verify_code_input_view)
        mSendVerifyCodeButton=findViewById<Button>(R.id.register_send_code_button).apply {
            setOnClickListener { handleSendVerifyCodeButtonClicked() }
        }
        mRegisterButton=findViewById<Button>(R.id.register_button).apply {
            setOnClickListener { handleRegisterButtonClicked() }
        }
    }

    fun handleSendVerifyCodeButtonClicked(){
        val localViewModel=viewModel?:return
        val email=getEmail()
        localViewModel.sendEmail(email).toSubscribe { localViewModel.postSendEmail(it) }
    }

    fun handleRegisterButtonClicked(){
        val localViewModel=viewModel?:return
        val email=getEmail()
        val password=getPassword()
        val verifyCode=getVerifyCode()
        localViewModel.register(email,password,verifyCode).toSubscribe { localViewModel.postRegister(it) }
    }



    override fun observeLiveData() {
        val localViewModel=viewModel?:return
        localViewModel.mldRegister.observe(this, Observer {
            val msg= if(it.isOk()){
                AppUtil.storeLoginState()
                REGISTER_TIPS
            }else{
                it.getDataString()
            }
            AppUtil.toastShow { msg }
            if(it.isOk()){
                FunctionSelectActivity.startActivity()
            }
        })

        localViewModel.mldSendEmail.observe(this, Observer {
            val msg = if(it.isOk()){
                EMAIL_TIPS
            }else{
                it.getDataString()
            }

            AppUtil.toastShow { msg }
        })

    }

    fun getEmail():String{
        return mAccountEditText?.text.toString()
    }
    fun getPassword():String{
        return mPasswordEditText?.text.toString()
    }

    fun getVerifyCode():String{
        return mVerifyCodeEditText?.text.toString()
    }





    override fun initViewModel() {
        viewModel=ViewModelProvider(this).get(RegisterViewModel::class.java)
    }
}