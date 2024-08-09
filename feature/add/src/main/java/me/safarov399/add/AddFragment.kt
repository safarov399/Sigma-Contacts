package me.safarov399.add

import android.app.DatePickerDialog
import android.content.ContentProviderOperation
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import dagger.hilt.android.AndroidEntryPoint
import me.safarov399.add.databinding.FragmentAddBinding
import me.safarov399.core.base.BaseFragment
import me.safarov399.core.entity.ContactEntity
import me.safarov399.core.exception.InvalidEmailTypeException
import me.safarov399.core.exception.InvalidPhoneNumberTypeException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class AddFragment : BaseFragment<FragmentAddBinding, AddViewModel, AddState, AddEffect, AddEvent>() {
    override val getViewBinding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAddBinding = { inflater, viewGroup, value ->
        FragmentAddBinding.inflate(inflater, viewGroup, value)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            addExitIv.setOnClickListener {
                activity?.onBackPressedDispatcher?.onBackPressed()
            }
            addThreeDotsIv.setOnClickListener {
                showHelpAndFeedbackPopUp(it)
            }
            addSaveButton.setOnClickListener {
                val firstName = addFirstNameTiet.text.toString()
                val lastname = addLastNameTiet.text.toString()
                val company = addCompanyTiet.text.toString()
                val phoneNumber = addCallTiet.text.toString()
                val phoneType = addPhoneActv.text.toString()
                val email = addEmailTiet.text.toString()
                val emailType = addEmailActv.text.toString()

                val contactEntity = ContactEntity(
                    firstName = firstName, lastName = lastname, company = company, number = phoneNumber, phoneLabel = phoneType, email = email, emailLabel = emailType
                )
                insertContact(contactEntity)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        initPhoneSpinner()
        initEmailSpinner()
        initDatePickerSpinner()
        initDateTypeSpinner()
    }

    private fun insertContact(contactEntity: ContactEntity) {
        val contentProviderOperation: ArrayList<ContentProviderOperation> = arrayListOf()

        val fullName = (contactEntity.firstName.trim() + " " + contactEntity.lastName.trim()).trim()
        val company = contactEntity.company
        val phoneNumber = contactEntity.number
        val email = contactEntity.email

        val phoneLabel: Int = when (contactEntity.phoneLabel) {
            "Mobile" -> ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE
            "Home" -> ContactsContract.CommonDataKinds.Phone.TYPE_HOME
            "Work" -> ContactsContract.CommonDataKinds.Phone.TYPE_WORK
            "Work Fax" -> ContactsContract.CommonDataKinds.Phone.TYPE_FAX_WORK
            "Home Fax" -> ContactsContract.CommonDataKinds.Phone.TYPE_FAX_HOME
            "Pager" -> ContactsContract.CommonDataKinds.Phone.TYPE_PAGER
            "Other" -> ContactsContract.CommonDataKinds.Phone.TYPE_OTHER
            "Custom" -> ContactsContract.CommonDataKinds.Phone.TYPE_CUSTOM
            "Callback" -> ContactsContract.CommonDataKinds.Phone.TYPE_CALLBACK
            "Car" -> ContactsContract.CommonDataKinds.Phone.TYPE_CAR
            "Company Main" -> ContactsContract.CommonDataKinds.Phone.TYPE_COMPANY_MAIN
            "ISDN" -> ContactsContract.CommonDataKinds.Phone.TYPE_ISDN
            "Main" -> ContactsContract.CommonDataKinds.Phone.TYPE_MAIN
            "Other Fax" -> ContactsContract.CommonDataKinds.Phone.TYPE_OTHER_FAX
            "Radio" -> ContactsContract.CommonDataKinds.Phone.TYPE_RADIO
            "Telex" -> ContactsContract.CommonDataKinds.Phone.TYPE_TELEX
            "TTY TTD" -> ContactsContract.CommonDataKinds.Phone.TYPE_TTY_TDD
            "Work Mobile" -> ContactsContract.CommonDataKinds.Phone.TYPE_WORK_MOBILE
            "Work Pager" -> ContactsContract.CommonDataKinds.Phone.TYPE_WORK_PAGER
            "Assistant" -> ContactsContract.CommonDataKinds.Phone.TYPE_ASSISTANT
            "MMS" -> ContactsContract.CommonDataKinds.Phone.TYPE_MMS
            else -> throw InvalidPhoneNumberTypeException("The specified phone number label is not supported.")
        }

        val emailLabel: Int = when (contactEntity.emailLabel) {
            "Home" -> ContactsContract.CommonDataKinds.Email.TYPE_HOME
            "Work" -> ContactsContract.CommonDataKinds.Email.TYPE_WORK
            "Other" -> ContactsContract.CommonDataKinds.Email.TYPE_OTHER
            "Mobile" -> ContactsContract.CommonDataKinds.Email.TYPE_MOBILE
            "Custom" -> ContactsContract.CommonDataKinds.Email.TYPE_CUSTOM
            else -> throw InvalidEmailTypeException("The specified email label is not supported.")
        }


        contentProviderOperation.add(
            ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI).withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null).withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null).build()
        )


        if (fullName.isNotBlank()) {
            contentProviderOperation.add(
                ContentProviderOperation.newInsert(
                    ContactsContract.Data.CONTENT_URI
                ).withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0).withValue(
                        ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE
                    ).withValue(
                        ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, fullName
                    ).build()
            )
        }
