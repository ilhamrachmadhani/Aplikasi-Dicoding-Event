package com.dicoding.submissiondicodingevent.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_events")
data class EventEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val mediaCover: String
)


