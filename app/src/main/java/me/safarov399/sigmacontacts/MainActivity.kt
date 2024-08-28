package me.safarov399.sigmacontacts

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
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
import me.safarov399.core.navigation.NavigationManager
import me.safarov399.core.navigation.NavigationDestinationHandler.NAVIGATION_ID
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