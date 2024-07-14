package me.safarov399.common.custom_views.home.label

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import me.safarov399.common.databinding.LabelHeaderViewBinding

class LabelHeaderView @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attr, defStyleAttr) {
    private val binding = LabelHeaderViewBinding.inflate(
        LayoutInflater.from(context), this, true
    )
}