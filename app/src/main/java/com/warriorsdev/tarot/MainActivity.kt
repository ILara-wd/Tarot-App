package com.warriorsdev.tarot

import android.Manifest
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.warriorsdev.tarot.databinding.ActivityMainBinding
import com.warriorsdev.tarot.tools.DayChangedBroadcastReceiver
import com.warriorsdev.tarot.tools.ManagePermissions
import com.warriorsdev.tarot.tools.Preferences
import com.warriorsdev.tarot.tools.TarotUtils

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        preferences = Preferences(this@MainActivity)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarMain.toolbar)
        verifyStoragePermissions()
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.nav_home), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


    private val PermissionsRequestCode = 123
    private lateinit var managePermissions: ManagePermissions

    private fun verifyStoragePermissions() {
        val list = listOf(
            Manifest.permission.POST_NOTIFICATIONS
        )
        managePermissions =
            ManagePermissions(this@MainActivity, list, PermissionsRequestCode)
        managePermissions.checkPermissions()
    }

    private val dayChangedBroadcastReceiver = object : DayChangedBroadcastReceiver() {

        override fun onDayChanged() {
            val description = when (val count = preferences.getReading()) {
                0 -> {
                    getString(R.string.app_notification_description_first)
                }
                1 -> {
                    getString(R.string.app_notification_description_second)
                }
                else -> {
                    getString(R.string.app_notification_description_count, (count + 1).toString())
                }
            }
            TarotUtils.showNotification(this@MainActivity, description)
        }
    }

    override fun onResume() {
        super.onResume()
        this.registerReceiver(
            dayChangedBroadcastReceiver,
            DayChangedBroadcastReceiver.getIntentFilter()
        )
    }

}
