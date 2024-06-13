package com.example.findrecipe

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.findrecipe.databinding.ActivityDishBinding

class DishActivity : AppCompatActivity() {
    lateinit var binding: ActivityDishBinding
    private var enlarged=true
    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivityDishBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.back2home.setOnClickListener{
            finish()
        }
        Glide.with(this).load(intent.getStringExtra("image")).into(binding.dishImage)
        binding.title.text=intent.getStringExtra("title").toString()
        val ing=intent.getStringExtra("ing")!!.split("\n".toRegex()).dropWhile { it.isEmpty() }.toTypedArray()
        binding.stepsData.text=intent.getStringExtra("steps").toString()
        binding.time.text=ing[0]
        for (i in 1 until ing.size){
            binding.ingData.text=
                """${binding.ingData.text} 🟢 ${ing[i]}
                    
                """.trimIndent()
        }

        binding.stepScroll.visibility=View.GONE
        binding.steps.background=null
        binding.steps.setTextColor(getColor(R.color.black))
        binding.steps.setOnClickListener{
            binding.ing.background=null
            binding.steps.setBackgroundResource(R.drawable.btn_ing)
            binding.steps.setTextColor(getColor(R.color.white))
            binding.ing.setTextColor(getColor(R.color.black))
            binding.stepScroll.visibility=View.VISIBLE
            binding.ingScroll.visibility=View.GONE
        }
        binding.ing.setOnClickListener{
            binding.steps.background=null
            binding.steps.setTextColor(getColor(R.color.black))
            binding.ing.setTextColor(getColor(R.color.white))
            binding.ing.setBackgroundResource(R.drawable.btn_ing)
            binding.ingScroll.visibility=View.VISIBLE
            binding.stepScroll.visibility=View.GONE
        }
        binding.enlarge.setOnClickListener{
            if (enlarged){
                binding.dishImage.scaleType=ImageView.ScaleType.FIT_CENTER
                enlarged=false
            }else{
                binding.dishImage.scaleType=ImageView.ScaleType.CENTER_CROP
                enlarged=true
            }
        }

    }
}