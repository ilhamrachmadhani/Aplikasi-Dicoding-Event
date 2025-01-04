package com.dicoding.submissiondicodingevent.ui.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.submissiondicodingevent.data.local.entity.EventEntity
import com.dicoding.submissiondicodingevent.databinding.ItemEventBinding

class FavoriteEventAdapter(
    private val onItemClick: (EventEntity) -> Unit
) : ListAdapter<EventEntity, FavoriteEventAdapter.FavoriteEventViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteEventViewHolder {
        val binding = ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteEventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteEventViewHolder, position: Int) {
        val event = getItem(position)
        holder.bind(event)
    }

    inner class FavoriteEventViewHolder(private val binding: ItemEventBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(event: EventEntity) {
            binding.tvEventName.text = event.name
            Glide.with(binding.imgEventLogo.context).load(event.mediaCover).into(binding.imgEventLogo)

            binding.root.setOnClickListener {
                onItemClick(event)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<EventEntity>() {
        override fun areItemsTheSame(oldItem: EventEntity, newItem: EventEntity) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: EventEntity, newItem: EventEntity) = oldItem == newItem
    }
}
