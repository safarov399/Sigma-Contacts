package me.safarov399.home

import android.annotation.SuppressLint
import android.content.Context
import android.provider.ContactsContract
import dagger.hilt.android.qualifiers.ApplicationContext
import me.safarov399.core.base.BaseViewModel
import me.safarov399.core.entity.ContactEntity
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : BaseViewModel<HomeState, HomeEffect, HomeEvent>() {
    override fun getInitialState(): HomeState = HomeState(arrayListOf())

    override fun onEventUpdate(event: HomeEvent) {
        when(event) {
            HomeEvent.ReadContacts -> readContacts()
            HomeEvent.LoadHomeViewModel -> TODO()
        }
    }

    @SuppressLint("Range")
    private fun readContacts() {
        val contactsList = arrayListOf<ContactEntity>()
        val cursor = context.contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI, null, null, null, null
        )
        if ((cursor?.count ?: 0) > 0) {
            while (cursor!!.moveToNext()) {

                val id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                val name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                val phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)).toInt()
                if (phoneNumber > 0) {
                    val cursorPhoneNumber = context.contentResolver.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " =?",
                        arrayOf(id),
                        null
                    )
                    if ((cursorPhoneNumber?.count ?: 0) > 0) {
                        while (cursorPhoneNumber!!.moveToNext()) {
                            val phoneNumber = cursorPhoneNumber.getString(
                                cursorPhoneNumber.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                            )
                            contactsList.add(ContactEntity(contactsId = id, name = name, number = phoneNumber))
                        }
                    }
                    cursorPhoneNumber?.close()
                }
            }
        }
        cursor?.close()

    }
}