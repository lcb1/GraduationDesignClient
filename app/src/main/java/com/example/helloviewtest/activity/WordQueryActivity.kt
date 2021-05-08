package com.example.helloviewtest.activity

import android.content.Intent
import android.graphics.Rect
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.helloviewtest.R
import com.example.helloviewtest.adapter.WordQueryAdapter
import com.example.helloviewtest.model.WordQueryViewModel
import com.example.helloviewtest.utils.AppUtil
import com.example.helloviewtest.utils.MockUtils
import com.example.helloviewtest.utils.compact
import com.example.helloviewtest.utils.dp2px

class WordQueryActivity:BaseActivity() {


    private var viewModel:WordQueryViewModel?=null
    private var recyclerView:RecyclerView?=null
    private var wordInputView:EditText?=null

    companion object{
        fun startActivity(){
            val intent=Intent(AppUtil.applicationContext,WordQueryActivity::class.java)
            intent.flags=Intent.FLAG_ACTIVITY_NEW_TASK
            AppUtil.applicationContext.startActivity(intent)
        }
    }


    override fun getLayout(): Int = R.layout.word_query_layout

    override fun initView() {
        initRecyclerView()
        initWordInputView()
    }

    fun initWordInputView(){
        wordInputView=findViewById<EditText>(R.id.word_input_view).apply {
            doOnTextChanged { text, _, _, _ ->
                if(text.isNullOrEmpty()||text.isNullOrBlank()) {
                    viewModel?.postWords()
                }
                handleOnTextChanged(text.compact())
            }

        }
    }




    private fun handleOnTextChanged(text:String){
        viewModel?.run {
            query(text).subscribe( {
                postWords(it) },{
                Log.e("MyDebug",it.stackTraceToString())
                })
        }






    }







    private fun initRecyclerView(){
//        val data=MockUtils.wordQueryMock() as ArrayList
        recyclerView=findViewById<RecyclerView>(R.id.words_rec_view).apply {
            adapter=WordQueryAdapter()
            layoutManager=LinearLayoutManager(this@WordQueryActivity)
            addItemDecoration(WordItemDecoration())
        }
    }

    override fun observeLiveData() {
        val words=viewModel?.words?:return
        val adapter=recyclerView?.adapter as WordQueryAdapter

        words.observe(this, Observer {
            adapter.notifyData(it)
        })

    }

    override fun initViewModel() {
        viewModel=ViewModelProvider(this).get(WordQueryViewModel::class.java)
    }

    class WordItemDecoration:RecyclerView.ItemDecoration(){

        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            super.getItemOffsets(outRect, view, parent, state)
            outRect.top=10.dp2px()
        }

    }


}