package com.bhaicompany.kotlinarchitecturalcomponents

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import kotlinx.android.synthetic.main.single_item.view.*

class UserAdapter: PagedListAdapter<User, UserAdapter.UserItemViewHolder>(object : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User?, newItem: User?): Boolean {
        return oldItem?.uid == newItem?.uid
    }

    override fun areContentsTheSame(oldItem: User?, newItem: User?): Boolean {
        return oldItem == newItem
    }
})
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserItemViewHolder =
            UserItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.single_item, parent, false))

    override fun onBindViewHolder(holder: UserItemViewHolder, position: Int) {
        val user = getItem(position)
        user?.let { holder.bindTo(it) }
    }

    class UserItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        fun bindTo(user: User) = with(itemView)
        {
            name.text = user.name
            uid.text = user.uid.toString()
            gender.text = user.gender
        }
    }
}