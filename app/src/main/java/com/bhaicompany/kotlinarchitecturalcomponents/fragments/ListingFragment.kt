package com.bhaicompany.kotlinarchitecturalcomponents.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bhaicompany.kotlinarchitecturalcomponents.R
import com.bhaicompany.kotlinarchitecturalcomponents.UserAdapter
import com.bhaicompany.kotlinarchitecturalcomponents.UserViewModel
import com.bhaicompany.kotlinarchitecturalcomponents.util.UI
import kotlinx.android.synthetic.main.fragment_listing.*
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch



/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ListingFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ListingFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */



class ListingFragment : Fragment() {

    private var listener: OnFragmentInteractionListener? = null
    private val userViewModel: UserViewModel by lazy {
        ViewModelProviders.of(activity!!).get(UserViewModel::class.java)
    }
    private val adapter = UserAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        listener?.onFragmentInteraction("I am in listing Fragment!")
        return inflater.inflate(R.layout.fragment_listing, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        queryText.text = "Loading ..."
        if(arguments?.getString("myarg") != null)
        {
            println("Deeplinked ==> "+arguments?.getString("myarg"))
            val args = arguments?.getString("myarg")

            val listData = async {
                args?.let { userViewModel.initPagerList(it) }
            }
            launch {
                val ryList =   listData.await()
                activity?.let {
                    ryList?.observe(it, Observer {
                        launch(UI) {
                            println("Count ==> "+ it?.size)
                            adapter.submitList(it)
                            queryText.text = "Query:" + args
                        }
                    })
                }
                //  println( "count ==> "+ userViewModel.countUsers())

            }

        }
        else
        {
            val args = ListingFragmentArgs.fromBundle(arguments)

            val listData = async {
                userViewModel.initPagerList(args.query)
            }
            launch {
                val ryList =   listData.await()
                activity?.let {
                    ryList.observe(it, Observer {
                        launch(UI) {
                            println("Count ==> "+ it?.size)
                            adapter.submitList(it)
                            queryText.text = "Query:" + args.query
                        }
                    })
                }
                //  println( "count ==> "+ userViewModel.countUsers())

            }

        }


        // Run this once for test data
        launch {
            repeat(500)
            {
                userViewModel.populateWithTestData()
            }
        }
    }



    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(string: String)
    }


    private fun initRecyclerView()
    {
        val llm = LinearLayoutManager(activity)
        llm.orientation = LinearLayoutManager.VERTICAL
        recycler_view.layoutManager = llm
        recycler_view.adapter = adapter
    }

    // for info counter
    private fun getUserCount()
    {
        userViewModel.countUsers()
    }

}
