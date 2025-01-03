package com.stepikbrowser

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.stepikbrowser.databinding.MainActivityBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: MainActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavView)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        bottomNavigationView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            //Clearing these because we never back down
            navController.popBackStack(R.id.splashFragment, true)
            navController.popBackStack(R.id.onboardingFragment, true)
            navController.popBackStack(R.id.authFragment, true)
            Log.d("Navigating logger", "Navigated to $destination")
            when (destination.id) {
                R.id.homeFragment,
                R.id.favoritesFragment,
                R.id.profileFragment -> {
                    bottomNavigationView.visibility = View.VISIBLE
                }
                else -> bottomNavigationView.visibility = View.GONE
            }
        }
    }
}
