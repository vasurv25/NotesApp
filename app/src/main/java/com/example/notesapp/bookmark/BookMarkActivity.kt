package com.example.notesapp.bookmark

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notesapp.R
import com.example.notesapp.address.AddressActivity
import com.example.notesapp.base.BaseActivity
import com.example.notesapp.dashboard.DashboardClickListener
import com.example.notesapp.database.NotesDBViewModel
import com.example.notesapp.database.dbmodel.NotesDBModel
import com.example.notesapp.model.NotesDataModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_bookmark.*
import java.util.ArrayList

class BookMarkActivity: BaseActivity() , DashboardClickListener {

    private lateinit var notesDBViewModel: NotesDBViewModel
    private var mNotesDataModel: ArrayList<NotesDataModel> = arrayListOf()
    private var isUpdated: Boolean = false
    private lateinit var mAdapter: BookMarkAdapter

    override fun init() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bookmark)
        initView()
    }

    override fun bookmarkCheckBoxClicked(isCheck: Boolean, id: String) {}

    override fun deleteBookMark(id: String, position: Int) {
        notesDBViewModel.updateBookMarkNotes(id, false)
        isUpdated = true
        mAdapter.bookMarkItemRemoved(position)
    }

    override fun chooseOptionListener(id: String, position: Int) {
        showBottomSheetDialog(id)
    }

    override fun checkAddressSelection(id: Int, isSelected: Boolean) {
    }

    private fun showBottomSheetDialog(id: String) {
        val bottomSheetDialog = BottomSheetDialog(this, R.style.BottomSheetDialogTheme)
        val bottomSheetView = LayoutInflater.from(applicationContext).inflate(R.layout.bookmark_bottom_sheet,
            findViewById<ConstraintLayout>(R.id.bottom_sheet_container)
        )
        val cartButton = bottomSheetView.findViewById<AppCompatTextView>(R.id.tv_sheet_cart)
        val buyNowButton = bottomSheetView.findViewById<AppCompatTextView>(R.id.tv_sheet_buy_now)
        cartButton.setOnClickListener {
            notesDBViewModel.updateAddToCartNotes(id, true)
            isUpdated = true
            Toast.makeText(this, getString(R.string.toast_added_to_cart_msg), Toast.LENGTH_SHORT).show()
            bottomSheetDialog.dismiss()
        }
        buyNowButton.setOnClickListener {
            val intent = Intent(this, AddressActivity::class.java)
            startActivity(intent)
        }
        bottomSheetDialog.setContentView(bottomSheetView)
        bottomSheetDialog.show()
    }

    private fun initView() {
        notesDBViewModel = ViewModelProviders.of(this).get(NotesDBViewModel::class.java)
        notesDBViewModel.getAllBookMarkNotes(true).observe(this, Observer<List<NotesDBModel>> {
            if (!isUpdated) {
                it.mapTo(mNotesDataModel) { t ->
                    NotesDataModel(
                        t.id,
                        t.subjectName,
                        t.author,
                        t.rating,
                        t.price,
                        t.bookMarked,
                        t.addedToCart
                    )
                }
                setUpAdapter()
                mAdapter?.apply {
                    addAllBookMark(mNotesDataModel)
                }
                isUpdated = false
            }
        })
    }

    private fun setUpAdapter() {
        val layoutManager = LinearLayoutManager(this)
        rv_bookmark!!.layoutManager = layoutManager
        mAdapter =
            BookMarkAdapter(this, this)

        rv_bookmark.apply {
            setHasFixedSize(true)
            adapter = mAdapter
        }
    }
}