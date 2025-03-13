package me.safarov399.sigmacontacts

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowInsetsController
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.color.DynamicColors
import dagger.hilt.android.AndroidEntryPoint
import me.safarov399.core.exception.InvalidNavigationTargetException
import me.safarov399.core.navigation.NavigationDestinationHandler.DATA_ID
import me.safarov399.core.navigation.NavigationDestinationHandler.NAVIGATION_ID
import me.safarov399.core.navigation.NavigationManager
import me.safarov399.sigmacontacts.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavigationManager {

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding?.root)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }
        DynamicColors.applyToActivityIfAvailable(this)
        configureViews()
        configureBottomNavigationView()
    }


    private fun configureBottomNavigationView() {
        val navController = (supportFragmentManager.findFragmentById(
            R.id.main_fragment_container_view
        ) as NavHostFragment).navController
        binding?.mainBottomNavView?.setupWithNavController(navController)
    }


    private fun configureViews() {
        binding?.mainAppBar?.findViewById<ImageView>(me.safarov399.common.R.id.search_bar_three_dots_icon)
            ?.setOnClickListener {
                showSelectPopup(it)
            }
        setNavigationBarColor()
    }

    private fun setNavigationBarColor() {
        val navBarColor = getColor(me.safarov399.common.R.color.bottom_nav_background_color)
        window.navigationBarColor = navBarColor

        // 2. Set light/dark icons based on color contrast
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // For API 30+ (Android 11+)
            window.insetsController?.setSystemBarsAppearance(
                WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS,
                WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS
            )
        } else {
            // For APIs 26-29
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR // Dark icons for light backgrounds
        }

        // 3. Ensure system draws the navigation bar background (disable edge-to-edge overlap)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
    }

    private fun showSelectPopup(view: View) {
        val popup = PopupMenu(this, view)
        val popupMenuInflater = popup.menuInflater
        popupMenuInflater.inflate(me.safarov399.common.R.menu.search_menu, popup.menu)
        popup.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                me.safarov399.common.R.id.select -> {
                    Toast.makeText(this, "Select", Toast.LENGTH_SHORT).show()
                }

                me.safarov399.common.R.id.select_all -> {
                    Toast.makeText(this, "Select all", Toast.LENGTH_SHORT).show()
                }
            }
            true
        }
        popup.show()
    }

    override fun navigateToMainActivity() {
        throw InvalidNavigationTargetException("Cannot launch MainActivity from MainActivity")
    }

    override fun navigateToFullScreenActivity(destinationId: Int, dataId: Long) {
        val intent = Intent(this, FullScreenActivity::class.java)
        intent.putExtra(NAVIGATION_ID, destinationId)
        intent.putExtra(DATA_ID, dataId)
        startActivity(intent)
    }
}