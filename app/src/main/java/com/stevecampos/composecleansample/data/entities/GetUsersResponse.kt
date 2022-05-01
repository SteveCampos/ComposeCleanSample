package com.stevecampos.composecleansample.data.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "userTable")
data class GetUsersResponse(
    @PrimaryKey
    val id: Int,
    val name: String,
    val username: String,
    val email: String,
    @Embedded
    val address: Address,
    val phone: String,
    val website: String,
    @Embedded
    val company: Company
) : Parcelable

@Entity
@Parcelize
data class Company(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "company_id")
    val id: Int,
    @ColumnInfo(name = "company_name")
    val name: String,
    val catchPhrase: String,
    val bs: String
) : Parcelable


@Entity
@Parcelize
data class Address(
    val street: String,
    val suite: String,
    val city: String,
    val zipcode: String,
    @Embedded
    val geo: Geo
) : Parcelable


@Entity
@Parcelize
data class Geo(
    val lat: Double,
    val lng: Double
) : Parcelable
