package com.dicoding.submissiondicodingevent.ui.finished

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.submissiondicodingevent.data.remote.event.EventDetail
import com.dicoding.submissiondicodingevent.databinding.ItemEventFinishedBinding
import com.dicoding.submissiondicodingevent.ui.detail.EventDetailActivity

class FinishedEventAdapter : RecyclerView.Adapter<FinishedEventAdapter.FinishedEventViewHolder>() {

    private var eventList: List<EventDetail> = emptyList()

    fun setData(events: List<EventDetail>) {
        this.eventList = events
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FinishedEventViewHolder {
        val binding = ItemEventFinishedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FinishedEventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FinishedEventViewHolder, position: Int) {
        val event = eventList[position]
        holder.bind(event)

    }

    override fun getItemCount(): Int = eventList.size

    inner class FinishedEventViewHolder(private val binding: ItemEventFinishedBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(event: EventDetail) {
            binding.tvEventName.text = event.name
            Glide.with(binding.imgEventLogo.context)
                .load(event.imageLogo)
                .into(binding.imgEventLogo)

            itemView.setOnClickListener {
                val context = itemView.context
                val intent = Intent(context, EventDetailActivity::class.java)

                intent.putExtra("EVENT_ID", event.id)
                context.startActivity(intent)
            }
        }
    }
}
