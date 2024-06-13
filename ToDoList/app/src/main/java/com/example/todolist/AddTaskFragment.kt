package com.example.todolist

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddTaskFragment :BottomSheetDialogFragment() {
    private lateinit var listener:AddTaskListener

    interface AddTaskListener{
        fun onTaskAdded(task:String)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener=context as AddTaskListener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.new_task,container,false)
        val taskEdit=view.findViewById<EditText>(R.id.new_task)
        val addTaskBtn=view.findViewById<Button>(R.id.save_task)

        addTaskBtn.setOnClickListener{
            val task=taskEdit.text.toString()
            if (task.isNotEmpty()){
                listener.onTaskAdded(task)
                dismiss()
            }
        }
        return view
    }
}