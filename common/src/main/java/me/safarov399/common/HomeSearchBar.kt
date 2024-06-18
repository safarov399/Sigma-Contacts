package me.safarov399.common

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import me.safarov399.common.databinding.HomeSearchBarBinding
import me.safarov399.common.dialogs.AccountDialog

class HomeSearchBar @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attr, defStyleAttr) {
    private var binding = HomeSearchBarBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    init {
        binding.searchBarSmartphoneIcon.setOnClickListener {
            val dialog: AccountDialog = AccountDialog(context)
            dialog.show()
        }
    }

}