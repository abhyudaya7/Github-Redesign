package com.abhyudaya.githubredesign.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.abhyudaya.githubredesign.R
import com.abhyudaya.githubredesign.data.ContributorData
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class ContributorViewAdapter(val context: Context, val contributorList: List<ContributorData>)
    :RecyclerView.Adapter<ContributorViewAdapter.ViewHolder>() {

    lateinit var mListener: onItemClickListener

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        mListener = listener
    }

        class ViewHolder(itemView: View, listener: onItemClickListener): RecyclerView.ViewHolder(itemView) {
            var contributorName: TextView = itemView.findViewById(R.id.contributor_name)
            var contributorImage: CircleImageView = itemView.findViewById(R.id.contributor_profile_image)
            var contributionNumber: TextView = itemView.findViewById(R.id.contributors_number)

            init {
                itemView.setOnClickListener {
                    listener.onItemClick(adapterPosition)
                }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView = LayoutInflater.from(context).inflate(R.layout.contributor_layout, parent, false)
        return ViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.contributorName.text = contributorList[position].login
        Picasso.get().load(contributorList[position].avatar_url).into(holder.contributorImage)
        holder.contributionNumber.text = "${contributorList[position].contributions.toString()} contributions"
    }

    override fun getItemCount(): Int {
        return contributorList.size
    }
}