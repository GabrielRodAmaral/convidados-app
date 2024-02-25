package com.example.convidadosapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.convidadosapp.model.GuestModel
import com.example.convidadosapp.repository.GuestRepository

class GuestFormViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = GuestRepository(application.applicationContext)

    private val guestModel = MutableLiveData<GuestModel>()
    val guest: LiveData<GuestModel> = guestModel

    private val mutableSaveGuest = MutableLiveData<Boolean>()
    val saveGuest: LiveData<Boolean> = mutableSaveGuest

    fun save(guestModel: GuestModel) {
        if (guestModel.id == 0) {
            mutableSaveGuest.value = repository.insert(guestModel)
        } else {
            mutableSaveGuest.value = repository.update(guestModel)
        }
    }

    fun get(guestId: Int) {
        guestModel.value = repository.get(guestId)
    }
}