package com.example.findrecipe

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.findrecipe.databinding.SearchitemBinding

class SearchAdapter(var values:ArrayList<recipe>,var context: Context):
    RecyclerView.Adapter<SearchAdapter.VH>() {
        class VH(val binding: SearchitemBinding):RecyclerView.ViewHolder(binding.root){

        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchAdapter.VH {
        val view=SearchitemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return VH(view)
    }
    @SuppressLint("NotifyDataSetChanged")
    fun filterList(filterList:ArrayList<recipe>){
        values=filterList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        Glide.with(context).load(values[position].img).into(holder.binding.imageSearch)
        holder.binding.textSearch.text=values[position].tittle
        holder.binding.searchItem.setOnClickListener{
            val intent= Intent(context,DishActivity::class.java)
            intent.putExtra("title",values[position].tittle)
            intent.putExtra("image",values[position].img)
            intent.putExtra("ing",values[position].ing)
            intent.putExtra("steps",values[position].des)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return values.size

    }
}