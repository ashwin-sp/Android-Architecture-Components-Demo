package com.bhaicompany.kotlinarchitecturalcomponents

import android.arch.lifecycle.ViewModel
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import android.arch.lifecycle.LiveData

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData


class UserViewModel(application: Application) : AndroidViewModel(application){
    val dao = AppDatabase.getAppDatabase(application).userDao()
    fun initPagerList(name: String) : LiveData<PagedList<User>>
    {
        println("Name ==> $name")
        if(name == "null" || name.trim().isEmpty()) {
            val pagedListConfig = PagedList.Config.Builder().setEnablePlaceholders(true)
                    .setPrefetchDistance(10)
                    .setPageSize(20).setEnablePlaceholders(false).build()
            return LivePagedListBuilder(dao.usersAll(), pagedListConfig)
                    .build()
        }
        else
        {
            val pagedListConfig = PagedList.Config.Builder().setEnablePlaceholders(true)
                    .setPrefetchDistance(10)
                    .setPageSize(20).setEnablePlaceholders(false).build()
            return LivePagedListBuilder(dao.findByNameList(name.toLowerCase()), pagedListConfig)
                    .build()
        }
    }

    private fun addUser(user: User): User {
        dao.insertAll(user)
        return user
    }

    fun populateWithTestData() {
        var user = User(name = "ajay", age = 20, gender = "male")
        addUser(user)
        user = User(name = "shyam", age = 21, gender = "male")
        addUser(user)
    }


    fun countUsers() = dao.countUsers()

    fun updateUser(name: String, id: Int) = dao.updateName(name, id)

    fun getUsersList() = dao.all
}