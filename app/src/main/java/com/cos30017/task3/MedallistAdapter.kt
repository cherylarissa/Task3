package com.cos30017.task3

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cos30017.task3.databinding.MedallistitemBinding

class MedallistAdapter(
    private val teams: List<Medallist>,
    private val top10Countries: Set<String>, // Top 10 countries by name
    private val onItemShortPressed: (Medallist) -> Unit,
    private val onItemLongPressed: (Medallist) -> Unit
) : RecyclerView.Adapter<MedallistAdapter.MedallistViewHolder>() {

    class MedallistViewHolder(val binding: MedallistitemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedallistViewHolder {
        val binding = MedallistitemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MedallistViewHolder(binding)
    }

    override fun getItemCount(): Int = teams.size

    override fun onBindViewHolder(holder: MedallistViewHolder, position: Int) {
        val medallist = teams[position]

        // Bind data to the views
        holder.binding.apply {
            textViewCountryName.text = medallist.country
            textViewContryCode.text = medallist.iocCode
            textViewTimesCom.text = medallist.timesCompeted.toString()

            // Highlight top 10 countries with stabilo yellow background
            if (medallist.country in top10Countries) {
                root.setBackgroundColor(Color.parseColor("#FFFF00")) // Stabilo yellow
            } else {
                root.setBackgroundColor(Color.TRANSPARENT) // Default background
            }

            // Set short-press listener
            root.setOnClickListener {
                onItemShortPressed(medallist)
            }

            // Set long-press listener
            root.setOnLongClickListener {
                onItemLongPressed(medallist)
                true
            }
        }
    }
}
