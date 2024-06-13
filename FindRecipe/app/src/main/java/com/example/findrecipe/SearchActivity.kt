package com.example.findrecipe

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.findrecipe.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {
    lateinit var values:ArrayList<recipe>
    lateinit var rvAdapter: SearchAdapter
    lateinit var binding:ActivitySearchBinding
    lateinit var recipes: List<recipe?>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.searchItem.requestFocus()
        binding.backBtn.setOnClickListener{
            finish()
        }
        val db=Room.databaseBuilder(this,AppDatabase::class.java,"db_name")
            .allowMainThreadQueries()
            .createFromAsset("recipe.db")
            .build()
        val daoObject=db.getDao()
        recipes = daoObject.getAll()
        setuprecyclerview()
        binding.searchItem.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s.toString()!=""){
                    filter(s.toString())
                }
                else{
                    setuprecyclerview()
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
    }

    private fun setuprecyclerview() {
        values= ArrayList()
        binding.searchRv.layoutManager=LinearLayoutManager(this)
        for (i in recipes.indices){
            values.add(recipes[i]!!)
            rvAdapter=SearchAdapter(values,this)
            binding.searchRv.adapter=rvAdapter
        }
    }
    private fun filter(filterText:String){
        val filterList=ArrayList<recipe>()
        for (i in recipes.indices){
            if (recipes[i]!!.tittle.lowercase().contains(filterText.lowercase())){
                filterList.add(recipes[i]!!)
            }
            rvAdapter.filterList(filterList)
        }
    }
}