package com.abhyudaya.githubredesign.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.abhyudaya.githubredesign.R
import com.abhyudaya.githubredesign.data.ReposData
import com.abhyudaya.githubredesign.user_interface.MainActivity
import com.abhyudaya.githubredesign.user_interface.ProfileFragmentDirections
import com.abhyudaya.githubredesign.user_interface.RepoDisplayFragment
import com.abhyudaya.githubredesign.utils.Utils
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.theme.MaterialComponentsViewInflater

class RecyclerViewAdapter(val context: Context, val repoList: List<ReposData>)
    :RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    lateinit var mListener: onItemClickListener

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        mListener = listener
    }

    class ViewHolder(itemView: View, listener: onItemClickListener): RecyclerView.ViewHolder(itemView) {
        var repoName: TextView = itemView.findViewById(R.id.repo_name)
        var repoDescription: TextView = itemView.findViewById(R.id.repo_description)
        var language: TextView = itemView.findViewById(R.id.language)
        var starCount: TextView = itemView.findViewById(R.id.star_count)
        var forkCount: TextView = itemView.findViewById(R.id.fork_count)
        var lastUpdated: TextView = itemView.findViewById(R.id.last_updated_info)
        var chipGrp: ChipGroup = itemView.findViewById(R.id.chip_group)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView = LayoutInflater.from(context).inflate(R.layout.repo_layout, parent, false)
        return ViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.repoName.text = repoList[position].name
        holder.repoDescription.text = repoList[position].description
        holder.language.text = repoList[position].language
        holder.starCount.text = repoList[position].stargazers_count.toString()
        holder.forkCount.text = repoList[position].forks_count.toString()
        holder.lastUpdated.text = Utils().getLastUpdatedAt(repoList[position].updated_at)
        holder.chipGrp.removeAllViews()
        for (item in repoList[position].topics) {
            var chip = Chip(holder.chipGrp.context)
            chip.text = item
            chip.textSize = 10.0f
            chip.setChipBackgroundColorResource(R.color.buttonColor)
            holder.chipGrp.addView(chip)
        }
    }

    override fun getItemCount(): Int {
        return repoList.size
    }
}