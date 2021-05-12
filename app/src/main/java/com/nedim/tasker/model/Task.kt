package com.nedim.tasker.model

import java.util.*

data class Task(
        val text: String,
        val grupa: String?,
        var completed: Boolean,
        val id: Int,
        val date: Date?)

{
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Task

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id
    }
}
