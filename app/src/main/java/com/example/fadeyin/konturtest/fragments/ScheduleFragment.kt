package com.example.fadeyin.konturtest.fragments

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.CountDownTimer

import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ProgressBar
import android.widget.SearchView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.fadeyin.konturtest.R
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fadeyin.konturtest.models.InterfaceAPI
import com.example.fadeyin.konturtest.models.User
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.schedule_fragment.*

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar


class ScheduleFragment : Fragment() {
    private var adapter: ScheduleAdapter? = null
    private var rv: RecyclerView? = null
    private var mSwipeRefreshLayout: SwipeRefreshLayout? = null
    private var searchView: SearchView? = null
    companion object {
        fun newInstance() = ScheduleFragment()
        var searchArrayList = ArrayList<User>()
        var errTxt:String = ""
        var errors: Int = 0
        var currentUsers: ArrayList<User>? = null
        var currentTime: Long = 60000 //milliseconds
        const val t: Long = 0
        val timer = object: CountDownTimer(currentTime, 1000) {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() {
                currentTime = 0
            }
        }
    }

    private lateinit var viewModel: ScheduleViewModel

    @SuppressLint("CheckResult")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.schedule_fragment, container, false)
        val progressBar = v.findViewById<ProgressBar>(R.id.progressBar)
        adapter = ScheduleAdapter { userItem: User ->
            userItemClicked(userItem)
        }
        rv = v.findViewById(R.id.rv_user_list) as RecyclerView
        rv?.layoutManager = LinearLayoutManager(v.context, RecyclerView.VERTICAL, false)
        rv?.adapter = adapter
        if((currentUsers == null || currentTime == t) && !checkErrors()) {
            getUsers()
            currentTime = 10000
        }
        else{
            currentUsers?.let { adapter!!.setUserList(it)
            progressBar.visibility = ProgressBar.INVISIBLE
            }
        }
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ScheduleViewModel::class.java)
        timer.start()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)
        if (checkErrors()) {
            Snackbar.make(view, errTxt, Snackbar.LENGTH_SHORT).show()
            progressBar.visibility = ProgressBar.INVISIBLE
        }
      searchView = view.findViewById(R.id.search) as SearchView
      searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
          override fun onQueryTextSubmit(query: String): Boolean {
              Log.d(TAG, "onQueryTextSubmit: $query")
              adapter?.setUserList(searchArrayList)
              return false
          }

          override fun onQueryTextChange(newText: String): Boolean {
              adapter?.filter(newText)
              Log.d(TAG, "onQueryTextChange: $newText" )
              return false
          }
      })
      mSwipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout) as SwipeRefreshLayout
      mSwipeRefreshLayout!!.setOnRefreshListener {
          progressBar.visibility = ProgressBar.VISIBLE
              getUsers()
          if (checkErrors()){
              Snackbar.make(view, errTxt, Snackbar.LENGTH_SHORT).show()
              progressBar.visibility = ProgressBar.INVISIBLE
          }
          mSwipeRefreshLayout!!.isRefreshing = false
      }
    }

    @SuppressLint("CheckResult")
    private fun getUsers(){
        currentUsers = null
        val apiService = InterfaceAPI.createService()
        apiService.getUser1()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                currentUsers = joinLists(currentUsers,result)
                errors = 0
            }, { error ->
                errors = 1
                error.printStackTrace()
            }
            )
        apiService.getUser2()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                currentUsers = joinLists(currentUsers,result)
                errors = 0
            }, { error ->
                errors = 1
                error.printStackTrace()
            }
            )
        apiService.getUser3()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                currentUsers = joinLists(currentUsers,result)
                adapter?.setUserList(currentUsers!!)
                errors = 0
                progressBar.visibility = ProgressBar.INVISIBLE
            }, { error ->
                errors = 1
                error.printStackTrace()
            }
            )
    }

    private fun userItemClicked(userItem : User) {
        view?.let { sendArguments(it, userItem) }
    }

    private fun sendArguments(view: View, userItem: User) {
        val action: ScheduleFragmentDirections.ActionToProfile =
            ScheduleFragmentDirections.actionToProfile(userItem)
        action.profile = userItem
        Navigation.findNavController(view).navigate(action)
    }

    private fun <T> joinLists(listA: ArrayList<T>?, listB: ArrayList<T>?): ArrayList<T>? {
        if (listA == null) {
            if (listB != null) {
                return listB
            }
        }
        if (listB == null) {
            if (listA != null) {
                return listA
            }
        }
        val result = ArrayList<T>(
                    listA!!.size + listB!!.size
                )
                result.addAll(listA)
                result.addAll(listB)
                return result
            }

    private fun checkErrors():Boolean{
        errTxt = when (errors) {
            1 -> "Ошибка загрузки данных"
            2 -> "Нет подключения к интернету"
            else -> return false
        }
        return true
    }
}


