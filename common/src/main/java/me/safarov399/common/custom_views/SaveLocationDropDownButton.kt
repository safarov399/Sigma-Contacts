package me.safarov399.common.custom_views

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import me.safarov399.common.databinding.SaveLocationDropDownButtonBinding

class SaveLocationDropDownButton @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attr, defStyleAttr) {
    private val binding =
        SaveLocationDropDownButtonBinding.inflate(LayoutInflater.from(context), this, true)


    @SuppressLint("DiscouragedApi")
    fun setDropDownResource(logo: String) {
        binding.saveLocationDropDownViewDown.setImageResource(
            context.resources.getIdentifier(
                logo, "drawable", context.packageName
            )
        )
    }
}