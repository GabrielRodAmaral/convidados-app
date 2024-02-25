package com.example.convidadosapp.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.convidadosapp.databinding.FragmentAllGuestsBinding
import com.example.convidadosapp.view.adapter.GuestsAdapter
import com.example.convidadosapp.view.listener.OnGuestListener
import com.example.convidadosapp.viewModel.AllGuestsViewModel

class AllGuestsFragment : Fragment() {

    private var _binding: FragmentAllGuestsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AllGuestsViewModel
    private val adapter = GuestsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, b: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(AllGuestsViewModel::class.java)

        _binding = FragmentAllGuestsBinding.inflate(inflater, container, false)

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