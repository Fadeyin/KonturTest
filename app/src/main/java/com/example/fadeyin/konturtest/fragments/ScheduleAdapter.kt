package com.example.fadeyin.konturtest.fragments

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.fadeyin.konturtest.R
import com.example.fadeyin.konturtest.models.User
import java.util.*

import kotlin.collections.ArrayList



open class ScheduleAdapter(private val clickListener: (User) -> Unit) : androidx.recyclerview.widget.RecyclerView.Adapter<ScheduleAdapter.ViewHolder>(){

    private var userList:ArrayList<User> = ArrayList()
    private var userListFiltered:ArrayList<User> = ArrayList()
    fun setUserList(userList:ArrayList<User>){
        this.userList = userList
        this.userListFiltered = userList
        notifyDataSetChanged()
    }
    @SuppressLint("CheckResult")
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(p0.context).inflate(R.layout.user_item, p0, false)
        return ViewHolder(v)
    }
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(userList[position],clickListener)
    }
    override fun getItemCount(): Int {
        return userList.size
    }
    class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        private val height = itemView.findViewById<TextView>(R.id.height)
        private val name = itemView.findViewById<TextView>(R.id.name)
        private val phone = itemView.findViewById<TextView>(R.id.phone)
        
        fun bind(User:User, clickListener: (User) -> Unit){
            name?.text = User.name
            height?.text = User.height.toString()
            phone?.text = User.phone
            itemView.setOnClickListener { clickListener(User)}
        }

    }
    // Filter Class
    fun filter(charText: String) {
        val charsText = charText
        charsText.toLowerCase(Locale.getDefault())

        ScheduleFragment.searchArrayList.clear()
        if (charsText.isEmpty()) {
            ScheduleFragment.searchArrayList.addAll(userList)
        } else {
            for (wp in userList) {
                if (wp.getSearchName().toLowerCase(Locale.getDefault()).contains(charsText)) {
                    ScheduleFragment.searchArrayList.add(wp)
                }
                if (wp.getSearchPhone().toLowerCase(Locale.getDefault()).contains(charsText)) {
                    ScheduleFragment.searchArrayList.add(wp)
                }
            }
        }
        notifyDataSetChanged()
    }
}

