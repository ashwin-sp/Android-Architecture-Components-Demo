package com.bhaicompany.kotlinarchitecturalcomponents

import android.arch.lifecycle.ViewModel
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import android.arch.lifecycle.LiveData

import android.app.Application


class UserViewModel(application : Application)  : ViewModel(){
    val dao = AppDatabase.getAppDatabase(application).userDao()

    fun initPagerList() : LiveData<PagedList<User>>
    {
        val pagedListConfig = PagedList.Config.Builder().setEnablePlaceholders(true)
                .setPrefetchDistance(50)
                .setPageSize(100).build()
        return LivePagedListBuilder(dao.usersAll(), pagedListConfig)
                .build()
    }

    private fun addUser(user: User): User {
        dao.insertAll(user)
        return user
    }

    fun populateWithTestData() {
        var user = User(name = "Ajay", age = 20, gender = "male")
        addUser(user)
        user = User(name = "Shyam", age = 21, gender = "male")
        addUser(user)
    }

    fun countUsers() = dao.countUsers()

    fun updateUser(name: String, id: Int) = dao.updateName(name, id)

    fun getUsersList() = dao.all
}