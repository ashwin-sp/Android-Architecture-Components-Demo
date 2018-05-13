package com.bhaicompany.kotlinarchitecturalcomponents.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import com.bhaicompany.kotlinarchitecturalcomponents.R
import com.bhaicompany.kotlinarchitecturalcomponents.StartFragmentDirections
import kotlinx.android.synthetic.main.fragment_start.*


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [StartFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [StartFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class StartFragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        listener?.onFragmentInteraction("I am in Start Fragment")
        return inflater.inflate(R.layout.fragment_start, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        play.setOnClickListener {
            val directions = StartFragmentDirections.action_startFragment_to_listingFragment();
            directions.setQuery(editText.text.toString())
            NavHostFragment.findNavController(this).navigate(directions)
        }
        editText.addTextChangedListener(object : TextWatcher
        {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })
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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment StartFragment.
         */
        @JvmStatic
        fun newInstance() = StartFragment()
    }
}
