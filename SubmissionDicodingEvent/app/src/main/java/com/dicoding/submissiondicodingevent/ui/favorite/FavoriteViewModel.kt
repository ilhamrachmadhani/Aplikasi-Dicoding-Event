package com.dicoding.submissiondicodingevent.ui.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.submissiondicodingevent.data.EventRepository
import com.dicoding.submissiondicodingevent.data.local.database.EventDatabase
import com.dicoding.submissiondicodingevent.data.local.entity.EventEntity
import kotlinx.coroutines.launch

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: EventRepository
    val allFavorites: LiveData<List<EventEntity>>

    init {
        val dao = EventDatabase.getDatabase(application).eventDao()
        repository = EventRepository(dao)
        allFavorites = repository.allFavorites
    }

    fun addFavorite(event: EventEntity) = viewModelScope.launch {
        repository.addFavorite(event)
    }

    fun removeFavorite(event: EventEntity) = viewModelScope.launch {
        repository.removeFavorite(event)
    }

    fun getFavoriteById(id: Int): LiveData<EventEntity?> {
        return repository.getFavoriteById(id)
    }
}
