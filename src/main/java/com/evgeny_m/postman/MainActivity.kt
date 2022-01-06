package com.evgeny_m.postman

import android.Manifest
import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.evgeny_m.postman.databinding.ActivityMainBinding
import com.evgeny_m.postman.presentation.viewModels.settings.SettingsViewModel
import com.evgeny_m.postman.presentation.viewModels.settings.SettingsViewModelFactory
import java.util.*

lateinit var drawerLayout: DrawerLayout


class MainActivity : AppCompatActivity() {

    private val MY_PERMISSIONS_REQUEST = 1234
    private val PERMISSIONS = arrayOf<String>(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_CONTACTS
    )


    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfig: AppBarConfiguration
    private lateinit var viewModelSettings: SettingsViewModel
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /* if (savedInstanceState == null) {
             //supportFragmentManager.beginTransaction().replace(R.id.nav_content_host, SplashFragment()).addToBackStack(null).commit()

         }*/

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModelSettings = ViewModelProvider(
            this,
            SettingsViewModelFactory(this)
        )[SettingsViewModel::class.java]

        drawerLayout = binding.drawerLayout
        val navView = binding.navView
        val navController = findNavController(R.id.nav_content_host)
        appBarConfig =
            AppBarConfiguration(setOf(R.menu.menu_nav_drawer), drawerLayout)
        navView.setupWithNavController(navController)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && isPermissions()) {
            requestPermissions(PERMISSIONS, MY_PERMISSIONS_REQUEST)
            return
        }
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.close()
        } else {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            super.onBackPressed()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == MY_PERMISSIONS_REQUEST && grantResults.isNotEmpty()) {
            if (isPermissions()) {
                (Objects.requireNonNull(this.getSystemService(Context.ACTIVITY_SERVICE)) as ActivityManager).clearApplicationUserData()
            }
        }
    }

    @SuppressLint("NewApi")
    private fun isPermissions(): Boolean {
        PERMISSIONS.forEach {
            if (checkSelfPermission(it) != PackageManager.PERMISSION_GRANTED) {
                return true
            }
        }
        return false
    }

    override fun onStart() {
        super.onStart()
        viewModelSettings.online()
    }

    override fun onStop() {
        super.onStop()
        viewModelSettings.offline()
    }
}