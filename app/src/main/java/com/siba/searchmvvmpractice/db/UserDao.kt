package com.siba.searchmvvmpractice.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.siba.searchmvvmpractice.vo.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user : User)

    @Query("SELECT * FROM user WHERE login = :login")
    fun findByLogin(login : String) : LiveData<User>
}