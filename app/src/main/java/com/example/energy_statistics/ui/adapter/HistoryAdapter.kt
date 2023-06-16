package com.example.energy_statistics.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.energy_statistics.data.HistoryResponse
import com.example.energy_statistics.databinding.ItemHistoryBinding

class HistoryAdapter(var datas: List<HistoryResponse>): RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {
    inner class HistoryViewHolder(val binding: ItemHistoryBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.binding.txtDate.text = datas[position].dateReport
        holder.binding.txtElectric.text = "Electric used: ${datas[position].electricUsed}kW"
        holder.binding.txtWater.text = "Water used: ${datas[position].waterUsed}kW"
        holder.binding.txtTemp.text = "Temperature: ${datas[position].temperature}°C"
    }

    public fun updateData(datas: List<HistoryResponse>){
        this.datas = datas
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = datas.size
}