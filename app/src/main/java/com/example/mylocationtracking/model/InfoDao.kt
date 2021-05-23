package com.example.mylocationtracking.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface InfoDao {

    @Insert
    fun insertInfo(info: Info)

    @Query("select * from info_table ORDER BY id DESC")
    fun getAllInfos(): LiveData<List<Info>>
}