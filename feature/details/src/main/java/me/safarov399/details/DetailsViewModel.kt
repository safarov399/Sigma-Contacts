package me.safarov399.details

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.provider.ContactsContract
import android.provider.ContactsContract.PhoneLookup
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import me.safarov399.core.base.BaseViewModel
import me.safarov399.core.entity.ContactEntity
import me.safarov399.domain.usecase.contact.DeleteByIdContactUseCase
import me.safarov399.domain.usecase.contact.GetByIdContactUseCase
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getByIdContactUseCase: GetByIdContactUseCase,
    private val deleteByIdContactUseCase: DeleteByIdContactUseCase,
    @ApplicationContext private val context: Context
): BaseViewModel<DetailsState, DetailsEffect,DetailsEvent>() {
    override fun getInitialState(): DetailsState = DetailsState(
        ContactEntity(
            numbers = mutableListOf(""),
            emails = mutableListOf("")
        )
    )

    override fun onEventUpdate(event: DetailsEvent) {
        when(event) {
            is DetailsEvent.LoadContact -> {
                viewModelScope.launch(Dispatchers.IO) {
                    getByIdContactUseCase.invoke(event.id).onEach {
                        setState(
                            getCurrentState().copy(
                                contact = it
                            )
                        )
                    }.collect()
                }
            }

            is DetailsEvent.DeleteContact -> {
                viewModelScope.launch(Dispatchers.IO) {
                    deleteContactFromRoom(event.id)
                    deleteContactHelper(context, event.number, event.displayName)
                }

            }
        }
    }

    private fun deleteContactFromRoom(id: Long) {
        deleteByIdContactUseCase.invoke(id)
    }

    //TODO("Deletion of contact that does not have phone number will be supported later on.")
    @SuppressLint("Range")
    private fun deleteContactHelper(ctx: Context, number: String?, name: String?): Boolean {
        val contactUri = Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI, Uri.encode(number))
        val cur = ctx.contentResolver.query(contactUri, null, null, null, null)
        try {
            if (cur!!.moveToFirst()) {
                do {
                    if (cur.getString(cur.getColumnIndex(PhoneLookup.DISPLAY_NAME)).equals(name, ignoreCase = true)) {
                        val lookupKey = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY))
                        val uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_LOOKUP_URI, lookupKey)
                        ctx.contentResolver.delete(uri, null, null)
                        return true
                    }
                } while (cur.moveToNext())
            }
        } catch (e: Exception) {
            println(e.stackTrace)
        }
        finally {
            cur!!.close()
        }
        return false
    }
}