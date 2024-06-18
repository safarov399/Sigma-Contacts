package me.safarov399.common.dialogs

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View.VISIBLE
import android.view.Window
import me.safarov399.common.databinding.PermissionDialogBinding

class PermissionRequestDialog(context: Context) : Dialog(context) {

    private val binding: PermissionDialogBinding

    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setCancelable(false)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        binding = PermissionDialogBinding.inflate(LayoutInflater.from(context))
        setContentView(binding.root)
    }



    fun setTitle(title: String) {
        binding.permissionDialogTitleTv.text = title
    }

    fun setDescription(description: String) {
        binding.permissionDialogDescriptionTv.text = description
    }

    private fun setCancelButtonVisibility(setVisible: Int) {
        binding.permissionDialogCancelButton.visibility = setVisible
    }

    fun setCancelButtonOnClickListener(onClick: () -> Unit) {
        binding.permissionDialogCancelButton.setOnClickListener {
            onClick.invoke()
        }
        setCancelButtonVisibility(VISIBLE)
    }

    fun setCancelButtonText(text: String) {
        binding.permissionDialogCancelButton.text = text
    }

    fun setOkButtonOnClickListener(onClick: () -> Unit) {
        binding.permissionDialogOkButton.setOnClickListener {
            onClick.invoke()
        }
    }

    fun setOkButtonText(text: String) {
        binding.permissionDialogOkButton.text = text
    }
}
