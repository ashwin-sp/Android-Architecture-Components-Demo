package com.bhaicompany.kotlinarchitecturalcomponents

import android.arch.lifecycle.Observer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.coroutines.experimental.launch
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.experimental.async
import com.bhaicompany.kotlinarchitecturalcomponents.util.UI


class MainActivity : AppCompatActivity() {


    // Initialisations
    private val userViewModel: UserViewModel by lazy {
        ViewModelProviders.of(this, ViewModelFactory(this.application)).get(UserViewModel::class.java)
    }
    private val adapter = UserAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initRecyclerView()
        val listData = async {
            userViewModel.initPagerList()
        }
        launch {
            val ryList =   listData.await()
            ryList.observe(this@MainActivity, Observer {
                   launch(UI) {
                       adapter.submitList(it)
                   }
            })
            //  println( "count ==> "+ userViewModel.countUsers())
            repeat(500)
            {
                userViewModel.populateWithTestData()
            }
        }
    }

    private fun initRecyclerView()
    {
        val llm = LinearLayoutManager(this@MainActivity)
        llm.orientation = LinearLayoutManager.VERTICAL
        recycler_view.layoutManager = llm
        recycler_view.adapter = adapter
    }

    // for info counter
    private fun getUserCount()
    {
        userViewModel.countUsers()
    }

    override fun onDestroy() {
        AppDatabase.destroyInstance()
        super.onDestroy()
    }
}





