package com.nedim.tasker.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.checkbox.MaterialCheckBox
import com.nedim.tasker.R
import com.nedim.tasker.model.Task
import com.nedim.tasker.viewmodel.TaskViewModel
import java.text.SimpleDateFormat
import java.util.*

class TaskRecyclerAdapter(
    var tasks : List<Task>
) : RecyclerView.Adapter<TaskRecyclerAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.one_item_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val sdfDate : SimpleDateFormat = SimpleDateFormat("dd.MM.yyyy")
        val date = Calendar.getInstance().time
        val taskViewModel = TaskViewModel()
        Log.d("TAGIC", "Sanity check " + tasks.toString())

        holder.tekst.text = tasks[position].text
        holder.id.text = tasks[position].id.toString()
        holder.grupa.text = if(tasks[position].grupa != null) "@" + tasks[position].grupa else "Uncategorized"
        holder.date.text = if(sdfDate.format(date).equals(sdfDate.format(tasks[position].date))) "Today" else sdfDate.format(tasks[position].date)
        holder.checkbox.isChecked = tasks[position].completed

        holder.checkbox.setOnClickListener {
            if(holder.checkbox.isChecked) taskViewModel.doTask(tasks[position], true)
            else taskViewModel.doTask(tasks[position], false)
        }
//        holder.complete.setImageDrawable(
//            ResourcesCompat.getDrawable(
//            holder.itemView.context.resources,
//                if(tasks[position].completed) android.R.drawable.checkbox_on_background else android.R.drawable.checkbox_off_background,
//            null
//            )
//        )
    }

    override fun getItemCount(): Int = tasks.size


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tekst: TextView = itemView.findViewById(R.id.textView)
        val id: TextView = itemView.findViewById(R.id.textView3)
        val grupa: TextView = itemView.findViewById(R.id.textView2)
        val date: TextView = itemView.findViewById(R.id.date)
        val checkbox: MaterialCheckBox = itemView.findViewById(R.id.checkBox)
//        val complete: ImageView = itemView.findViewById(R.id.imageView)
    }
}