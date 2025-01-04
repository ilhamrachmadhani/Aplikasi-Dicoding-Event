package com.dicoding.submissiondicodingevent.data

import androidx.lifecycle.LiveData
import com.dicoding.submissiondicodingevent.data.local.dao.EventDao
import com.dicoding.submissiondicodingevent.data.local.entity.EventEntity


class EventRepository(private val dao: EventDao) {
    val allFavorites: LiveData<List<EventEntity>> = dao.getAllFavorites()

    suspend fun addFavorite(event: EventEntity) {
        dao.addFavorite(event)
    }

    suspend fun removeFavorite(event: EventEntity) {
        dao.removeFavorite(event)
    }

    fun getFavoriteById(id: Int): LiveData<EventEntity?> {
        return dao.getFavoriteEventById(id)
    }
}