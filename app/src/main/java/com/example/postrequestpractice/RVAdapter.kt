package com.example.postrequestpractice

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.rv_row.view.*

class RVAdapter (private var users: ArrayList<dataItem>): RecyclerView.Adapter<RVAdapter.ItemViewHolder>() {
    class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.rv_row,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val user = users[position]

        holder.itemView.apply {
            rowName.text = user.name
            rowLocation.text = user.location

            holder.itemView.setOnClickListener{
                val userData = dataItem(users[position].location, users[position].name, users[position].pk)
                val intent = Intent(holder.itemView.context,UpdateDeleteData::class.java)
                intent.putExtra("displayData",userData)
                holder.itemView.context.startActivity(intent)
            }
        }
    }

    override fun getItemCount() = users.size
}