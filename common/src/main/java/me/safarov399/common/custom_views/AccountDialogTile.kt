package me.safarov399.common.custom_views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import me.safarov399.common.R
import me.safarov399.common.databinding.AccountDialogTileBinding

class AccountDialogTile @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attr, defStyleAttr) {
    private val binding = AccountDialogTileBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        context.theme.obtainStyledAttributes(
            attr,
            R.styleable.AccountDialogTile,
            0, 0
        ).apply {
            try {
                val imageResId = getResourceId(R.styleable.AccountDialogTile_imageSrc, 0)
                if (imageResId != 0) {
                    binding.accountDialogTileIv.setImageResource(imageResId)
                }
                val text = getString(R.styleable.AccountDialogTile_text)
                if (!text.isNullOrEmpty()) {
                    binding.accountDialogTileTv.text = text
                }
            } finally {
                recycle()
            }
        }
    }
}