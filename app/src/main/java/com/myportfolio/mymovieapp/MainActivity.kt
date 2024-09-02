package com.myportfolio.mymovieapp

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import dagger.hilt.android.AndroidEntryPoint

// Main Activity class. Contains all the fragments used in the app and deals with the toolbar.
@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    // Controls the navigation between fragments.
    private lateinit var navController: NavController
    // Button inside the toolbar that sends the user to the Favorites Fragment, where it shows all the user's favorite media.
    private lateinit var favoritesButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.findNavController()
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        setupActionBarWithNavController(navController)
        favoritesButton = findViewById(R.id.toolbar_favorites_button)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    // Shows the Favorites button in the toolbar.
    fun showFavoritesButton() {
        favoritesButton.visibility = View.VISIBLE
        favoritesButton.setOnClickListener {
            navController.navigate(R.id.action_homeFragment_to_favoritesFragment)
        }
    }

    // Hides the Favorites button in the toolbar.
    fun hideFavoritesButton() {
        favoritesButton.visibility = View.INVISIBLE
        favoritesButton.setOnClickListener(null)
    }

}