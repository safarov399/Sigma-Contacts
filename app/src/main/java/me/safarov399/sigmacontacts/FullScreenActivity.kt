package me.safarov399.sigmacontacts

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import dagger.hilt.android.AndroidEntryPoint
import me.safarov399.add.AddFragment
import me.safarov399.core.exception.InvalidNavigationTargetException
import me.safarov399.core.navigation.NavigationDestinationHandler.NAVIGATE_TO_ADD
import me.safarov399.core.navigation.NavigationDestinationHandler.NAVIGATE_TO_DETAILS
import me.safarov399.core.navigation.NavigationDestinationHandler.NAVIGATE_TO_SETTINGS
import me.safarov399.core.navigation.NavigationManager
import me.safarov399.core.navigation.NavigationDestinationHandler.NAVIGATION_ID
import me.safarov399.details.DetailsFragment
import me.safarov399.settings.SettingsFragment
import me.safarov399.sigmacontacts.databinding.ActivityFullScreenBinding

@AndroidEntryPoint
class FullScreenActivity : AppCompatActivity(), NavigationManager {

    private var binding: ActivityFullScreenBinding? = null

    @SuppressLint("CommitTransaction")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFullScreenBinding.inflate(LayoutInflater.from(this))
        enableEdgeToEdge()
        setContentView(binding?.root)

        val navId = intent.getIntExtra(NAVIGATION_ID, 0)
        when (navId) {
            NAVIGATE_TO_ADD -> {
                supportFragmentManager.beginTransaction().replace(binding!!.fullContainerView.id, AddFragment()).commit()
            }

            NAVIGATE_TO_SETTINGS -> {
                supportFragmentManager.beginTransaction().replace(binding!!.fullContainerView.id, SettingsFragment()).commit()
            }

            NAVIGATE_TO_DETAILS -> {
                supportFragmentManager.beginTransaction().replace(binding!!.fullContainerView.id, DetailsFragment()).commit()
            }

            else -> {
                throw RuntimeException("Some shit happened")
            }
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }


    }

    override fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()

    }

    override fun navigateToFullScreenActivity(id: Int) {
        throw InvalidNavigationTargetException("Cannot launch FullScreenActivity from FullScreenActivity")

    }
}