package com.example.android_bleed.reminder.view

import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android_bleed.R
import com.example.android_bleed.data.models.Reminder

class ReminderAdapter : RecyclerView.Adapter<ReminderAdapter.ReminderViewHolder>() {

    private val mReminderList: ArrayList<Reminder> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReminderViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_reminder, parent, false)
        return ReminderViewHolder(itemView)
    }

    override fun getItemCount(): Int = mReminderList.size

    override fun onBindViewHolder(holder: ReminderViewHolder, position: Int) {
        holder.bind(mReminderList[position])
    }

    fun setReminderList(reminderList: List<Reminder>) {
        mReminderList.clear()
        mReminderList.addAll(reminderList)
        notifyDataSetChanged()
    }

    class ReminderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvReminderMessage: TextView = itemView.findViewById(R.id.tv_reminder_message_item_reminder)
        private val tvReminderDate: TextView = itemView.findViewById(R.id.tv_reminder_date_item_reminder)

        fun bind(reminder: Reminder) {
            tvReminderMessage.text = reminder.reminderMessage
            tvReminderDate.text = reminder.reminderDate
        }
    }
}