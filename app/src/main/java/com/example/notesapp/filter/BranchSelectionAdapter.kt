package com.example.notesapp.filter

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.R
import com.example.notesapp.model.SavedDataModel
import kotlinx.android.synthetic.main.item_filter_branch_selection.view.*

class BranchSelectionAdapter(
    val context: FragmentActivity,
    val branchList: List<SavedDataModel>
) : RecyclerView.Adapter<BranchSelectionAdapter.BranchSelectionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = BranchSelectionViewHolder(
        LayoutInflater.from(context).inflate(R.layout.item_filter_branch_selection, parent, false)
    )

    override fun onBindViewHolder(holder: BranchSelectionViewHolder, position: Int) {
        holder.bindActivityView(branchList[position])
    }

    override fun getItemCount(): Int {
        return branchList.size
    }


    inner class BranchSelectionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindActivityView(savedDataModel: SavedDataModel) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                itemView.view_color.setBackgroundColor(
                    context.resources.getColor(
                        savedDataModel.color!!,
                        null
                    )
                )
            }
            itemView.tv_branch_name.text = savedDataModel.techFullName
        }
    }
}