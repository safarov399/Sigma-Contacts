package me.safarov399.common.dialogs

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.Window
import me.safarov399.common.databinding.AccountDialogBinding

class AccountDialog(context: Context): Dialog(context) {
    private val binding: AccountDialogBinding


    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setCancelable(true)

        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        binding = AccountDialogBinding.inflate(LayoutInflater.from(context))
        setContentView(binding.root)

        binding.xButtonIv.setOnClickListener {
            dismiss()
        }
    }



}