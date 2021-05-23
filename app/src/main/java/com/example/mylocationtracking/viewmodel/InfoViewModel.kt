package com.example.mylocationtracking.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.mylocationtracking.model.Info
import com.example.mylocationtracking.model.InfoRepository

class InfoViewModel(application: Application): AndroidViewModel(application) {

    private var repository: InfoRepository = InfoRepository(application)
    private var allInfos: LiveData<List<Info>> = repository.getAllInfos()

    fun insert(info: Info) {
        repository.insert(info)
    }

    fun getAllInfos(): LiveData<List<Info>> {
        return allInfos
    }
}