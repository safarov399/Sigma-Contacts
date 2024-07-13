package me.safarov399.common

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import me.safarov399.common.databinding.UtilityAppBarBinding

class UtilityAppBar @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attr, defStyleAttr) {
    private val binding = UtilityAppBarBinding.inflate(LayoutInflater.from(context), this, true)
}