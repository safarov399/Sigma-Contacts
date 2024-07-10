package me.safarov399.common.custom_views.highlights

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import me.safarov399.common.R
import me.safarov399.common.databinding.HighlightsCardViewBinding

class HighlightsCardView @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attr, defStyleAttr) {
    private val binding = HighlightsCardViewBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        context.theme.obtainStyledAttributes(
            attr,
            R.styleable.HighlightsCardView,
            0, 0
        ).apply {
            try {
                val imageResId = getResourceId(R.styleable.HighlightsCardView_imageSrc, 0)
                if (imageResId != 0) {
                    binding.higlightsCardviewIv.setImageResource(imageResId)
                }
                val title = getString(R.styleable.HighlightsCardView_title)
                if (!title.isNullOrEmpty()) {
                    binding.highlightsCardviewTitleTv.text = title
                }
                val description = getString(R.styleable.HighlightsCardView_description)
                if (!description.isNullOrEmpty()) {
                    binding.higlightsCardviewDescriptionTv.text = description
                }
                val actionButtonImage = getResourceId(R.styleable.HighlightsCardView_btnImgSrc, 0)
                if (actionButtonImage != 0) {
                    binding.higlightsCardviewActionButton.findViewById<ImageView>(R.id.highlights_action_iv).setImageResource(actionButtonImage)
                }
                val actionButtonText = getString(R.styleable.HighlightsActionButton_text)
                binding.higlightsCardviewActionButton.findViewById<TextView>(R.id.highlights_action_tv).text = actionButtonText

                val closeIcon = getBoolean(R.styleable.HighlightsCardView_closeIconEnabled, false)
                if(closeIcon) {
                    binding.highlightsCardviewCloseButton.visibility = View.VISIBLE
                }
                else {
                    binding.highlightsCardviewCloseButton.visibility = View.INVISIBLE
                }
            } finally {
                recycle()
            }
        }
    }
}