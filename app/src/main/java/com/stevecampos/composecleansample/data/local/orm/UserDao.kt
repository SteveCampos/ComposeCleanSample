package com.stevecampos.composecleansample.data.local.orm

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.stevecampos.composecleansample.data.entities.GetUsersResponse
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("select * from userTable")
    fun getUsers(): Flow<List<GetUsersResponse>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUsers(users: List<GetUsersResponse>)
}