//        TODO("Add support for company types");
        if (company.isNotBlank()) {
            contentProviderOperation.add(
                ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI).withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0).withValue(
                        ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE
                    ).withValue(ContactsContract.CommonDataKinds.Organization.COMPANY, company).withValue(ContactsContract.CommonDataKinds.Organization.TYPE, ContactsContract.CommonDataKinds.Organization.TYPE_WORK).build()
            );
        }

        if (phoneNumber.isNotBlank()) {
            contentProviderOperation.add(
                ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI).withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0).withValue(
                        ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE
                    ).withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, phoneNumber).withValue(
                        ContactsContract.CommonDataKinds.Phone.TYPE, phoneLabel
                    ).build()
            )
        }

        if (email.isNotBlank()) {
            contentProviderOperation.add(
                ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI).withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0).withValue(
                        ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE
                    ).withValue(ContactsContract.CommonDataKinds.Email.DATA, email).withValue(ContactsContract.CommonDataKinds.Email.TYPE, emailLabel).build()
            );
        }

        try {
            requireActivity().contentResolver.applyBatch(ContactsContract.AUTHORITY, contentProviderOperation)
            postEvent(
                AddEvent.InsertContact(contactEntity)
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(requireContext(), "Exception: " + e.message, Toast.LENGTH_SHORT).show()
        }


    }

    private fun initDateTypeSpinner() {
        val dateTypes = resources.getStringArray(me.safarov399.common.R.array.add_date_types)
        val adapter = ArrayAdapter(requireContext(), me.safarov399.common.R.layout.drop_down, dateTypes)
        binding.addDateTypeActv.setAdapter(adapter)
        binding.addDateTypeActv.setText(dateTypes[0], false)
    }

    private fun initDatePickerSpinner() {
        binding.addDatePickerActv.setOnClickListener {
            showSignificantDatePicker()
        }
    }

    private fun showHelpAndFeedbackPopUp(view: View) {
        val popup = PopupMenu(requireActivity(), view)
        val popupMenuInflater = popup.menuInflater
        popupMenuInflater.inflate(me.safarov399.common.R.menu.add_menu, popup.menu)
        popup.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                me.safarov399.common.R.id.add_help_and_feedback -> {
                    Toast.makeText(requireActivity(), "Select", Toast.LENGTH_SHORT).show()
                }
            }
            true
        }
        popup.show()
    }

    private fun showSignificantDatePicker() {
        val datePickerDialog = DatePickerDialog(
            requireContext(), { _, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, monthOfYear, dayOfMonth)
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val formattedDate = dateFormat.format(selectedDate.time)
                binding.addDatePickerActv.setText(formattedDate)
            }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    private fun initEmailSpinner() {
        val emailTypes = resources.getStringArray(me.safarov399.common.R.array.add_email_types)
        val adapter = ArrayAdapter(requireContext(), me.safarov399.common.R.layout.drop_down, emailTypes)
        binding.addEmailActv.setAdapter(adapter)
        binding.addEmailActv.setText(emailTypes[0], false)
    }

    private fun initPhoneSpinner() {
        val phoneTypes = resources.getStringArray(me.safarov399.common.R.array.add_phone_types)
        val adapter = ArrayAdapter(requireContext(), me.safarov399.common.R.layout.drop_down, phoneTypes)
        binding.addPhoneActv.setAdapter(adapter)
        binding.addPhoneActv.setText(phoneTypes[0], false)
    }


    override fun getViewModelClass(): Class<AddViewModel> = AddViewModel::class.java
}