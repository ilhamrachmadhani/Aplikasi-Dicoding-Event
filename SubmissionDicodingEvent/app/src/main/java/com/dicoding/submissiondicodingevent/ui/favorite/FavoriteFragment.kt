package com.dicoding.submissiondicodingevent.ui.favorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submissiondicodingevent.databinding.FragmentFavoriteBinding
import com.dicoding.submissiondicodingevent.ui.detail.EventDetailActivity

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private val favoriteViewModel: FavoriteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = FavoriteEventAdapter { event ->
            // Mulai EventDetailActivity dan kirimkan EVENT_ID
            val intent = Intent(requireContext(), EventDetailActivity::class.java).apply {
                putExtra("EVENT_ID", event.id) // Mengirim id event yang dipilih
            }
            startActivity(intent)
        }


        binding.rvFavoriteEvents.layoutManager = LinearLayoutManager(requireContext())
        binding.rvFavoriteEvents.adapter = adapter

        favoriteViewModel.allFavorites.observe(viewLifecycleOwner) { favoriteEvents ->
            adapter.submitList(favoriteEvents)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
