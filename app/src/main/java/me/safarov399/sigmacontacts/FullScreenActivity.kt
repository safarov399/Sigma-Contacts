package me.safarov399.sigmacontacts

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.ColorUtils
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.shape.MaterialShapeDrawable
import dagger.hilt.android.AndroidEntryPoint
import me.safarov399.add.AddFragment
import me.safarov399.core.exception.InvalidNavigationTargetException
import me.safarov399.core.navigation.NavigationDestinationHandler.DATA_ID
import me.safarov399.core.navigation.NavigationDestinationHandler.NAVIGATE_TO_ADD
import me.safarov399.core.navigation.NavigationDestinationHandler.NAVIGATE_TO_DETAILS
import me.safarov399.core.navigation.NavigationDestinationHandler.NAVIGATE_TO_SETTINGS
import me.safarov399.core.navigation.NavigationManager
import me.safarov399.core.navigation.NavigationDestinationHandler.NAVIGATION_ID
import me.safarov399.core.navigation.NavigationSettings
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
        val dataId = intent.getLongExtra(DATA_ID, 0)
        when (navId) {
            NAVIGATE_TO_ADD -> {
                supportFragmentManager.beginTransaction().replace(binding!!.fullContainerView.id, AddFragment()).commit()
            }

            NAVIGATE_TO_SETTINGS -> {
                supportFragmentManager.beginTransaction().replace(binding!!.fullContainerView.id, SettingsFragment()).commit()
            }

            NAVIGATE_TO_DETAILS -> {
                val detailsFragment = DetailsFragment()
                detailsFragment.setDataId(dataId)
                supportFragmentManager.beginTransaction().replace(binding!!.fullContainerView.id, detailsFragment).commit()
            }

            else -> {
                throw RuntimeException("Some shit happened")
            }
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onResume() {
        super.onResume()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            setNavigationBarColor()
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    fun setNavigationBarColor() {
        val navigationMode = Settings.Secure.getInt(contentResolver, "navigation_mode", -1)

        if(Build.VERSION.SDK_INT == Build.VERSION_CODES.R || Build.VERSION.SDK_INT == Build.VERSION_CODES.Q) {
            if(navigationMode == NavigationSettings.THREE_BUTTON_NAVIGATION || navigationMode == NavigationSettings.TWO_BUTTON_NAVIGATION) {
                if((resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES) {
                    val typedValue = TypedValue()
                    theme.resolveAttribute(com.google.android.material.R.attr.colorSurface, typedValue, true)
                    val bgColor = typedValue.data
                    window.navigationBarColor = bgColor
                }
            }
        }
    }

    @OptIn(ExperimentalStdlibApi::class)
    override fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()

    }

    override fun navigateToFullScreenActivity(destinationId: Int, dataId: Long) {
        throw InvalidNavigationTargetException("Cannot launch FullScreenActivity from FullScreenActivity.")
    }

    override fun toggleMoreVertVisibility(visibility: Int) {
        throw UnsupportedOperationException("This operation is not valid for FullScreenActivity.")
    }
}