package com.example.notesapp.bookmark

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.R
import com.example.notesapp.dashboard.DashboardClickListener
import com.example.notesapp.model.NotesDataModel
import kotlinx.android.synthetic.main.item_bookmark.view.*
import java.util.ArrayList

class BookMarkAdapter(
    val context: Context,
    val deleteBookMarkListener: DashboardClickListener
) : RecyclerView.Adapter<BookMarkAdapter.BookMarkListViewHolder>() {

    private val mBookMarkList = mutableListOf<NotesDataModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = BookMarkListViewHolder(
        LayoutInflater.from(context).inflate(R.layout.item_bookmark, parent, false)
    )

    override fun getItemCount(): Int {
        return mBookMarkList.size
    }

    override fun onBindViewHolder(holder: BookMarkListViewHolder, position: Int) {
        holder.bindActivityView(mBookMarkList[position], position)
    }


    inner class BookMarkListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindActivityView(
            notesDataModel: NotesDataModel,
            position: Int
        ) {
            itemView.tv_bookmark_subject_name.text = notesDataModel.subjectName
            itemView.tv_bookmark_author_name.text = notesDataModel.author
            itemView.tv_bookmark_price.text = context.getString(R.string.Rs) + notesDataModel.price
            itemView.iv_option_selection.setOnClickListener {
                deleteBookMarkListener.chooseOptionListener(notesDataModel.id, position)
            }
            itemView.iv_bookmark_remove.setOnClickListener {
                deleteBookMarkListener.deleteBookMark(notesDataModel.id, position)
            }
        }
    }

    fun addAllBookMark(notesDataModel: ArrayList<NotesDataModel>) {
        mBookMarkList.addAll(notesDataModel)
        notifyDataSetChanged()
    }

    fun bookMarkItemRemoved(position: Int) {
        mBookMarkList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, itemCount)
    }
}