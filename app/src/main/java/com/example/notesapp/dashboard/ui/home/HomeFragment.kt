package com.example.notesapp.dashboard.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notesapp.R
import com.example.notesapp.dashboard.DashboardClickListener
import com.example.notesapp.database.DBActivityListener
import com.example.notesapp.database.NotesDBViewModel
import com.example.notesapp.database.dbmodel.NotesDBModel
import com.example.notesapp.model.NotesDataModel
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.ArrayList

class HomeFragment : Fragment(), DashboardClickListener, DBActivityListener {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var notesDBViewModel: NotesDBViewModel
    private var mNotesDataModel: ArrayList<NotesDataModel> = arrayListOf()
    private var mAdapter: DashboardAdapter? = null
    private var isUpdated: Boolean = false

    val noteList = arrayListOf<NotesDataModel>(NotesDataModel("1", "Mathematics I", "Sir I", "3.5", "150", false, false),
        NotesDataModel("2", "Engg Mechanics", "Sir II", "4", "250", false, false),
        NotesDataModel("3", "Basic Electrical Engg", "Sir III", "3.0", "350", false, false),
        NotesDataModel("4", "English", "Sir IV", "2.5", "150", false, false),
        NotesDataModel("5", "Basic Mechanical Engg", "Sir V", "4.0", "100", false, false),
        NotesDataModel("6", "Engg Drawing", "Sir VI", "3.5", "300", false, false),
        NotesDataModel("7", "Signal and Systems", "Sir VII", "3.0", "275", false, false),
        NotesDataModel("8", "Microprocessor I", "Sir VII", "3.5", "240", false, false),
        NotesDataModel("9", "Engg Drawing", "Sir IX", "4.0", "150", false, false),
        NotesDataModel("10", "Basic Civil Engg", "Sir X", "4.5", "400", false, false))

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        notesDBViewModel = ViewModelProviders.of(this).get(NotesDBViewModel::class.java)
        getBookMarkIds()
    }

    private fun setAdapter(notesDataModel: ArrayList<NotesDataModel>) {
        val layoutManager = LinearLayoutManager(activity)
        rv_dashboard!!.layoutManager = layoutManager
        mAdapter = DashboardAdapter(this.activity!!, this, notesDataModel)
        rv_dashboard!!.adapter = mAdapter
    }

    override fun bookmarkCheckBoxClicked(isCheck: Boolean, id: String) {
        notesDBViewModel.updateBookMarkNotes(id, isCheck)
        isUpdated = true
    }

    override fun deleteBookMark(id: String) {}

    override fun getBookMarkIdUpdate(ids: List<String>) {
        Log.d("HomeFragment" , "Notes Size : " + ids.size)
        if (ids.isNotEmpty()) {
            for (notes in noteList) {
                for (id in ids) {
                    if (id == notes.id) {
                        notes.bookMarked = true
                        Log.d("HomeFragment" , "Notes Id bookMarked : " + noteList.get(1).bookMarked)
                    }
                }
            }
        }
        insertNotes()
    }

    override fun insertAllNotesUpdate() {

    }

    private fun getBookMarkIds() {
        notesDBViewModel.getBookMarkIds(true, this)
    }

    private fun insertNotes() {
        for (notes in noteList) {
            Log.d("HomeFragment" , "Notes : " + notes.bookMarked)
            notesDBViewModel.insertAllNotes(notes, this)
        }
        getAllNotes()
    }

    private fun getAllNotes() {
        notesDBViewModel.getAllNotes().observe(this, Observer<List<NotesDBModel>> {
            mNotesDataModel.clear()
            it.mapTo(mNotesDataModel) { t ->
                NotesDataModel(
                    t.id, t.subjectName, t.author, t.rating, t.price, t.bookMarked, t.addedToCart
                )
            }
            Log.d("HomeFragment" , "Is Updated : " + isUpdated)
            if (!isUpdated) {
                setAdapter(mNotesDataModel)
            }
            isUpdated = false
        })
    }
}