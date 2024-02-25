package com.example.convidadosapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.convidadosapp.R
import com.example.convidadosapp.databinding.ActivityGuestFormBinding
import com.example.convidadosapp.model.GuestModel
import com.example.convidadosapp.viewModel.GuestFormViewModel

class GuestFormActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityGuestFormBinding
    private lateinit var viewModel: GuestFormViewModel
    private var guestId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGuestFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(GuestFormViewModel::class.java)

        binding.buttonSave.setOnClickListener(this)
        binding.radioPresent.isChecked = true

        observe()
        loadData()
    }

    override fun onClick(v: View) {
        if (v.id == R.id.button_save) {
            val name = binding.editName.getText().toString()
            val presence = binding.radioPresent.isChecked

            val model = GuestModel()
            model.id = guestId
            model.name = name
            model.presence = presence
            viewModel.save(model)
        }
    }

    private fun observe() {
        viewModel.guest.observe(this, Observer {
            binding.editName.setText(it.name)
            if (it.presence) {
                binding.radioPresent.isChecked = true
            } else {
                binding.radioAbsent.isChecked = true
            }
        })

        viewModel.saveGuest.observe(this, Observer {
            if (it) {
                if (guestId == 0) {
                    Toast.makeText(this, getString(R.string.insert_guest_success), Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, getString(R.string.update_guest_success), Toast.LENGTH_SHORT).show()
                }
                finish()
            } else {
                if (guestId == 0) {
                    Toast.makeText(this, getString(R.string.insert_guest_error), Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, getString(R.string.update_guest_error), Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun loadData() {
        val bundle = intent.extras
        if (bundle != null) {
            guestId = bundle.getInt("guestId")
            viewModel.get(guestId)
        }
    }
}