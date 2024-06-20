package me.safarov399.add

import android.app.DatePickerDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import me.safarov399.add.databinding.FragmentAddBinding
import me.safarov399.core.base.BaseFragment
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddFragment :
    BaseFragment<FragmentAddBinding, AddViewModel, AddState, AddEffect, AddEvent>() {
    override val getViewBinding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAddBinding =
        { inflater, viewGroup, value ->
            FragmentAddBinding.inflate(inflater, viewGroup, value)
        }

    override fun onResume() {
        super.onResume()
        initPhoneSpinner()
        initEmailSpinner()
        initDatePickerSpinner()
        initDateTypeSpinner()
    }

    private fun initDateTypeSpinner() {
        val dateTypes = resources.getStringArray(me.safarov399.common.R.array.add_date_types)
        val adapter =
            ArrayAdapter(requireContext(), me.safarov399.common.R.layout.drop_down, dateTypes)
        binding.addDateTypeActv.setAdapter(adapter)
        binding.addDateTypeActv.setText(dateTypes[0], false)
    }

    private fun initDatePickerSpinner() {
        binding.addDatePickerActv.setOnClickListener {
            showSignificantDatePicker()
        }
    }

    private fun showSignificantDatePicker() {
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, monthOfYear, dayOfMonth)
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val formattedDate = dateFormat.format(selectedDate.time)
                binding.addDatePickerActv.setText(formattedDate)
            },
            Calendar.getInstance().get(Calendar.YEAR),
            Calendar.getInstance().get(Calendar.MONTH),
            Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    private fun initEmailSpinner() {
        val emailTypes = resources.getStringArray(me.safarov399.common.R.array.add_email_types)
        val adapter =
            ArrayAdapter(requireContext(), me.safarov399.common.R.layout.drop_down, emailTypes)
        binding.addEmailActv.setAdapter(adapter)
        binding.addEmailActv.setText(emailTypes[0], false)
    }

    private fun initPhoneSpinner() {
        val phoneTypes = resources.getStringArray(me.safarov399.common.R.array.add_phone_types)
        val adapter =
            ArrayAdapter(requireContext(), me.safarov399.common.R.layout.drop_down, phoneTypes)
        binding.addPhoneActv.setAdapter(adapter)
        binding.addPhoneActv.setText(phoneTypes[0], false)
    }


    override fun getViewModelClass(): Class<AddViewModel> = AddViewModel::class.java
}