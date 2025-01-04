package com.dicoding.submissiondicodingevent.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.dicoding.dicodingevents.ui.setting.ViewModelFactory
import com.dicoding.submissiondicodingevent.R
import com.dicoding.submissiondicodingevent.databinding.ActivityMainBottomBinding
import com.dicoding.submissiondicodingevent.ui.setting.MainViewModel
import com.dicoding.submissiondicodingevent.ui.setting.SettingPreferences
import com.dicoding.submissiondicodingevent.ui.setting.dataStore
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivityBottom : AppCompatActivity() {

    private lateinit var binding: ActivityMainBottomBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBottomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = SettingPreferences.getInstance(application.dataStore)

        val themeViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(
            MainViewModel::class.java)

        themeViewModel.getThemeSettings().observe(this) { isDarkModeActive ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        val toolbar: Toolbar = binding.toolbar
        setSupportActionBar(toolbar)

        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        val navController = findNavController(R.id.nav_host_fragment_activity_main_bottom)
        val navView: BottomNavigationView = binding.bottomNavigation

        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.navigation_upcoming, R.id.navigation_finished, R.id.navigation_favorite  ,R.id.navigation_setting)
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_activity_main_bottom)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
