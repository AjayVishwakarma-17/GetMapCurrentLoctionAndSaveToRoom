package com.example.mylocationtracking.model

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Info::class], version = 1)
abstract class InfoDatabase : RoomDatabase() {

    abstract fun infoDao(): InfoDao

    companion object {

        private var instance: InfoDatabase? = null
        fun getInstance(context: Context): InfoDatabase? {

            if (instance == null) {
                synchronized(InfoDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        InfoDatabase::class.java, "location_database"
                    )
                        .fallbackToDestructiveMigration()
                        .addCallback(roomCallBack)
                        .build()
                }
            }
            return instance
        }
        //synchronized : only one thread can access this method
        //fallbacktodestructivemigration : to handle versions
        private val roomCallBack: Callback = object : Callback() {
            val populateDbAsyncTask = PopulateDbAsyncTask(instance).execute()
        }
    }

    /*//synchronized : only one thread can access this method
    //fallbacktodestructivemigration : to handle versions
    private val roomCallBack: Callback = object : Callback() {
        // new PopulateDb(instance).execute();
    }*/

    class PopulateDbAsyncTask(db: InfoDatabase?) : AsyncTask<Unit, Unit, Unit>() {
        private val infoDao = db?.infoDao()

        override fun doInBackground(vararg p0: Unit?) {
            /*infoDao?.insertInfo(Info("Title 1", "description 1"))
            infoDao?.insertInfo(Info("Title 2", "description 2"))
            infoDao?.insertInfo(Info("Title 3", "description 3"))*/
            println("PopulateDbAsyncTask calling from infoDatabase!!")
        }
    }

}