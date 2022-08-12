package com.example.sliideusersbook.userslist.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sliideusersbook.databinding.UserAdapterItemBinding
import com.example.sliideusersbook.userslist.model.User

class UsersListAdapter (
    private val usersList: List<User>,
    private val onOnLongClickHandler: (User) -> Unit
) : RecyclerView.Adapter<UsersListAdapter.UsersListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersListViewHolder {
        val binding = UserAdapterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UsersListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UsersListViewHolder, position: Int) {
        holder.bind(usersList[position])
    }

    override fun getItemCount() = usersList.size

    inner class UsersListViewHolder(private val binding: UserAdapterItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.userNameTextView.text = user.name
            binding.userEmailTextView.text = user.email

            binding.root.setOnLongClickListener {
                onOnLongClickHandler(user)
                true
            }
        }
    }
}