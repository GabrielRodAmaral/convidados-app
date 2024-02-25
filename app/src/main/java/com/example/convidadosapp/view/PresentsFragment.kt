package com.example.convidadosapp.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.convidadosapp.databinding.FragmentPresentsBinding
import com.example.convidadosapp.view.adapter.GuestsAdapter
import com.example.convidadosapp.view.listener.OnGuestListener
import com.example.convidadosapp.viewModel.AbsentsViewModel
import com.example.convidadosapp.viewModel.PresentsViewModel

class PresentsFragment : Fragment() {

    private var _binding: FragmentPresentsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: PresentsViewModel
    private val adapter = GuestsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, b: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(PresentsViewModel::class.java)
        _binding = FragmentPresentsBinding.inflate(inflater, container, false)

        binding.recyclerGuests.layoutManager = LinearLayoutManager(context)
        binding.recyclerGuests.adapter = adapter

        val listener = object: OnGuestListener {
            override fun onClick(guestId: Int) {
                val bundle = Bundle()
                bundle.putInt("guestId", guestId)
                startActivity(Intent(context, GuestFormActivity::class.java).putExtras(bundle))
            }

            override fun onDelete(guestId: Int) {
                viewModel.delete(guestId)
                viewModel.getAll()
            }

        }

        observe()
        adapter.atachListener(listener)
        viewModel.getAll()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAll()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observe() {
        viewModel.guests.observe(viewLifecycleOwner) {
            adapter.updateGuests(it)
        }
    }
}