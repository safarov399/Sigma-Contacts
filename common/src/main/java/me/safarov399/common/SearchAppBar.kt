package me.safarov399.common

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import me.safarov399.common.databinding.SearchAppBarBinding
import me.safarov399.common.dialogs.AccountDialog

class SearchAppBar @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attr, defStyleAttr) {
    private var binding = SearchAppBarBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    init {
        binding.searchBarSmartphoneIcon.setOnClickListener {
            val dialog = AccountDialog(context)
            dialog.show()
        }
    }

}