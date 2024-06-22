package me.safarov399.core.base

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import me.safarov399.common.custom_views.SaveLocationDropDownButton
import me.safarov399.common.databinding.SaveLocationDropDownButtonBinding
import me.safarov399.core.databinding.BaseBottomSheetBinding

class AppBottomSheet(
    private val fragmentFactory: (() -> Fragment)? = null
): BottomSheetDialogFragment() {
    private var binding: BaseBottomSheetBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BaseBottomSheetBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(fragmentFactory == null) {
            dismiss()

        }
        else {
            binding?.apply {
                childFragmentManager.beginTransaction().apply {
                    add(baseBottomSheetFragment.id, fragmentFactory.invoke())
                }.commit()
            }
        }
    }



}