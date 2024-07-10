package me.safarov399.common.custom_views.highlights

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import me.safarov399.common.databinding.HighlightsAddFavoritesCardViewBinding

class HighlightsAddFavoritesCard @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attr, defStyleAttr) {
    private val binding = HighlightsAddFavoritesCardViewBinding.inflate(LayoutInflater.from(context), this, true)
}