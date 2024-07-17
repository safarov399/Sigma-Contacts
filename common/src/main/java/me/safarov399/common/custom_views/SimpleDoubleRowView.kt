package me.safarov399.common.custom_views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import me.safarov399.common.R
import me.safarov399.common.databinding.SimpleDoubleRowViewBinding

class SimpleDoubleRowView @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attr, defStyleAttr) {

    private val binding = SimpleDoubleRowViewBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        context.theme.obtainStyledAttributes(
            attr,
            R.styleable.SimpleDoubleRowView,
            0, 0
        ).apply {
            try {
                val title = getString(R.styleable.SimpleDoubleRowView_title)
                if (!title.isNullOrEmpty()) {
                    binding.simpleDoubleRowTitleTv.text = title
                }

                val description = getString(R.styleable.SimpleDoubleRowView_description)
                if(!description.isNullOrEmpty()) {
                    binding.simpleDoubleRowDescriptionTv.text = description
                }

            } finally {
                recycle()
            }
        }
    }
}