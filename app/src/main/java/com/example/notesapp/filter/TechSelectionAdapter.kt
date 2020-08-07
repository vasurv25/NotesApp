package com.example.notesapp.filter

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.R
import com.example.notesapp.model.SavedDataModel
import kotlinx.android.synthetic.main.item_filter_tech_selection.view.*

class TechSelectionAdapter(
    val context: FragmentActivity,
    val techList: List<SavedDataModel>,
    val techSelectionFragment: TechSelectionFragment
): RecyclerView.Adapter<TechSelectionAdapter.TechSelectionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = TechSelectionViewHolder(
        LayoutInflater.from(context).inflate(R.layout.item_filter_tech_selection, parent, false))

    override fun getItemCount(): Int {
        return techList.size
    }

    override fun onBindViewHolder(holder: TechSelectionViewHolder, position: Int) {
        holder.bindActivityView(position, techList[position])
    }


    inner class TechSelectionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
       fun bindActivityView(
           position: Int,
           savedDataModel: SavedDataModel
       ) {
           itemView.tv_tech_short_name.text = savedDataModel.techShortName
           itemView.tv_tech_full_name.text = savedDataModel.techFullName
           if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
               itemView.cv_tech_item.transitionName = "transition$position"
           }
           if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
               itemView.view_tech.setBackgroundColor(context.resources.getColor(savedDataModel.color!!, null))
           }
           itemView.cv_tech_item.setOnClickListener {
               techSelectionFragment.openBranchSelectionFragment(position, itemView.cv_tech_item)
           }
       }
    }
}