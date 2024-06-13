package com.example.todolist

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(val context: Context,val todoList: List<MyData>) :RecyclerView.Adapter<MyAdapter.VH>(){
    class VH(itemView: View):RecyclerView.ViewHolder(itemView){
        val checkbox=itemView.findViewById<CheckBox>(R.id.checkTextBox)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val itemView=LayoutInflater.from(context).inflate(R.layout.task_card,parent,false)
        return VH(itemView)
    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.checkbox.text=todoList[position].task
        holder.checkbox.isChecked=todoList[position].status
    }

}