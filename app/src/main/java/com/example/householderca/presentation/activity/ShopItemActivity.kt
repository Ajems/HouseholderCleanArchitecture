package com.example.householderca.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.householderca.R
import com.example.householderca.presentation.viewModel.ShopItemViewModel

class ShopItemActivity : AppCompatActivity() {

    private lateinit var viewModel: ShopItemViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)
    }
}