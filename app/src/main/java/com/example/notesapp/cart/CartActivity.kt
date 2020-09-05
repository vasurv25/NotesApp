package com.example.notesapp.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notesapp.R
import com.example.notesapp.base.BaseActivity
import com.example.notesapp.dashboard.DashboardClickListener
import com.example.notesapp.database.DBActivityListener
import com.example.notesapp.database.NotesDBViewModel
import com.example.notesapp.database.dbmodel.NotesDBModel
import com.example.notesapp.model.NotesDataModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_cart.*
import java.util.ArrayList

class CartActivity : BaseActivity(), DBActivityListener, DashboardClickListener {

    private lateinit var notesDBViewModel: NotesDBViewModel
    private var mNotesDataModel: ArrayList<NotesDataModel> = arrayListOf()
    private lateinit var mAdapter : CartAdapter

    private var isUpdated: Boolean = false

    override fun init() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        initViews()
    }

    private fun initViews() {
        notesDBViewModel = ViewModelProviders.of(this).get(NotesDBViewModel::class.java)
        notesDBViewModel.getAddedToCartNotes(true).observe(this, Observer<List<NotesDBModel>> {
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
                if (!isUpdated) {
                    setUpAdapter()
                    mAdapter?.apply {
                        addAllCart(mNotesDataModel)
                    }
                }
                isUpdated = false
            }
        })
    }

    private fun setUpAdapter() {
        val layoutManager = LinearLayoutManager(this)
        rv_cart!!.layoutManager = layoutManager
        mAdapter =
            CartAdapter(this, this)

        rv_cart.apply {
            setHasFixedSize(true)
            adapter = mAdapter
        }
    }

    override fun bookmarkCheckBoxClicked(isCheck: Boolean, id: String) {
    }

    override fun deleteBookMark(id: String, position: Int) {
        notesDBViewModel.updateAddToCartNotes(id, false)
        isUpdated = true
        mAdapter.cartItemRemoved(position)
    }

    override fun chooseOptionListener(id: String, position: Int) {
        showBottomSheetDialog(id, position)
    }

    override fun getBookMarkIdUpdate(ids: List<String>) {
    }

    override fun insertAllNotesUpdate() {
    }

    override fun getAddedToCartIdUpdate(ids: List<String>) {
    }

    override fun checkAddressSelection(id: Int, isSelected: Boolean) {
    }


    private fun showBottomSheetDialog(id: String, position: Int) {
        val bottomSheetDialog = BottomSheetDialog(this, R.style.BottomSheetDialogTheme)
        val bottomSheetView = LayoutInflater.from(applicationContext).inflate(R.layout.cart_bottom_sheet,
            findViewById<ConstraintLayout>(R.id.bottom_sheet_container)
        )
        val moveToWishListButton = bottomSheetView.findViewById<AppCompatTextView>(R.id.tv_cart_move_wishlist)
        moveToWishListButton.setOnClickListener {
            notesDBViewModel.updateBookMarkNotes(id, true)
            notesDBViewModel.updateAddToCartNotes(id, false)
            mAdapter.cartItemRemoved(position)
            isUpdated = true
            Toast.makeText(this, getString(R.string.toast_add_to_wishlist), Toast.LENGTH_SHORT).show()
            bottomSheetDialog.dismiss()
        }
        bottomSheetDialog.setContentView(bottomSheetView)
        bottomSheetDialog.show()
    }
}