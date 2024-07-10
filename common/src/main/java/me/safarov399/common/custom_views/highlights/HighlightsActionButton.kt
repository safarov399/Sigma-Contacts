package me.safarov399.common.custom_views.highlights

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import me.safarov399.common.R
import me.safarov399.common.databinding.HighlightsActionButtonBinding

class HighlightsActionButton @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attr, defStyleAttr) {

    private val binding = HighlightsActionButtonBinding.inflate(LayoutInflater.from(context), this, true)
    init {
        context.theme.obtainStyledAttributes(
            attr,
            R.styleable.HighlightsActionButton,
            0, 0
        ).apply {
            try {
                val imageResId = getResourceId(R.styleable.HighlightsActionButton_imageSrc, 0)
                if (imageResId != 0) {
                    binding.highlightsActionIv.setImageResource(imageResId)
                }
                val text = getString(R.styleable.HighlightsActionButton_text)
                if (!text.isNullOrEmpty()) {
                    binding.highlightsActionTv.text = text
                }
            } finally {
                recycle()
            }
        }
    }
}