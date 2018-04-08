package com.bhaicompany.kotlinarchitecturalcomponents

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull
import android.support.v7.util.DiffUtil



@Entity(tableName = "user")
data class User(@PrimaryKey(autoGenerate = true) val uid: Int = 0, var name: String, var age: Int, var gender: String)
