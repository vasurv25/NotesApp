package com.example.notesapp.filter

import android.os.Build
import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.notesapp.R
import com.example.notesapp.base.BaseActivity

class FilterActivity : BaseActivity() {

    // double back pressed function
    companion object {
        // double back pressed function
        private var backPressed: Long = 0
    }


    override fun init() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)
        showFragment(TechSelectionFragment(), "techList")
    }

    /**
     * function to show the fragment
     *
     * @param name fragment to be shown
     * @param tag  fragment tag
     */
    private fun showFragment(fragment: Fragment, tag: String) {
        val fragmentManager = supportFragmentManager
        // check if the fragment is in back stack
        val fragmentPopped = fragmentManager.popBackStackImmediate(tag, 0)
        if (fragmentPopped) {
            // fragment is pop from backStack
        } else {
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.container_filter, fragment, tag)
            fragmentTransaction.addToBackStack(tag)
            fragmentTransaction.commit()
        }
    }

    /**
     * function to show the fragment
     *
     * @param current current visible fragment
     * @param tag     fragment tag
     */
    fun showFragmentWithTransition(
        current: Fragment,
        newFragment: Fragment,
        tag: String,
        sharedView: View,
        sharedElementName: String
    ) {
        val fragmentManager = supportFragmentManager
        // check if the fragment is in back stack
        val fragmentPopped = fragmentManager.popBackStackImmediate(tag, 0)
        if (fragmentPopped) {
            // fragment is pop from backStack
        } else {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                current.sharedElementReturnTransition =
                    TransitionInflater.from(this).inflateTransition(R.transition.default_transition)
                current.exitTransition = TransitionInflater.from(this)
                    .inflateTransition(android.R.transition.no_transition)

                newFragment.sharedElementEnterTransition =
                    TransitionInflater.from(this).inflateTransition(R.transition.default_transition)
                newFragment.enterTransition = TransitionInflater.from(this)
                    .inflateTransition(android.R.transition.no_transition)
            }
            Log.d("TechActivity", "Name   :::::::::: " + sharedElementName)
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.container_filter, newFragment, tag)
            fragmentTransaction.addToBackStack(tag)
            fragmentTransaction.addSharedElement(sharedView, sharedElementName)
            fragmentTransaction.commit()
        }
    }

    /**
     * function to go back to previous fragment
     */
    private fun oneStepBack() {
        val fts = supportFragmentManager.beginTransaction()
        val fragmentManager = supportFragmentManager
        if (fragmentManager.backStackEntryCount >= 2) {
            fragmentManager.popBackStackImmediate()
            fts.commit()
        } else {
            doubleClickToExit()
        }
    }

    private fun doubleClickToExit() {
        if (backPressed + 2000 > System.currentTimeMillis())
            finish()
        else
            Toast.makeText(this, "Click again to exit", Toast.LENGTH_SHORT).show()
        backPressed = System.currentTimeMillis()
    }

    override fun onBackPressed() {
        oneStepBack()
    }
}