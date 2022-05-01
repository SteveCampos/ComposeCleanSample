package com.stevecampos.composecleansample.data.local.orm

import androidx.room.Database
import androidx.room.RoomDatabase
import com.stevecampos.composecleansample.data.entities.GetPostsResponse
import com.stevecampos.composecleansample.data.entities.GetUsersResponse

@Database(
    entities = [GetUsersResponse::class, GetPostsResponse::class],
    version = 2,
    exportSchema = false
)
abstract class AppDb : RoomDatabase() {
    abstract val userDao: UserDao
}