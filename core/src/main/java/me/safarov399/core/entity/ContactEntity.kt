package me.safarov399.core.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import me.safarov399.core.base.BaseEntity
import java.time.LocalDate

@Entity("contacts")
data class ContactEntity(

    @PrimaryKey(true) @ColumnInfo override var id: Long = 0,
    @ColumnInfo val contactsId: String = "",

    @ColumnInfo val namePrefix: String = "",
    @ColumnInfo val firstName: String = "",
    @ColumnInfo val middleName: String = "",
    @ColumnInfo val lastName: String = "",
    @ColumnInfo val nameSuffix: String = "",
    @ColumnInfo val phoneticLastName: String = "",
    @ColumnInfo val phoneticMiddleName: String = "",
    @ColumnInfo val phoneticFirstName: String = "",
    @ColumnInfo val nickname: String = "",

    @ColumnInfo val company: String = "",
    @ColumnInfo val department: String = "",
    @ColumnInfo val companyTitle: String = "",

    @ColumnInfo val number: String = "",
    @ColumnInfo val phoneLabel: String = "",

    @ColumnInfo val email: String = "",
    @ColumnInfo val emailLabel: String = "",

    @ColumnInfo val address: String = "",
    @ColumnInfo val addressLabel: String = "",

    @ColumnInfo val website: String = "",

    @ColumnInfo val significantDateAndLabel: MutableList<Pair<LocalDate, String>> = mutableListOf(),

    @ColumnInfo val relatedPerson: String = "",
    @ColumnInfo val relatedPersonType: String = "",

    @ColumnInfo val sip: String = "",

    @ColumnInfo val notes: String = "",

    @ColumnInfo val generalLabel: String = "",

    @ColumnInfo val profilePhoto: String = ""
): BaseEntity
