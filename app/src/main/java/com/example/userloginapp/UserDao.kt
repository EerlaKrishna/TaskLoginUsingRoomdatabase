package com.example.userloginapp

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    @Query("SELECT * FROM users WHERE username = :username")
    suspend fun getUserByUsername(username: String): User?


    @Query("SELECT * FROM users WHERE id = :userId")
    suspend fun getUserById(userId: Int): User?


    @Query("SELECT * FROM users")
    suspend fun getAllUsers(): List<User>
}
