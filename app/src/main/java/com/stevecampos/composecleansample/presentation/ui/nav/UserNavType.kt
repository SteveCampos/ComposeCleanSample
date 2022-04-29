package com.stevecampos.composecleansample.presentation.ui.nav

import android.os.Bundle
import androidx.navigation.NavType
import com.google.gson.Gson
import com.stevecampos.composecleansample.data.entities.GetUsersResponse

class UserNavType : NavType<GetUsersResponse>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): GetUsersResponse? {
        return bundle.getParcelable(key)
    }

    override fun parseValue(value: String): GetUsersResponse {
        return Gson().fromJson(value, GetUsersResponse::class.java)
    }

    override fun put(bundle: Bundle, key: String, value: GetUsersResponse) {
        bundle.putParcelable(key, value)
    }
}