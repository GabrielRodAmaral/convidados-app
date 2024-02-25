package com.example.convidadosapp.repository

import android.content.Context

import com.example.convidadosapp.model.GuestModel

class GuestRepository(context: Context) {

    private val guestDataBase = GuestDataBase.getDataBase(context).guestDAO()

    fun insert(guest: GuestModel): Boolean {
        return guestDataBase.insert(guest) > 0
    }

    fun update(guest: GuestModel): Boolean {
        return guestDataBase.update(guest) > 0
    }

    fun delete(guestId: Int) {
        val guest = get(guestId)
        guestDataBase.delete(guest)
    }

    fun getAll(): List<GuestModel> {
        return guestDataBase.getAll()
    }

    fun get(guestId: Int): GuestModel {
        return guestDataBase.get(guestId)
    }

    fun getPresents(): List<GuestModel> {
        return guestDataBase.getPresents()
    }

    fun getAbsents(): List<GuestModel> {
        return guestDataBase.getAbsents()
    }
}