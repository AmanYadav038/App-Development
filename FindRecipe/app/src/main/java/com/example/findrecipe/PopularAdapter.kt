package com.example.findrecipe

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.findrecipe.databinding.PopularrecipeviewBinding

class PopularAdapter(val context:Context,val arrayList: ArrayList<recipe>):RecyclerView.Adapter<PopularAdapter.VH>() {

    class VH(var binding :PopularrecipeviewBinding):RecyclerView.ViewHolder(binding.root){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding=PopularrecipeviewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return VH(binding)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        Glide.with(context).load(arrayList[position].img).into(holder.binding.imagePopular)
        holder.binding.popularRecipeName.text= arrayList[position].tittle
        val time=arrayList[position].ing.split("\n".toRegex()).dropWhile { it.isEmpty() }.toTypedArray()
        holder.binding.popularRecipeTime.text="\uD83D\uDD5B "+time[0]
        holder.binding.item.setOnClickListener{
            val intent= Intent(context,DishActivity::class.java)
            intent.putExtra("title",arrayList[position].tittle)
            intent.putExtra("image",arrayList[position].img)
            intent.putExtra("ing",arrayList[position].ing)
            intent.putExtra("steps",arrayList[position].des)
            context.startActivity(intent)
        }

    }
}
