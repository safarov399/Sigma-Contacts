package me.safarov399.core.appbars

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import me.safarov399.common.databinding.SearchAppBarBinding

class SearchAppBar @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attr, defStyleAttr) {
    private var binding = SearchAppBarBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    init {
        binding.searchBarDeviceBackground.setOnClickListener {
            val dialog = me.safarov399.core.dialog.AccountDialog(context)
            dialog.show()
        }
    }

}