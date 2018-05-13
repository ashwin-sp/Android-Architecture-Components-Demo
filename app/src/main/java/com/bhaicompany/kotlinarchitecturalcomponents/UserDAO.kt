package com.bhaicompany.kotlinarchitecturalcomponents

import android.arch.lifecycle.LiveData
import android.arch.paging.DataSource
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query


@Dao
interface UserDAO {
    @get:Query("SELECT * FROM user")
    val all: LiveData<List<User>>

    @Query("SELECT * FROM User")
    fun usersAll(): DataSource.Factory<Int, User>

    @Query("SELECT * FROM user where name LIKE :name")
    fun findByName(name: String): User

    @Query("SELECT * FROM user where name LIKE :name")
    fun findByNameList(name: String): DataSource.Factory<Int, User>

    @Query("UPDATE user SET name = :name where uid = :id")
    fun updateName(name: String,id: Int)

    @Query("SELECT COUNT(*) from user")
    fun countUsers(): Int

    @Insert
    fun insertAll(vararg users: User)

    @Delete
    fun delete(user: User)
}