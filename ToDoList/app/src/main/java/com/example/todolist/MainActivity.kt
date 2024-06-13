package com.example.todolist

import android.annotation.SuppressLint
import android.content.ContentValues
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract.Data
import android.service.voice.VoiceInteractionSession.ActivityId
import android.text.Editable
import android.view.LayoutInflater
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(),AddTaskFragment.AddTaskListener {
    private lateinit var dbHelper :DatabaseHandler
    private lateinit var adapter: MyAdapter
    lateinit var dataList:MutableList<MyData>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        dbHelper= DatabaseHandler(this)
        dataList=readTasks(dbHelper).toMutableList()


        val recyclerViewTask=findViewById<RecyclerView>(R.id.recycler_view)
        recyclerViewTask.layoutManager=LinearLayoutManager(this)
        adapter=MyAdapter(this,dataList)
        recyclerViewTask.adapter=adapter

        //swipe to delete
        val itemTouchHelper=ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position =viewHolder.adapterPosition
                var task=dataList[position]
                when(direction){
                    ItemTouchHelper.LEFT->{
                        deleteTask(task)
                        dataList.removeAt(position)
                        adapter.notifyItemRemoved(position)
                    }
                    ItemTouchHelper.RIGHT->{
                        showEditTaskDailog(task,position)
                    }
                }
            }
        })
        itemTouchHelper.attachToRecyclerView(recyclerViewTask)



        val fab=findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener{
            val addTaskFragment=AddTaskFragment()
            addTaskFragment.show(supportFragmentManager,"addTaskFragment")
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onTaskAdded(task: String) {
        insertTask(dbHelper,false,task)
        dataList.clear()
        dataList.addAll(readTasks((dbHelper)))
        adapter.notifyDataSetChanged()
    }

    private fun insertTask(dbHelper:DatabaseHandler,status:Boolean,task:String){
        val db=dbHelper.writableDatabase
        val values=ContentValues().apply{
            put(DatabaseContract.TaskEntry.COLUMN_TASK, if (status) 1 else 0)
            put(DatabaseContract.TaskEntry.COLUMN_TASK,task)
        }
        db.insert(DatabaseContract.TaskEntry.TABLE_NAME,null,values)
    }

    private fun readTasks(dbHelper: DatabaseHandler):List<MyData>{
        val db=dbHelper.readableDatabase
        val dataList=arrayOf(
            DatabaseContract.TaskEntry.COLUMN_ID,
            DatabaseContract.TaskEntry.COLUMN_STATUS,
            DatabaseContract.TaskEntry.COLUMN_TASK
        )
        val cursor=db.query(true,DatabaseContract.TaskEntry.TABLE_NAME,dataList,null,null,null,null,null,null)
        val tasks= mutableListOf<MyData>()
        with(cursor){
            while(moveToNext()){
                val id=getInt(getColumnIndexOrThrow(DatabaseContract.TaskEntry.COLUMN_ID))
                val status=getInt(getColumnIndexOrThrow(DatabaseContract.TaskEntry.COLUMN_STATUS))>0
                val task=getString(getColumnIndexOrThrow(DatabaseContract.TaskEntry.COLUMN_TASK))
                tasks.add(MyData(id,status,task))
            }
        }
        cursor.close()
        return tasks
    }
    private fun deleteTask(task:MyData){
        val db=dbHelper.writableDatabase
        db.delete(DatabaseContract.TaskEntry.TABLE_NAME,"${DatabaseContract.TaskEntry.COLUMN_ID}=?",arrayOf(task.id.toString()))
    }

    private fun showEditTaskDailog(task: MyData,position :Int){
        val dailogView=LayoutInflater.from(this).inflate(R.layout.edit_task,null,false)
        val editText=dailogView.findViewById<EditText>(R.id.new_task)
        editText.setText(task.task)

        val dailog=AlertDialog.Builder(this)
        dailog.setTitle("Edit Task")
            .setView(dailogView)
            .setPositiveButton("Save"){dailog,_->
                val newTask=editText.text.toString()
                task.task=newTask
                updateTask(task)
                dataList[position]=task
                adapter.notifyItemChanged(position)
                dailog.dismiss()
            }
            .setNegativeButton("cancel"){dailog,_->
                adapter.notifyItemChanged(position)
                dailog.cancel()
            }
            .create()
        dailog.show()
    }
    private fun updateTask(task:MyData){
        val db=dbHelper.writableDatabase
        val values=ContentValues().apply {
            put(DatabaseContract.TaskEntry.COLUMN_TASK,task.task)
            put(DatabaseContract.TaskEntry.COLUMN_STATUS,task.status)
        }
        db.update(DatabaseContract.TaskEntry.TABLE_NAME,values,"${DatabaseContract.TaskEntry.COLUMN_ID}=?",
            arrayOf(task.id.toString()))
    }
}