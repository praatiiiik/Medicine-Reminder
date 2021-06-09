package com.example.medicinereminder

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapter(private val context: Context , private val listener : RclrClicked) : RecyclerView.Adapter<RecyclerViewAdapter.noteViewHolder>() {

    private val allNotes = ArrayList<UserEntity>()

    inner class noteViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val textView1 = itemView.findViewById<TextView>(R.id.itemMedicineName)
        val textView2 = itemView.findViewById<TextView>(R.id.itemTime)
        val deleteButton = itemView.findViewById<ImageButton>(R.id.delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): noteViewHolder {
        val viewHolder = noteViewHolder(LayoutInflater.from(context).inflate(R.layout.layout,parent,false))
        viewHolder.deleteButton.setOnClickListener{
            //delete the medicine
            listener.itemClicked(allNotes[viewHolder.absoluteAdapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: noteViewHolder, position: Int) {

        val currentNotes = allNotes[position]
        holder.textView1.text = currentNotes.name
        holder.textView2.text = currentNotes.dose.toString()
    }

    override fun getItemCount(): Int {
        return allNotes.size
    }

    fun updateList(newList: List<UserEntity>){
        allNotes.clear()
        allNotes.addAll(newList)

        notifyDataSetChanged()
    }
}

interface RclrClicked {
    fun itemClicked(data:UserEntity)
}