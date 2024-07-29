package me.safarov399.core.dialog

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.view.Gravity
import android.view.LayoutInflater
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.ActionBar.LayoutParams
import me.safarov399.common.databinding.AccountDialogBinding
import me.safarov399.core.navigation.NavigationDestinationHandler.NAVIGATE_TO_SETTINGS
import me.safarov399.core.navigation.NavigationManager

class AccountDialog(context: Context) : Dialog(context) {
    private val binding: AccountDialogBinding


    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setCancelable(true)

        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        binding = AccountDialogBinding.inflate(LayoutInflater.from(context))
        setContentView(binding.root)

        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(window?.attributes)
        layoutParams.gravity = Gravity.TOP
        layoutParams.y = 220

        window?.attributes = layoutParams
        window?.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)

        binding.xButtonIv.setOnClickListener {
            dismiss()
        }
        binding.accountDialogContactSettings.setOnClickListener {
            val ctx = context as NavigationManager
            ctx.navigateToFullScreenActivity(NAVIGATE_TO_SETTINGS)
            dismiss()
        }
        binding.accountDialogHelpAndFeedback.setOnClickListener {
            val link = "https://support.google.com/nexus/topic/6118711"
            val browserIntent = Intent(Intent.ACTION_VIEW)
            browserIntent.setData(Uri.parse(link))
            context.startActivity(browserIntent)
            dismiss()
        }

        binding.accountDialogPrivacyPolicyTv.setOnClickListener {
            val link = "https://policies.google.com/privacy"
            val browserIntent = Intent(Intent.ACTION_VIEW)
            browserIntent.setData(Uri.parse(link))
            context.startActivity(browserIntent)
            dismiss()
        }

        binding.accountDialogTermsOfService.setOnClickListener {
            val link = "https://policies.google.com/terms"
            val browserIntent = Intent(Intent.ACTION_VIEW)
            browserIntent.setData(Uri.parse(link))
            context.startActivity(browserIntent)
            dismiss()
        }
    }


}