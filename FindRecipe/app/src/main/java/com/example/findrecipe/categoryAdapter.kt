package com.example.findrecipe

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.findrecipe.databinding.CategorizedBinding

class categoryAdapter(val context:Context, val values:ArrayList<recipe>) : RecyclerView.Adapter<categoryAdapter.VH>() {

    class VH(var binding: CategorizedBinding):RecyclerView.ViewHolder(binding.root){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding=CategorizedBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return VH(binding)
    }

    override fun getItemCount(): Int {
        return values.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        Glide.with(context).load(values[position].img).into(holder.binding.image)
        holder.binding.dishName.text=values[position].tittle
        val time=values[position].ing.split("\n".toRegex()).dropWhile { it.isEmpty() }.toTypedArray()
        holder.binding.time.text="\uD83D\uDD5B "+time[0]
        holder.binding.categoryItem.setOnClickListener{
            val intent = Intent(context,DishActivity::class.java)
            intent.putExtra("title",values[position].tittle)
            intent.putExtra("image",values[position].img)
            intent.putExtra("ing",values[position].ing)
            intent.putExtra("steps",values[position].des)
            context.startActivity(intent)
        }
    }
}