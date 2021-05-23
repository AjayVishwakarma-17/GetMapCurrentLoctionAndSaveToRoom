package com.example.mylocationtracking.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "info_table")
data class Info(
    var startTimeStamp: String,
    var lat: String,
    var lon: String,
    var stopTimeStamp: String
):Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}