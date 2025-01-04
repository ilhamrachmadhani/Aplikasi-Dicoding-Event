package com.dicoding.submissiondicodingevent.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dicoding.submissiondicodingevent.databinding.ActivityEventDetailBinding
import androidx.core.text.HtmlCompat
import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.dicoding.submissiondicodingevent.R
import com.dicoding.submissiondicodingevent.data.local.entity.EventEntity
import com.dicoding.submissiondicodingevent.ui.favorite.FavoriteViewModel

class EventDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEventDetailBinding
    private val eventDetailViewModel: EventDetailViewModel by viewModels()
    private lateinit var favoriteViewModel: FavoriteViewModel // Deklarasi favoriteViewModel

    private var isFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        favoriteViewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)

        val eventId = intent.getIntExtra("EVENT_ID", -1)
        if (eventId != -1) {
            eventDetailViewModel.getEventDetail(eventId)

            favoriteViewModel.getFavoriteById(eventId).observe(this) { favoriteEvent ->
                isFavorite = favoriteEvent != null
                updateFavoriteButton()
            }
        }
        // Atur tampilan dan klik listener pada tombol favorit
        binding.ivBookmark.setOnClickListener {
            val event = eventDetailViewModel.eventDetail.value
            if (event != null) {
                if (isFavorite) {
                    // Hapus dari favorit
                    favoriteViewModel.removeFavorite(EventEntity(event.id ?: 0, event.name ?: "", event.mediaCover ?: ""))
                } else {
                    // Tambahkan ke favorit
                    favoriteViewModel.addFavorite(EventEntity(event.id ?: 0, event.name ?: "", event.mediaCover ?: ""))
                }
                isFavorite = !isFavorite // Ubah status favorit
                updateFavoriteButton() // Perbarui tampilan tombol
            }
        }

        eventDetailViewModel.eventDetail.observe(this) { event ->
            if (event != null) {
                binding.tvEventName.text = event.name
                binding.tvEventOwner.text = event.ownerName
                binding.tvEventTime.text = event.beginTime
                binding.tvEventQuota.text =
                    "${event.quota?.minus(event.registrants ?: 0)} remaining"

                // Load image
                Glide.with(this)
                    .load(event.imageLogo ?: event.mediaCover)
                    .into(binding.imgEventLogo)

                // Load description with HTML
                binding.tvEventDescription.text = HtmlCompat.fromHtml(
                    event.description ?: "", HtmlCompat.FROM_HTML_MODE_LEGACY
                )

                // Set link to open in browser
                binding.btnOpenLink.setOnClickListener {
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(event.link))
                    startActivity(browserIntent)
                }

            }

            eventDetailViewModel.isLoading.observe(this) { isLoading ->
                binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            }
        }
    }
    private fun updateFavoriteButton() {
        // Ubah ikon tombol favorit berdasarkan status favorit
        if (isFavorite) {
            binding.ivBookmark.setImageResource(R.drawable.baseline_favorite_24) // Ganti dengan ikon favorit
        } else {
            binding.ivBookmark.setImageResource(R.drawable.baseline_favorite_border_24) // Ganti dengan ikon tidak favorit
        }
    }
}
