package com.stevecampos.composecleansample.data.local.orm

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.stevecampos.composecleansample.data.entities.GetPostsResponse
import com.stevecampos.composecleansample.data.entities.GetUsersResponse
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("select * from userTable")
    fun getUsers(): Flow<List<GetUsersResponse>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUsers(users: List<GetUsersResponse>)


    @Query("select * from GetPostsResponse where userId = :userId")
    fun getPosts(userId: Int): Flow<List<GetPostsResponse>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPosts(posts: List<GetPostsResponse>)
}