package com.dicoding.submissiondicodingevent.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dicoding.submissiondicodingevent.data.local.entity.EventEntity

@Dao
interface EventDao {
    @Query("SELECT * FROM favorite_events WHERE id = :id")
    fun getFavoriteEventById(id: Int): LiveData<EventEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(event: EventEntity)

    @Delete
    suspend fun removeFavorite(event: EventEntity)

    @Query("SELECT * FROM favorite_events")
    fun getAllFavorites(): LiveData<List<EventEntity>>
}
