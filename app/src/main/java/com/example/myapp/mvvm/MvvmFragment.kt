package com.example.myapp.mvvm

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapp.R
import kotlinx.android.synthetic.main.fragment_mvvm.*


class MvvmFragment: Fragment(R.layout.fragment_mvvm) {
    private var viewManager = LinearLayoutManager(activity)
    private lateinit var viewModel: MainViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val factory = MainViewModelFactory()
        viewModel = ViewModelProvider(this,factory).get(MainViewModel::class.java)
        button1.setOnClickListener {
            addData()
        }
        initialiseAdapter()
    }

    private fun initialiseAdapter(){
        recycler.layoutManager = viewManager
        observeData()
    }

    fun observeData(){
        viewModel.lst.observe(viewLifecycleOwner, Observer{
            Log.i("data",it.toString())
            recycler.adapter= activity?.let { it1 -> NoteRecyclerAdapter(viewModel, it, it1) }
        })
    }


    @SuppressLint("NotifyDataSetChanged")
    fun addData(){
        val title=titletxt.text.toString()
        if(title.isBlank()){
            Toast.makeText(activity,"Enter value!",Toast.LENGTH_LONG).show()
        }else{
            val blog= Blog(title)
            viewModel.add(blog)
            titletxt.text.clear()
            recycler.adapter?.notifyDataSetChanged()
        }
    }
}