package com.example.findrecipe

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Database
import androidx.room.Room
import com.example.findrecipe.databinding.ActivityMainBinding
import com.example.findrecipe.databinding.BottomSheetBinding
import java.util.Locale.Category

class MainActivity : AppCompatActivity() {
    lateinit var dataList:ArrayList<recipe>
    lateinit var rvAdapter:PopularAdapter
    private lateinit var binding: ActivityMainBinding
    private lateinit var bindingBottom:BottomSheetBinding
    companion object{
        const val KEY="com.example.findrecipe.KEY"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupRecyclerView();
        bindingBottom=BottomSheetBinding.inflate(layoutInflater)
        binding.bottomSheet.setOnClickListener{
            val dialog=Dialog(this)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(bindingBottom.root)
            dialog.show()
        }
        bindingBottom.appDev.setOnClickListener{
            intent=Intent(Intent.ACTION_VIEW)
            intent.data=Uri.parse("https://github.com/AmanYadav038")
            startActivity(intent)
        }
        bindingBottom.privacy.setOnClickListener{
            intent=Intent(this,PrivacyActivity::class.java)
            startActivity(intent)
        }
        binding.searchText.setOnClickListener{
            startActivity(Intent(this,SearchActivity::class.java))
        }
        binding.salad.setOnClickListener{
            intent=Intent(this,CategoryActivity::class.java)
            intent.putExtra(KEY,"Salad")
            startActivity(intent)
        }
        binding.deserts.setOnClickListener{
            intent=Intent(this,CategoryActivity::class.java)
            intent.putExtra(KEY,"Desserts")
            startActivity(intent)
        }
        binding.drinks.setOnClickListener{
            intent=Intent(this,CategoryActivity::class.java)
            intent.putExtra(KEY,"Drinks")
            startActivity(intent)
        }
        binding.maincourse.setOnClickListener{
            intent=Intent(this,CategoryActivity::class.java)
            intent.putExtra(KEY,"Dish")
            startActivity(intent)
        }
    }

    private fun setupRecyclerView() {
        dataList= ArrayList()
        binding.rvView.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)

        val db=Room.databaseBuilder(this,AppDatabase::class.java,"db_name")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .createFromAsset("recipe.db")
            .build()
        val daoObject=db.getDao()
        val recipes=daoObject.getAll()
        for (i in recipes!!.indices){
            if (recipes[i]!!.category.contains("Popular")){
                dataList.add(recipes[i]!!)
            }
            rvAdapter=PopularAdapter(this,dataList)
            binding.rvView.adapter=rvAdapter
        }
    }
}