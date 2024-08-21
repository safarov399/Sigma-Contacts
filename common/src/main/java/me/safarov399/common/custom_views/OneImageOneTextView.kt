package me.safarov399.common.custom_views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import me.safarov399.common.R
import me.safarov399.common.databinding.OneImageOneTextViewBinding

class OneImageOneTextView @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attr, defStyleAttr) {
    private val binding = OneImageOneTextViewBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        context.theme.obtainStyledAttributes(
            attr,
            R.styleable.OneImageOneTextView,
            0, 0
        ).apply {
            try {
                val imageResId = getResourceId(R.styleable.OneImageOneTextView_imageSrc, 0)
                if (imageResId != 0) {
                    binding.oneImageOneTextViewIv.setImageResource(imageResId)
                }
                val text = getString(R.styleable.OneImageOneTextView_text)
                if (!text.isNullOrEmpty()) {
                    binding.oneImageOneTextViewTv.text = text
                }
            } finally {
                recycle()
            }
        }
    }
}