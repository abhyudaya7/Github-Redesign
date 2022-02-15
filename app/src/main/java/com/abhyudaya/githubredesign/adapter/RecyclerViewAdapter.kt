package com.abhyudaya.githubredesign.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.abhyudaya.githubredesign.R
import com.abhyudaya.githubredesign.data.ReposData

class RecyclerViewAdapter(val context: Context, val repoList: List<ReposData>)
    :RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var repoName: TextView
        var repoDescription: TextView
        var language: TextView
        var starCount: TextView
        var forkCount: TextView

        init {
            repoName = itemView.findViewById(R.id.repo_name)
            repoDescription = itemView.findViewById(R.id.repo_description)
            language = itemView.findViewById(R.id.language)
            starCount = itemView.findViewById(R.id.star_count)
            forkCount = itemView.findViewById(R.id.fork_count)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView = LayoutInflater.from(context).inflate(R.layout.repo_layout, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.repoName.text = repoList[position].name
        holder.repoDescription.text = repoList[position].description
        holder.language.text = repoList[position].language
        holder.starCount.text = repoList[position].stargazers_count.toString()
        holder.forkCount.text = repoList[position].forks_count.toString()
    }

    override fun getItemCount(): Int {
        return repoList.size
    }
}