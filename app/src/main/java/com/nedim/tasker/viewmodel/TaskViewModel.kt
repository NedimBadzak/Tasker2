package com.nedim.tasker.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.nedim.tasker.model.Task
import kotlinx.coroutines.tasks.await
import java.util.*


class TaskViewModel : ViewModel() {
    var db = FirebaseFirestore.getInstance()
    fun doTask(task: Task, completed : Boolean) {
        task.completed = true
        changeInDB(mapOf(Pair("completed", completed)
        ), mapaTaskDocumentId[task].toString())
    }

    private fun changeInDB(mapa: Map<String, Any>, documentId: String) {
        db.collection("tasks").document(documentId)
                .update(mapa)
                .addOnSuccessListener { documentReference -> Log.d("TAGIC", "DocumentSnapshot added with ID: $documentReference") }
                .addOnFailureListener { e -> Log.w("TAGIC", "Error adding document", e) }
    }


    companion object {
        var mapaTaskDocumentId: MutableMap<Task, String> = mutableMapOf()

        suspend fun getAllFromDB(): MutableList<DocumentSnapshot> {
            var returnLista : MutableList<Task> = mutableListOf()
            val snapshot = FirebaseFirestore.getInstance().collection("tasks")
                    .get().await()
            return snapshot.documents
        }
    }
}