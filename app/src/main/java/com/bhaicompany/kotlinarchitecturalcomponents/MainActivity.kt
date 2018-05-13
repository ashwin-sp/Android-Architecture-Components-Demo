package com.bhaicompany.kotlinarchitecturalcomponents

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import com.bhaicompany.kotlinarchitecturalcomponents.fragments.ListingFragment
import com.bhaicompany.kotlinarchitecturalcomponents.fragments.StartFragment
import com.bhaicompany.kotlinarchitecturalcomponents.fragments.WelcomeFragment


class MainActivity : AppCompatActivity(), ListingFragment.OnFragmentInteractionListener, StartFragment.OnFragmentInteractionListener, WelcomeFragment.OnFragmentInteractionListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onDestroy() {
        AppDatabase.destroyInstance()
        super.onDestroy()
    }

    override fun onFragmentInteraction(string: String) {
        println("Log state ==> $string")
        supportActionBar?.title = string
    }

    override fun onSupportNavigateUp(): Boolean = Navigation.findNavController(this, R.id.nav_host).navigateUp()

}





