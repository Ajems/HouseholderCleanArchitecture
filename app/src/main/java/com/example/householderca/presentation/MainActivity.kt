package com.example.householderca.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.householderca.R

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    //private var count = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.shopList.observe(this){
            Log.d("MainActivityTest", it.toString())
            //if (count <= 10) {
            //    val item = it[0]
            //    count++
            //    viewModel.changeEnabledState(item)
            //    viewModel.removeShopItem(item)
            //}
        }
    }
}