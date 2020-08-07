package com.example.notesapp.dashboard.ui.home

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.R
import com.example.notesapp.dashboard.DashboardClickListener
import com.example.notesapp.model.NotesDataModel
import kotlinx.android.synthetic.main.item_dashboard_content.view.*

class DashboardAdapter(
    val context: FragmentActivity,
    val bookMarkChecked: DashboardClickListener,
    val noteList: ArrayList<NotesDataModel>
) : RecyclerView.Adapter<DashboardAdapter.DashboardContentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DashboardContentViewHolder(
        LayoutInflater.from(context).inflate(R.layout.item_dashboard_content, parent, false)
    )

    override fun getItemCount(): Int {
        return noteList.size
    }

    override fun onBindViewHolder(holder: DashboardContentViewHolder, position: Int) {
        holder.bindActivityView(noteList[position], position)
    }


    inner class DashboardContentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindActivityView(
            notesDataModel: NotesDataModel,
            position: Int
        ) {
            if (position % 2 == 1) {
                itemView.second_row_layout.visibility = View.VISIBLE
                itemView.first_row_layout.visibility = View.GONE
            } else {
                itemView.first_row_layout.visibility = View.VISIBLE
                itemView.second_row_layout.visibility = View.GONE
            }
            itemView.ib_bookmark.isChecked = noteList[position].bookMarked
            itemView.ib_bookmark.setOnCheckedChangeListener(null)
            //itemView.ib_bookmark.isSelected = notesDataModel.bookMarked
            itemView.ib_bookmark.tag = position
            itemView.ib_bookmark.setOnCheckedChangeListener { view, isCheck ->
                notesDataModel.bookMarked = isCheck
            }
            itemView.ib_bookmark.setOnClickListener {
                val pos = itemView.ib_bookmark.tag as Int
                noteList[pos].bookMarked = noteList[pos].bookMarked
                bookMarkChecked.bookmarkCheckBoxClicked(
                    notesDataModel.bookMarked,
                    notesDataModel.id
                )
            }
        }
    }
}