package com.example.helloviewtest.activity


import android.os.Build
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.helloviewtest.R
import com.example.helloviewtest.model.LoginViewModel
import com.example.helloviewtest.repo.LoginRepo
import com.example.helloviewtest.utils.*

class MainActivity :BaseActivity() {

    companion object{
        private const val IS_OK="is_ok"
        private const val LOGIN_STATE="login_state"
    }

    private var mViewModel:LoginViewModel?=null
    private var mAccountInputView:EditText?=null
    private var mPasswordInputView:EditText?=null
    private var mLoginButton: Button?=null
    private val loginRepo by lazy {
        AppUtil.retrofit.create(LoginRepo::class.java)
    }

    override fun interceptInit() {
        super.interceptInit()
        if(AppUtil.storeGet(LOGIN_STATE)== IS_OK){
            loginIsOk()
        }
    }


    override fun initView() {
        mAccountInputView=findViewById(R.id.login_account_input_view)
        mPasswordInputView=findViewById(R.id.login_password_input_view)
        mLoginButton=findViewById<Button>(R.id.login_button_view).apply {
            setOnClickListener { handleLoginButtonClicked() }
        }
    }

    private fun handleLoginButtonClicked(){
        val email=mAccountInputView?.text?.toString()?:return
        val password = mPasswordInputView?.text?.toString()?:return


        loginRepo.login(email,password).toSubscribe { mViewModel?.postLogin(it) }.addTo(this)
    }

    override fun observeLiveData() {
        mViewModel?.ldLogin?.observe(this, { handleLoginLiveDataObserver(it) })

    }

    override fun initViewModel() {
        mViewModel=ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    fun handleLoginLiveDataObserver(result: Result){
        AppUtil.toastShow {
            result.getDataString()
        }
        if(result.isOk()){
            AppUtil.storePut(LOGIN_STATE, IS_OK)
            loginIsOk()
        }
    }

    fun loginIsOk(){
        FunctionSelectActivity.startActivity()
        finish()
    }


    override fun getLayout(): Int = R.layout.login_layout
    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

}