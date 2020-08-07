package com.example.notesapp.dashboard

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.R
import com.example.notesapp.model.NotesDataModel
import kotlinx.android.synthetic.main.item_bookmark_list.view.*

class BookMarkAdapter(val context: Context, val deleteBookMarkListener: DashboardClickListener, val bookMarkList: ArrayList<NotesDataModel>): RecyclerView.Adapter<BookMarkAdapter.BookMarkListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = BookMarkListViewHolder(
        LayoutInflater.from(context).inflate(R.layout.item_bookmark_list, parent, false))

    override fun getItemCount(): Int {
        return bookMarkList.size
    }

    override fun onBindViewHolder(holder: BookMarkListViewHolder, position: Int) {
        holder.bindActivityView(bookMarkList[position], position)
    }


    inner class BookMarkListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindActivityView(
            notesDataModel: NotesDataModel,
            position: Int
        ) {
            itemView.cardView.setOnClickListener {
                deleteBookMarkListener.deleteBookMark(notesDataModel.id)
            }
        }
    }
}