package com.example.notesapp.dashboard

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notesapp.R
import com.example.notesapp.base.BaseActivity
import com.example.notesapp.dashboard.ui.home.HomeViewModel
import com.example.notesapp.database.NotesDBViewModel
import com.example.notesapp.database.dbmodel.NotesDBModel
import com.example.notesapp.model.NotesDataModel
import kotlinx.android.synthetic.main.activity_bookmark.*
import java.util.ArrayList

class BookMarkActivity: BaseActivity() , DashboardClickListener {

    private lateinit var notesDBViewModel: NotesDBViewModel
    private var mNotesDataModel: ArrayList<NotesDataModel> = arrayListOf()

    override fun init() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bookmark)
        initView()
    }

    override fun bookmarkCheckBoxClicked(isCheck: Boolean, id: String) {}

    override fun deleteBookMark(id: String) {
        notesDBViewModel.updateBookMarkNotes(id, false)
        mNotesDataModel.clear()
    }

    private fun initView() {
        notesDBViewModel = ViewModelProviders.of(this).get(NotesDBViewModel::class.java)
        notesDBViewModel.getAllBookMarkNotes(true).observe(this, Observer<List<NotesDBModel>> {
            it.mapTo(mNotesDataModel) { t ->
                NotesDataModel(
                    t.id, t.subjectName, t.author, t.rating, t.price, t.bookMarked, t.addedToCart
                )
            }
            val layoutManager = LinearLayoutManager(this)
            rv_bookmark!!.layoutManager = layoutManager
            val adapter = BookMarkAdapter(this, this, mNotesDataModel)
            rv_bookmark!!.adapter = adapter
        })
    }
}