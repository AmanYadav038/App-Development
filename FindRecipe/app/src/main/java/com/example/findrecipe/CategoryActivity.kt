package com.example.findrecipe

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.findrecipe.databinding.ActivityCategoryBinding

class CategoryActivity : AppCompatActivity() {
    lateinit var binding1: ActivityCategoryBinding
    lateinit var values:ArrayList<recipe>
    lateinit var cadapter: categoryAdapter
    lateinit var a: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding1= ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding1.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        a=intent.getStringExtra(MainActivity.KEY).toString()
        binding1.setCategory.text=a
        binding1.back.setOnClickListener{
            finish()
        }
        setupcategories()

    }

    private fun setupcategories() {
        values= ArrayList()
        binding1.rvCategory.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        val db=Room.databaseBuilder(this,AppDatabase::class.java,"db_name")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .createFromAsset("recipe.db")
            .build()
        val daoObject=db.getDao()
        val recipes=daoObject.getAll()
        for(i in recipes!!.indices){
            if (recipes[i]!!.category.contains(a)){
                values.add(recipes[i]!!)
            }
            cadapter= categoryAdapter(this,values)
            binding1.rvCategory.adapter=cadapter
        }
    }
}