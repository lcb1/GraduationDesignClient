package com.example.helloviewtest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.helloviewtest.R
import com.example.helloviewtest.adapter.holder.WordQueryHolder
import com.example.helloviewtest.entity.WordEntity

class WordQueryAdapter(private val words:ArrayList<WordEntity> = ArrayList()): RecyclerView.Adapter<WordQueryHolder>() {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordQueryHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.word_item_layout,parent,false)
        return WordQueryHolder(view)
    }

    override fun onBindViewHolder(holder: WordQueryHolder, position: Int) {
        holder.bindData(words[position])
    }

    override fun getItemCount() = words.size
    fun notifyData(outWords:List<WordEntity>):WordQueryAdapter{
        words.clear()
        words.addAll(outWords)
        notifyDataSetChanged()
        return this
    }

}



