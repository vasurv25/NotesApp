package com.example.notesapp.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.notesapp.R
import com.example.notesapp.base.BaseFragment
import com.example.notesapp.model.SavedDataModel
import com.example.utils.SELECTED_TECH
import com.example.utils.TRANSITION_NAME
import kotlinx.android.synthetic.main.fragment_filter_tech_selection.*

class TechSelectionFragment: BaseFragment() {

    private var mTechList = listOf(
        SavedDataModel(
            "",
            "",
            "",
            0,
            0,
            "B TECH",
            "Bachelor of Technology",
            R.color.color_tech_1
        ),
        SavedDataModel(
            "",
            "",
            "",
            0,
            0,
            "B ARCH",
            "Bachelor of Architecture",
            R.color.color_tech_2
        ),
        SavedDataModel(
            "",
            "",
            "",
            0,
            0,
            "B.A",
            "Bachelor of Arts",
            R.color.color_tech_3
        ),
        SavedDataModel(
            "",
            "",
            "",
            0,
            0,
            "B.SC",
            "Bachelor of Computer Science",
            R.color.color_tech_4
        ),
        SavedDataModel(
            "",
            "",
            "",
            0,
            0,
            "B.C.A",
            "Bachelor of Computer Applications (B.C.A.)",
            R.color.color_tech_5
        ),
        SavedDataModel(
            "",
            "",
            "",
            0,
            0,
            "B COM",
            "Bachelor of Commerce",
            R.color.color_tech_6
        ),
        SavedDataModel(
            "",
            "",
            "",
            0,
            0,
            "L.L.B",
            "Bachelor of Laws",
            R.color.color_tech_7
        ),
        SavedDataModel(
            "",
            "",
            "",
            0,
            0,
            "M.B.A",
            "Master of Business Administration (M.B.A.)",
            R.color.color_tech_8
        )
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_filter_tech_selection, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        val layoutManager = GridLayoutManager(activity, 2)
        rv_tech_selection!!.layoutManager = layoutManager
        val adapter = TechSelectionAdapter(this.activity!!, mTechList, this)
        rv_tech_selection!!.adapter = adapter
    }

    /**
     * function to open the movie detail fragment
     *
     * @param position Movie list position
     */
    fun openBranchSelectionFragment(position: Int, view: View) {
        if (context is FilterActivity) {
            val selectedTech = mTechList[position]
            val branchSelectionFragment = BranchSelectionFragment()
            val bundle = Bundle()
            bundle.putString(TRANSITION_NAME, "transition$position")
            bundle.putSerializable(SELECTED_TECH, selectedTech)

            branchSelectionFragment.arguments = bundle
            (context as FilterActivity).showFragmentWithTransition(
                this, branchSelectionFragment, "branchList", view,
                "transition$position"
            )
        }
    }
}