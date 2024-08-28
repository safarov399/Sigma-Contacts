package me.safarov399.home

import android.annotation.SuppressLint
import android.content.Context
import android.provider.ContactsContract
import androidx.lifecycle.viewModelScope
import androidx.security.crypto.EncryptedSharedPreferences
import me.safarov399.domain.usecase.contact.GetAllContactsUseCase
import me.safarov399.domain.usecase.contact.InsertAllContactsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import me.safarov399.core.SharedPreferencesManager
import me.safarov399.core.base.BaseViewModel
import me.safarov399.core.entity.ContactEntity
import me.safarov399.core.entity.SaveLocationEntity
import me.safarov399.domain.usecase.save_location.InsertSaveLocationUseCase
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    @ApplicationContext private val context: Context, private val insertContactsUseCase: InsertAllContactsUseCase, private val getAllContactsUseCase: GetAllContactsUseCase, private val sharedPreferences: EncryptedSharedPreferences, private val insertSaveLocationUseCase: InsertSaveLocationUseCase
) : BaseViewModel<HomeState, HomeEffect, HomeEvent>() {

    override fun getInitialState(): HomeState = HomeState(arrayListOf())

    override fun onEventUpdate(event: HomeEvent) {
        when (event) {
            HomeEvent.ReadContacts -> viewModelScope.launch(Dispatchers.IO) {
                if (sharedPreferences.getBoolean(SharedPreferencesManager.IS_FIRST_LAUNCH, true)) {
                    insertContactsToDatabase()
                    sharedPreferences.edit().putBoolean(SharedPreferencesManager.IS_FIRST_LAUNCH, false).apply()
                }
                getAllContactsUseCase.invoke().onEach {
                    setState(
                        getCurrentState().copy(
                            contactEntity = it
                        )
                    )
                }.collect()
            }

            HomeEvent.InsertSaveLocation -> {
                viewModelScope.launch(Dispatchers.IO) {
                    insertSaveLocationUseCase.invoke(
                        SaveLocationEntity(
                            logo = "add", title = "Device"
                        )
                    )
                }
            }
        }
    }


    @SuppressLint("Range")
    private fun readContacts(): Flow<ArrayList<ContactEntity>> {
        val contactsMap = mutableMapOf<String, ContactEntity>() // Use a map to store contacts by ID

        val cursor = context.contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI, null, null, null, ContactsContract.Contacts.DISPLAY_NAME + " ASC"
        )

        if ((cursor?.count ?: 0) > 0) {
            while (cursor!!.moveToNext()) {
                val id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                val name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                val hasPhoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)).toInt()
                val emails = mutableListOf<String>()
                val emailCursor = context.contentResolver.query(
                    ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?", arrayOf(id), null
                )
                if (emailCursor != null && emailCursor.count > 0) {
                    while (emailCursor.moveToNext()) {
                        val email = emailCursor.getString(emailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS))

                        emails.add(email)
                    }
                }
                emailCursor?.close()

                if (hasPhoneNumber > 0) {
                    val cursorPhoneNumber = context.contentResolver.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", arrayOf(id), null
                    )

                    if ((cursorPhoneNumber?.count ?: 0) > 0) {
                        while (cursorPhoneNumber!!.moveToNext()) {
                            val phoneNumber = cursorPhoneNumber.getString(
                                cursorPhoneNumber.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                            )

                            // Check if the contact already exists in the map
                            val contactEntity = contactsMap[id]
                            if (contactEntity != null) {
                                // If it exists, add the new phone number to the existing contact
                                contactEntity.numbers.add(phoneNumber)
                                contactEntity.emails.addAll(emails)
                            } else {
                                // If it doesn't exist, create a new contact and add it to the map
                                val newContact = ContactEntity(
                                    contactsId = id, firstName = name, numbers = mutableListOf(phoneNumber), // Initialize with the first phone number
                                    color = null, emails = emails,
                                )
                                contactsMap[id] = newContact
                            }
                        }
                    }
                    cursorPhoneNumber?.close()
                }
                else if (emails.isNotEmpty()) { // Handle contacts with only emails
                    val contactEntity = contactsMap[id]
                    if (contactEntity != null) {
                        contactEntity.emails.addAll(emails)
                    } else {
                        val newContact = ContactEntity(
                            contactsId = id,
                            firstName = name,
                            numbers = mutableListOf(),
                            emails = emails,
                            color = null
                        )
                        contactsMap[id] = newContact
                    }
                }
            }
        }
        cursor?.close()

        // Convert the map values to a list
        val contactsList = ArrayList(contactsMap.values)

        // Sort the contactsList
        val azerbaijaniComparator = compareBy<ContactEntity> { it.firstName }.thenBy { it.firstName.lowercase(Locale("az")) }

        contactsList.sortWith(azerbaijaniComparator)

        return flow {
            emit(contactsList)
        }
    }

    private fun insertContactsToDatabase() {
        viewModelScope.launch(Dispatchers.IO) {
            readContacts().collect {
                insertContactsUseCase.invoke(it)
            }
        }
    }
}