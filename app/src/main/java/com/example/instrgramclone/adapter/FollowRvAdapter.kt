package com.example.instrgramclone.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instrgramclone.Models.User
import com.example.instrgramclone.R
import com.example.instrgramclone.databinding.FollowRvBinding

class FollowRvAdapter(var context: Context,var folowList:ArrayList<User>):RecyclerView.Adapter<FollowRvAdapter.ViewHolder>() {
    inner class ViewHolder(var binding:FollowRvBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding=FollowRvBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return folowList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).load(folowList.get(position).image).placeholder(R.drawable.user).into(holder.binding.profileImage)
        holder.binding.name.text=folowList.get(position).name

    }
}