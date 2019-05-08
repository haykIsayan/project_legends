package com.example.android_bleed.note.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android_bleed.R
import com.example.android_bleed.editing.CreateNoteFlow
import com.example.android_bleed.data.models.Note
import com.example.android_bleed.flow.AndroidFlow
import com.example.android_bleed.flow.FlowResource
import com.example.android_bleed.flow.view.FlowFragment
import com.example.android_bleed.note.NoteListFlow
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlin.collections.ArrayList


class NoteListFragment : FlowFragment() {
    override fun getLayoutResource(): Int = R.layout.fragment_note_list

    private lateinit var mNoteListFlow: AndroidFlow

    private lateinit var etNoteText: EditText
    private lateinit var etNoteTitle: EditText

    private lateinit var fabAddNote: FloatingActionButton
    private lateinit var rvNoteList: RecyclerView

    private lateinit var mNoteListAdapter: NoteListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        getFlowData().observe(this, Observer {

            when (it.status) {
                FlowResource.Status.FAILED -> {
                    Toast.makeText(activity, "THIS BITCH EMPTY", Toast.LENGTH_SHORT).show()
                }
                FlowResource.Status.COMPLETED -> {
                    val noteList: ArrayList<Note> = it.bundle.getParcelableArrayList(Note.EXTRA_NOTE_LIST) ?: return@Observer

                    Toast.makeText(activity, noteList.size.toString(), Toast.LENGTH_SHORT).show()
                    mNoteListAdapter.setNoteList(noteList)
                }
                FlowResource.Status.PENDING -> {
                    Toast.makeText(activity, "LOADING", Toast.LENGTH_SHORT).show()
                }
            }
        })

        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        this.mNoteListFlow = getFlowByName(NoteListFlow::class.java.name)?:return
//        registerFlow(mNoteListFlow)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_note_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        fabAddNote = view.findViewById(R.id.fab_add_note_fragment_note_list)
        rvNoteList = view.findViewById(R.id.rv_note_list_fragment_note_list)
        mNoteListAdapter = NoteListAdapter(ArrayList())
        rvNoteList.adapter = mNoteListAdapter
        rvNoteList.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)

        fabAddNote.setOnClickListener {
            executeFlow(mNoteListFlow, NoteListFlow.ACTION_LAUNCH_CREATE_FLOW)
        }

        super.onViewCreated(view, savedInstanceState)
    }
}