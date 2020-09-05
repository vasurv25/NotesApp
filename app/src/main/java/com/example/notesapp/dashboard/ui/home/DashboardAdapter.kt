package com.example.notesapp.dashboard.ui.home

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.CheckBox
import android.widget.RatingBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.EVEN_COLUMN
import com.example.notesapp.ODD_COLUMN
import com.example.notesapp.R
import com.example.notesapp.dashboard.DashboardClickListener
import com.example.notesapp.model.NotesDataModel

class DashboardAdapter(
    val context: FragmentActivity,
    val bookMarkChecked: DashboardClickListener,
    val noteList: ArrayList<NotesDataModel>
) : RecyclerView.Adapter<DashboardAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType == EVEN_COLUMN) {
            DashboardEventContentViewHolder.create(parent.context as FragmentActivity)
        } else {
            DashboardOddContentViewHolder.create(parent.context as FragmentActivity)
        }
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (position % 2 == 0) {
            EVEN_COLUMN
        } else {
            ODD_COLUMN
        }
    }

    abstract class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        abstract fun bind(
            notesDataModel: NotesDataModel,
            context: FragmentActivity,
            bookMarkChecked: DashboardClickListener,
            noteList: ArrayList<NotesDataModel>,
            position: Int
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(noteList[position], context, bookMarkChecked, noteList, position)
    }


    class DashboardEventContentViewHolder(view: View) : ViewHolder(view) {
        var subjectName: TextView? = null
        var authorName: TextView? = null
        var ratingBar: RatingBar? = null
        var price: TextView? = null
        var bookMarkCheck: CheckBox? = null
        var parentLayout: ConstraintLayout? = null

        init {
            subjectName = view.findViewById(R.id.tv_subject_name_even)
            authorName = view.findViewById(R.id.tv_author_name_even)
            ratingBar = view.findViewById(R.id.rating_star_even)
            price = view.findViewById(R.id.tv_price_even)
            bookMarkCheck = view.findViewById(R.id.ib_bookmark_even)
            parentLayout = view.findViewById(R.id.first_container_layout)
        }

        override fun bind(
            notesDataModel: NotesDataModel,
            context: FragmentActivity,
            bookMarkChecked: DashboardClickListener,
            noteList: ArrayList<NotesDataModel>,
            position: Int
        ) {
            subjectName?.text = notesDataModel.subjectName
            authorName?.text = notesDataModel.author
            ratingBar?.rating = notesDataModel.rating
            price?.text = context.getString(R.string.Rs) + notesDataModel.price
            bookMarkCheck?.isChecked = noteList[position].bookMarked
            Log.d("Adapter", "Notes Even bookMarkCheck : " + noteList[position].bookMarked + " , " + notesDataModel.id)
            parentLayout?.animation = AnimationUtils.loadAnimation(context, R.anim.item_animation_fall_down)
            //bookMarkCheck?.setOnCheckedChangeListener(null)
            //itemView.ib_bookmark.isSelected = notesDataModel.bookMarked
            bookMarkCheck?.tag = position
//            bookMarkCheck?.setOnCheckedChangeListener { view, isCheck ->
//                if (isCheck) {
//                    val pos = bookMarkCheck?.tag as Int
//                    noteList[pos].bookMarked = isCheck
//                    Log.d(
//                        "Adapter",
//                        "Notes Even CheckedChangeListener : " + notesDataModel.bookMarked + " , " + notesDataModel.id
//                    )
//                }
//            }
            bookMarkCheck?.setOnClickListener {
                val pos = bookMarkCheck?.tag as Int
                noteList[pos].bookMarked = bookMarkCheck!!.isChecked
                Log.d("Adapter", "Notes Even ClickListener : " + noteList[pos].bookMarked+ " , " + noteList[pos].id)
                bookMarkChecked.bookmarkCheckBoxClicked(
                    noteList[pos].bookMarked,
                    noteList[pos].id
                )
            }
        }

        companion object {
            fun create(context: FragmentActivity): DashboardEventContentViewHolder {
                return DashboardEventContentViewHolder(
                    LayoutInflater.from(context)
                        .inflate(R.layout.even_item_dashboard_content, null, false)
                )
            }
        }
    }

    class DashboardOddContentViewHolder(view: View) : ViewHolder(view) {
        var subjectName: TextView? = null
        var authorName: TextView? = null
        var ratingBar: RatingBar? = null
        var price: TextView? = null
        var bookMarkCheck: CheckBox? = null
        var parentLayout: ConstraintLayout? = null

        init {
            subjectName = view.findViewById(R.id.tv_subject_name_odd)
            authorName = view.findViewById(R.id.tv_author_name_odd)
            ratingBar = view.findViewById(R.id.rating_star_odd)
            price = view.findViewById(R.id.tv_price_odd)
            bookMarkCheck = view.findViewById(R.id.ib_bookmark_odd)
            parentLayout = view.findViewById(R.id.second_container_layout)
        }

        override fun bind(
            notesDataModel: NotesDataModel,
            context: FragmentActivity,
            bookMarkChecked: DashboardClickListener,
            noteList: ArrayList<NotesDataModel>,
            position: Int
        ) {
            subjectName?.text = notesDataModel.subjectName
            authorName?.text = notesDataModel.author
            ratingBar?.rating = notesDataModel.rating
            price?.text = context.getString(R.string.Rs) + notesDataModel.price
            bookMarkCheck?.isChecked = noteList[position].bookMarked
            parentLayout?.animation = AnimationUtils.loadAnimation(context, R.anim.item_animation_fall_down)
            Log.d("Adapter", "Notes Odd bookMarkCheck : " + noteList[position].bookMarked + " , " + notesDataModel.id)
            //bookMarkCheck?.setOnCheckedChangeListener(null)
            //itemView.ib_bookmark.isSelected = notesDataModel.bookMarked
            bookMarkCheck?.tag = position
//            bookMarkCheck?.setOnCheckedChangeListener { view, isCheck ->
//                if (isCheck) {
//                    val pos = bookMarkCheck?.tag as Int
//                    noteList[pos].bookMarked = isCheck
//                    Log.d(
//                        "Adapter",
//                        "Notes Odd CheckedChangeListener : " + notesDataModel.bookMarked + " , " + notesDataModel.id
//                    )
//                }
//            }
            bookMarkCheck?.setOnClickListener {
                val pos = bookMarkCheck?.tag as Int
                noteList[pos].bookMarked = bookMarkCheck!!.isChecked
                Log.d("Adapter", "Notes Odd ClickListener : " + noteList[pos].bookMarked+ " , " + noteList[pos].id)
                bookMarkChecked.bookmarkCheckBoxClicked(
                    noteList[pos].bookMarked,
                    noteList[pos].id
                )
            }
        }

        companion object {
            fun create(context: FragmentActivity): DashboardOddContentViewHolder {
                return DashboardOddContentViewHolder(
                    LayoutInflater.from(context)
                        .inflate(R.layout.odd_item_dashboard_content, null, false)
                )
            }
        }
    }
}