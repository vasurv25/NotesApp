package com.example.notesapp.filter

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notesapp.R
import com.example.notesapp.base.BaseFragment
import com.example.notesapp.model.SavedDataModel
import com.example.utils.SELECTED_TECH
import com.example.utils.TRANSITION_NAME
import kotlinx.android.synthetic.main.fragment_filter_branch_selection.*

class BranchSelectionFragment: BaseFragment() {

    private var mBranchList = listOf(
        SavedDataModel(
            "",
            "",
            "",
            0,
            0,
            "B TECH",
            "Mechanical Engineering",
            R.color.color_tech_1
        ),
        SavedDataModel(
            "",
            "",
            "",
            0,
            0,
            "B TECH",
            "Civil Engineering",
            R.color.color_tech_2
        ),
        SavedDataModel(
            "",
            "",
            "",
            0,
            0,
            "B TECH",
            "Electrical Engineering",
            R.color.color_tech_3
        ),
        SavedDataModel(
            "",
            "",
            "",
            0,
            0,
            "B TECH",
            "Computer Science Engineering",
            R.color.color_tech_4
        ),
        SavedDataModel(
            "",
            "",
            "",
            0,
            0,
            "B TECH",
            "Information Technology",
            R.color.color_tech_5
        ),
        SavedDataModel(
            "",
            "",
            "",
            0,
            0,
            "B TECH",
            "Electronics & Telecommunication Engineering",
            R.color.color_tech_6
        ),
        SavedDataModel(
            "",
            "",
            "",
            0,
            0,
            "B TECH",
            "Electronics & Electrical Engineering",
            R.color.color_tech_7
        ),
        SavedDataModel(
            "",
            "",
            "",
            0,
            0,
            "B TECH",
            "Aeronautical Engineering",
            R.color.color_tech_8
        )
    )


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_filter_branch_selection, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        val transitionName = arguments?.getString(TRANSITION_NAME)
        val selectedTech = arguments?.getSerializable(SELECTED_TECH) as SavedDataModel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cv_tech_item.transitionName = transitionName
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            view_tech.setBackgroundColor(activity?.resources!!.getColor(selectedTech.color!!, null))
        }
        tv_tech_short_name.text = selectedTech.techShortName
        tv_tech_full_name.text = selectedTech.techFullName

        val layoutManager = LinearLayoutManager(activity)
        rv_branch!!.layoutManager = layoutManager
        val adapter = BranchSelectionAdapter(this.activity!!, mBranchList)
        rv_branch!!.adapter = adapter
    }
}