package com.example.notesapp.dashboard

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.example.notesapp.R
import com.example.notesapp.bookmark.BookMarkActivity
import com.example.notesapp.cart.CartActivity
import kotlinx.android.synthetic.main.app_bar_dashboard.*

class DashboardActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupporrtActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val actionBarDrawerToggle =
            object : ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close) {
                private val scaleFactor = 6f
                override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                    super.onDrawerSlide(drawerView, slideOffset)
                    val slideX = drawerView.width * slideOffset
                    content.translationX = slideX
                    content.scaleX = 1 - (slideOffset / scaleFactor)
                    content.scaleY = 1 - (slideOffset / scaleFactor)
                    content.cardElevation = 100.0f
                    content.radius = 80f
                }

                override fun onDrawerOpened(drawerView: View) {
                    super.onDrawerOpened(drawerView)

                }

                override fun onDrawerClosed(drawerView: View) {
                    super.onDrawerClosed(drawerView)
                    content.cardElevation = 0f
                    content.radius = 0f
                }
            }
        1
        actionBarDrawerToggle.isDrawerIndicatorEnabled = false
        actionBarDrawerToggle.setToolbarNavigationClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        actionBarDrawerToggle.setHomeAsUpIndicator(R.drawable.icon_hamburger)
        drawerLayout.setScrimColor(Color.TRANSPARENT)
        drawerLayout.drawerElevation = 0f
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        initView()
    }

    private fun initView() {
        ib_toolbar_bookmark.setOnClickListener {
            val intent = Intent(this, BookMarkActivity::class.java)
            startActivity(intent)
        }

        ib_toolbar_cart.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.dashboard, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
