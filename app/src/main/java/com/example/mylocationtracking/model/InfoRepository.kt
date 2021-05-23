package com.example.mylocationtracking.model

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData

class InfoRepository(application: Application) {

    private lateinit var infoDao: InfoDao
    private var allInfos: LiveData<List<Info>>

    init {
        val database: InfoDatabase? = InfoDatabase.getInstance(application)
        if (database != null) {
            infoDao = database.infoDao()
        }
        allInfos = infoDao.getAllInfos()
    }

    fun insert(info: Info) {
        val insertInfoAsyncTask = InsertInfoAsyncTask(infoDao).execute(info)
    }

    fun getAllInfos(): LiveData<List<Info>> {
        return allInfos
    }

    private class InsertInfoAsyncTask(infoDao: InfoDao) : AsyncTask<Info, Unit, Unit>() {
        val infoDao = infoDao

        override fun doInBackground(vararg params: Info?) {
            infoDao.insertInfo(params[0]!!)
        }
    }
}