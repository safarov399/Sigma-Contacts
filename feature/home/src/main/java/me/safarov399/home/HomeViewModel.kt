package me.safarov399.home

import android.annotation.SuppressLint
import android.content.Context
import android.provider.ContactsContract
import androidx.lifecycle.viewModelScope
import androidx.security.crypto.EncryptedSharedPreferences
import com.safarov399.domain.usecase.GetAllContactsUseCase
import com.safarov399.domain.usecase.InsertAllContactsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import me.safarov399.SharedPreferencesManager
import me.safarov399.core.base.BaseViewModel
import me.safarov399.core.entity.ContactEntity
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val insertContactsUseCase: InsertAllContactsUseCase,
    private val getAllContactsUseCase: GetAllContactsUseCase,
    private val sharedPreferences: EncryptedSharedPreferences
) : BaseViewModel<HomeState, HomeEffect, HomeEvent>() {
    override fun getInitialState(): HomeState = HomeState(arrayListOf())

    override fun onEventUpdate(event: HomeEvent) {
        when (event) {
            HomeEvent.ReadContacts -> viewModelScope.launch(Dispatchers.IO) {
                if (sharedPreferences.getBoolean(SharedPreferencesManager.IS_FIRST_LAUNCH, true)) {
                    insertContactsToDatabase()
                    sharedPreferences.edit()
                        .putBoolean(SharedPreferencesManager.IS_FIRST_LAUNCH, false).apply()
                }
                getAllContactsUseCase.invoke().onEach {
                    setState(
                        getCurrentState().copy(
                            contactEntity = it
                        )
                    )
                }.collect()
            }
        }
    }


    @SuppressLint("Range")
    private fun readContacts(): Flow<ArrayList<ContactEntity>> {
        val contactsList = arrayListOf<ContactEntity>()
        val cursor = context.contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            null,
            null,
            null,
            ContactsContract.Contacts.DISPLAY_NAME + " ASC"
        )
        if ((cursor?.count ?: 0) > 0) {
            while (cursor!!.moveToNext()) {

                val id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                val name =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                val phoneNumber =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))
                        .toInt()
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
                            contactsList.add(
                                ContactEntity(
                                    contactsId = id,
                                    name = name,
                                    number = phoneNumber
                                )
                            )
                        }
                    }
                    cursorPhoneNumber?.close()
                }
            }
        }
        cursor?.close()
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