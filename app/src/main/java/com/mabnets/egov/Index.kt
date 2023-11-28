package com.mabnets.egov

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.GravityCompat
import androidx.navigation.ui.*
import com.mabnets.egov.Util.showPermissionRequestExplanation
import com.mabnets.egov.databinding.ActivityIndexBinding
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint
@RequiresApi(Build.VERSION_CODES.M)
class Index : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityIndexBinding
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityIndexBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)


        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_index)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homescreen, R.id.contacts
            ), drawerLayout
        )
       // setupActionBarWithNavController(navController, appBarConfiguration)
        toolbar.setupWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if(destination.id == R.id.launcher) {
                toolbar.visibility = View.GONE
            }else{
                toolbar.visibility = View.VISIBLE
            }
        }
        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.register -> {
                    val c = "https://accounts.ecitizen.go.ke/register"
                    navController.navigate(R.id.wvinfo, bundleOf("web" to c))
                }
            }
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            //This is for maintaining the behavior of the Navigation view
            NavigationUI.onNavDestinationSelected(it, navController)
            true
        }

//comfirm if  permission was given

        requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
                if (!granted) {
                    Toast.makeText(this, "Storage Permission NOT Granted", Toast.LENGTH_SHORT).show()
                    requestStoragePermission()
                }
            }
        requestStoragePermission()

    }
    //asking for permission
    private fun requestStoragePermission(){
        when {
            ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED -> {
                // The permission is granted
                // you can go with the flow that requires permission here
            }
            shouldShowRequestPermissionRationale(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) -> {
                // This case means user previously denied the permission
                // So here we can display an explanation to the user
                // That why exactly we need this permission
                showPermissionRequestExplanation(
                    getString(R.string.write_storage),
                    getString(R.string.permission_request)
                ) { requestPermissionLauncher.launch(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) }
            }
            else -> {
                // Everything is fine you can simply request the permission
                requestPermissionLauncher.launch(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }
    }
    /*override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        //menuInflater.inflate(R.menu.index, menu)
        return true
    }*/

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_index)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}