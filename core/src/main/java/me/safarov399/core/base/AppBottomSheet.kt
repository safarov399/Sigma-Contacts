package me.safarov399.core.base

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import me.safarov399.core.databinding.BaseBottomSheetBinding

class AppBottomSheet(
    listener: AppBottomSheetListener,
    private val fragmentFactory: (() -> Fragment)? = null
) : BottomSheetDialogFragment() {
    private var binding: BaseBottomSheetBinding? = null
    private var mListener: AppBottomSheetListener? = null

    init {
        this.mListener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BaseBottomSheetBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            mListener = context as AppBottomSheetListener?
        } catch (e: ClassCastException) {
            e.printStackTrace()
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        mListener?.onBottomSheetClosedOrDismissed()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (fragmentFactory == null) {
            dismiss()

        } else {
            binding?.apply {
                childFragmentManager.beginTransaction().apply {
                    add(baseBottomSheetFragment.id, fragmentFactory.invoke())
                }.commit()
            }
        }
    }


    interface AppBottomSheetListener {
        fun onBottomSheetClosedOrDismissed()
    }
}