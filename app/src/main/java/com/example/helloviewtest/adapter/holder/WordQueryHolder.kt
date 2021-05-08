package com.example.helloviewtest.adapter.holder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.helloviewtest.R
import com.example.helloviewtest.entity.WordEntity
import java.util.*

class WordQueryHolder(
    private val view:View,
    private val wordTextView:TextView=view.findViewById(R.id.word_text_view),
    private val ukPhoneTextView:TextView=view.findViewById(R.id.uk_phone_text_view),
    private val targetTextView:TextView=view.findViewById(R.id.target_text_view),
    ):RecyclerView.ViewHolder(view) {
    fun bindData(wordEntity: WordEntity){
        wordTextView.text=wordEntity.rawWord
        val realUkPhone="美:[${wordEntity.ukPhone}]"
        ukPhoneTextView.text=realUkPhone
        targetTextView.text=wordEntity.target
    }





}