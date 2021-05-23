package com.example.mylocationtracking.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mylocationtracking.model.Info
import com.example.mylocationtracking.R
import com.example.mylocationtracking.view.DetailsActivity

class InfoAdapter(applicationContext: Context) : RecyclerView.Adapter<InfoAdapter.InfoHolder>() {

    private val context = applicationContext
    private var infos: List<Info> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfoHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_row_layout, parent, false)
        return InfoHolder(itemView)
    }

    override fun onBindViewHolder(holder: InfoHolder, position: Int) {
        val currentInfo = infos[position]
        holder.tv_startTime.text = "Start time : " +currentInfo.startTimeStamp
        holder.tv_stopTime.text = "Stop time : " +currentInfo.stopTimeStamp
        holder.tv_latitude.text = "Latitude : " +currentInfo.lat
        holder.tv_longitude.text ="Longitude :" +currentInfo.lon

        holder.tv_startTime.setOnClickListener {
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra("currentInfo", currentInfo)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return infos.size
    }

    fun setInfos(infos: List<Info>) {
        this.infos = infos
        notifyDataSetChanged()
    }

    class InfoHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv_startTime: TextView = itemView.findViewById(R.id.tv_start_time)
        var tv_latitude: TextView = itemView.findViewById(R.id.tv_latitude)
        var tv_longitude: TextView = itemView.findViewById(R.id.tv_longitude)
        var tv_stopTime: TextView = itemView.findViewById(R.id.tv_stop_time)
    }

}