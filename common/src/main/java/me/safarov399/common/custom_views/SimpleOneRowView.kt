package me.safarov399.common.custom_views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import me.safarov399.common.R
import me.safarov399.common.databinding.SimpleOneRowViewBinding

class SimpleOneRowView @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attr, defStyleAttr) {
    private val binding = SimpleOneRowViewBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        context.theme.obtainStyledAttributes(
            attr,
            R.styleable.SimpleOneRowView,
            0, 0
        ).apply {
            try {
                val text = getString(R.styleable.SimpleOneRowView_title)
                if (!text.isNullOrEmpty()) {
                    binding.simpleOneRowTitleTv.text = text
                }
            } finally {
                recycle()
            }
        }
    }
}