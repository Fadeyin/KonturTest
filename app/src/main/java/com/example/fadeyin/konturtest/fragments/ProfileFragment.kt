package com.example.fadeyin.konturtest.fragments

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController


import com.example.fadeyin.konturtest.R
import java.text.SimpleDateFormat
import java.util.*




class ProfileFragment : Fragment() {

    companion object {
        fun newInstance() = ProfileFragment()
    }

    private lateinit var viewModel: ProfileViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.profile_fragment, container, false)
        v.findViewById<ImageView>(R.id.back_btn)?.setOnClickListener {
            it.findNavController().navigate(R.id.scheduleFragment)
        }
        val safeArgs = arguments?.let { ProfileFragmentArgs.fromBundle(it) }
        val flowStepNumber = safeArgs?.profile
        val name = v.findViewById<TextView>(R.id.name)
        val phone = v.findViewById<TextView>(R.id.phone)
        val bio = v.findViewById<TextView>(R.id.biography)
        val temp = v.findViewById<TextView>(R.id.temperament)
        val educationTime = v.findViewById<TextView>(R.id.educationPeriod)

        name.text = flowStepNumber?.name
        bio.text = flowStepNumber?.biography
        phone.text =  flowStepNumber?.phone
        temp.text =  flowStepNumber?.temperament.toString()
        educationTime.text = flowStepNumber?.educationPeriod?.start?.let { getFormattedDate(it) } + " - " + flowStepNumber?.educationPeriod?.end?.let { getFormattedDate(it) }
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        // TODO: Use the ViewModel
    }

    @SuppressLint("SimpleDateFormat")
    private fun getFormattedDate(rawDate: Date): String {
        val fmt = SimpleDateFormat("dd.MM.yyyy")
        return fmt.format(rawDate)
    }


}
