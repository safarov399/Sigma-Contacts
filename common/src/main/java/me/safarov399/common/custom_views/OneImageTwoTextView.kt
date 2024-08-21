package me.safarov399.common.custom_views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import me.safarov399.common.R
import me.safarov399.common.databinding.OneImageTwoTextViewBinding

class OneImageTwoTextView @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attr, defStyleAttr)  {
    private val binding = OneImageTwoTextViewBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        context.theme.obtainStyledAttributes(
            attr,
            R.styleable.OneImageTwoTextView,
            0, 0
        ).apply {
            try {
                val imageResId = getResourceId(R.styleable.OneImageTwoTextView_imageSrc, 0)
                if (imageResId != 0) {
                    binding.oneImageTwoTextViewIv.setImageResource(imageResId)
                }
                val title = getString(R.styleable.OneImageTwoTextView_title)
                if (!title.isNullOrEmpty()) {
                    binding.oneImageTwoTextViewTitleTv.text = title
                }
                val subTitle = getString(R.styleable.OneImageTwoTextView_text)
                if (!subTitle.isNullOrEmpty()) {
                    binding.oneImageTwoTextViewSubtitleTv.text = subTitle
                }

            } finally {
                recycle()
            }
        }
    }
}