package com.example.fadeyin.konturtest

import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import com.example.fadeyin.konturtest.fragments.ScheduleFragment.Companion.errors



class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        errors = if (!verifyAvailableNetwork(this))
            2
        else
            0
            Navigation.findNavController(this, R.id.nav_host)
    }

    private fun verifyAvailableNetwork(activity:AppCompatActivity):Boolean{
        val connectivityManager=activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo=connectivityManager.activeNetworkInfo
        return  networkInfo!=null && networkInfo.isConnected
    }

}
