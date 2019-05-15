package com.example.android_bleed.reminder.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android_bleed.R
import com.example.android_bleed.android_legends.utilities.LegendResult
import com.example.android_bleed.android_legends.view.LegendsFragment
import com.example.android_bleed.editing.CreateReminderLegend
import com.example.android_bleed.reminder.domain.GetReminderListAction
import com.google.android.material.floatingactionbutton.FloatingActionButton


class ReminderListFragment : LegendsFragment() {
    override fun getLayoutResource(): Int = R.layout.fragment_reminder_list

    private lateinit var rvReminderList: RecyclerView
    private lateinit var fabAddReminder: FloatingActionButton

    private lateinit var mReminderAdapter: ReminderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mReminderAdapter = ReminderAdapter()

        getLegendData().observe(this, Observer {
            when (it) {
                is GetReminderListAction.GetRemindersResult -> {
                    Toast.makeText(activity, "${it.reminderList.size}", Toast.LENGTH_LONG).show()
                    mReminderAdapter.setReminderList(it.reminderList)
                }
                is LegendResult.FailResource -> {
                    Toast.makeText(activity, it.failMessage, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_reminder_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvReminderList = view.findViewById(R.id.rv_reminder_list_fragment_reminder_list)
        fabAddReminder = view.findViewById(R.id.fab_add_reminder_fragment_reminder_list)

        // SETUP RECYCLER VIEW
        rvReminderList.addItemDecoration(DividerItemDecoration(activity, RecyclerView.VERTICAL))
        rvReminderList.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        rvReminderList.adapter = mReminderAdapter

        // SETUP CLICK LISTENER
        fabAddReminder.setOnClickListener {
            startLegend(CreateReminderLegend::class)
        }
    }
}