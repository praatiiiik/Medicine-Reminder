package com.example.medicinereminder

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewModell (application: Application) : AndroidViewModel(application) {

    val allNotes: LiveData<List<UserEntity>>
    private val repositiory : Repositiory

    init {
        val dao = UserDatabase.getDatabase(application).getUserDao()
        repositiory = Repositiory(dao)
        allNotes = repositiory.allData
    }

    fun deleteNote(note : UserEntity) = viewModelScope.launch(Dispatchers.IO) {

        repositiory.delete(note)
    }

    fun insertNote(note : UserEntity) = viewModelScope.launch(Dispatchers.IO) {

        repositiory.insert(note)
    }
}