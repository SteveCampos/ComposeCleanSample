package com.stevecampos.composecleansample.data.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GetUsersResponse(
    val id: Int,
    val name: String,
    val username: String,
    val email: String,
    val address: Address,
    val phone: String,
    val website: String,
    val company: Company
) : Parcelable

@Parcelize
data class Company(
    val name: String,
    val catchPhrase: String,
    val bs: String
) : Parcelable

@Parcelize
data class Address(
    val street: String,
    val suite: String,
    val city: String,
    val zipcode: String,
    val geo: Geo
) : Parcelable

@Parcelize
data class Geo(
    val lat: Double,
    val lng: Double
) : Parcelable
