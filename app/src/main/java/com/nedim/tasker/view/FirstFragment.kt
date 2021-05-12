package com.nedim.tasker.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Timestamp
import com.nedim.tasker.R
import com.nedim.tasker.model.Task
import com.nedim.tasker.viewmodel.TaskViewModel
import com.nedim.tasker.viewmodel.TaskViewModel.Companion.mapaTaskDocumentId
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {
    lateinit var mRecyclerView : RecyclerView
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mRecyclerView = view.findViewById(R.id.mRecyclerView)
        mRecyclerView.layoutManager = GridLayoutManager(
            context,
            1
        )

        CoroutineScope(IO).launch {
            getList()
        }


    }

    private suspend fun getList() {
        val list = TaskViewModel.getAllFromDB()
        val returnLista : MutableList<Task> = mutableListOf()
            for (document in list) {
                Log.d("TAGIC", document.id + " => " + document.data)
                val temp = Task(
                        document.data?.get("tekst").toString(),
                        document.data?.get("category").toString(),
                        document.data?.get("completed") as Boolean,
                        (document.data?.get("id") as Long).toInt(),
                        Date((document.data?.get("date") as Timestamp).seconds)
                )
                mapaTaskDocumentId[temp] = document.id
                Log.d("TAGIC", "temp je $temp")
                returnLista.add(temp)
            }
        withContext (Main) {
            mRecyclerView.adapter = TaskRecyclerAdapter(returnLista.sortedBy {
                it.id
            })
        }
    }
}