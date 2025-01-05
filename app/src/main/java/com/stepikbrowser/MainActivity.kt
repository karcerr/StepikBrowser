package com.stepikbrowser

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.stepikbrowser.databinding.MainActivityBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: MainActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNavigationView = binding.bottomNavView
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        bottomNavigationView.setOnItemSelectedListener { item ->
            val currentDestination = navController.currentDestination?.id
            if (currentDestination == item.itemId) { // Preventing navigating to the fragment we are currently in
                return@setOnItemSelectedListener false
            }
            val navOptions = when (item.itemId) {
                R.id.homeFragment -> createNavOptions(currentDestination, R.id.homeFragment)
                R.id.favoritesFragment -> createNavOptions(currentDestination, R.id.favoritesFragment)
                R.id.profileFragment -> createNavOptions(currentDestination, R.id.profileFragment)
                else -> null
            }

            if (navOptions != null) {
                navController.navigate(item.itemId, null, navOptions)
                true
            } else {
                false
            }
        }
        bottomNavigationView.menu.findItem(R.id.homeFragment).setChecked(true)

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
    private fun createNavOptions(currentDestination: Int?, targetDestination: Int): NavOptions {
        return NavOptions.Builder()
            .setEnterAnim(
                if (currentDestination != null && currentDestination < targetDestination)
                    R.anim.slide_in_right
                else
                    R.anim.slide_in_left
            )
            .setExitAnim(
                if (currentDestination != null && currentDestination < targetDestination)
                    R.anim.slide_out_left
                else
                    R.anim.slide_out_right
            )
            .setPopEnterAnim(
                if (currentDestination != null && currentDestination > targetDestination)
                    R.anim.slide_in_left
                else
                    R.anim.slide_in_right
            )
            .setPopExitAnim(
                if (currentDestination != null && currentDestination > targetDestination)
                    R.anim.slide_out_right
                else
                    R.anim.slide_out_left
            )
            .build()
    }
}
