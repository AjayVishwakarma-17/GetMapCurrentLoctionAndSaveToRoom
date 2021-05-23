package com.example.mylocationtracking.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mylocationtracking.adapter.InfoAdapter
import com.example.mylocationtracking.viewmodel.InfoViewModel
import com.example.mylocationtracking.R
import kotlinx.android.synthetic.main.activity_info_logs.*

class InfoLogsActivity : AppCompatActivity() {

    private lateinit var infoViewModel: InfoViewModel
    private lateinit var adapter: InfoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_logs)

        adapter = InfoAdapter(this)
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)
        recycler_view.adapter = adapter
        infoViewModel = ViewModelProvider.AndroidViewModelFactory
            .getInstance(application).create(InfoViewModel::class.java)

        infoViewModel.getAllInfos().observe(this,
            {
                    t -> adapter.setInfos(t!!)
            }
        )
    }
}