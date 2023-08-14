package com.example.householderca.presentation.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.householderca.R
import com.example.householderca.presentation.adapters.ShopListAdapter
import com.example.householderca.presentation.viewModel.MainViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var shopListAdapter: ShopListAdapter
    private lateinit var buttonAddItem: FloatingActionButton
    private lateinit var recyclerViewShopList: RecyclerView
    private var statusBarHeight: Int? = null
    private var navigationBarHeight: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpRecyclerView()
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.shopList.observe(this) {
            shopListAdapter.submitList(it)
        }
        addButtonAddClickListener()
        setEdgeToEdgeWindow()
    }

    private fun setEdgeToEdgeWindow() {
        calculateStatusAndNavigationBar()
        increaseLayoutMargin()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        if (isLightTheme(this)) {
            val systemDecorColor = (View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR)
            window.decorView.systemUiVisibility = systemDecorColor

        } else {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
        }
    }

    private fun isLightTheme(context: Context): Boolean {
        val currentNightMode = context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return currentNightMode == Configuration.UI_MODE_NIGHT_NO
    }


    private fun increaseLayoutMargin() {
        val layoutParamsButtonAdd = buttonAddItem.layoutParams as ViewGroup.MarginLayoutParams
        navigationBarHeight?.let { height ->
            layoutParamsButtonAdd.bottomMargin += height
        }
        buttonAddItem.layoutParams = layoutParamsButtonAdd

        if(statusBarHeight != null && navigationBarHeight != null) {
            recyclerViewShopList.setPadding(
                0,
                statusBarHeight!!,
                0,
                navigationBarHeight!!+recyclerViewShopList.paddingBottom
            )
        }
    }

    @SuppressLint("DiscouragedApi", "InternalInsetResource")
    private fun calculateStatusAndNavigationBar() {
        val resourceIdSB = resources.getIdentifier("status_bar_height", "dimen", "android")
        val resourceIdNB = resources.getIdentifier("navigation_bar_height", "dimen", "android")
        if (resourceIdSB > 0 && resourceIdNB > 0) {
            statusBarHeight = resources.getDimensionPixelSize(resourceIdSB)
            navigationBarHeight = resources.getDimensionPixelSize(resourceIdNB)
        }
    }

    private fun addButtonAddClickListener() {
        buttonAddItem = findViewById(R.id.addShopItem)
        buttonAddItem.setOnClickListener {
            val intent = ShopItemActivity.newIntentAddItem(this)
            startActivity(intent)
        }
    }

    private fun setUpRecyclerView(){
        recyclerViewShopList = findViewById<RecyclerView>(R.id.recyclerViewShopList)

        with(recyclerViewShopList) {
            shopListAdapter = ShopListAdapter()
            adapter = shopListAdapter
            recycledViewPool.setMaxRecycledViews(
                ShopListAdapter.VIEW_TYPE_ENABLED,
                ShopListAdapter.MAX_POOL_SIZE
            )
            recycledViewPool.setMaxRecycledViews(
                ShopListAdapter.VIEW_TYPE_DISABLED,
                ShopListAdapter.MAX_POOL_SIZE
            )
        }

        setupLongClickListener()

        setupClickListener()

        setupSwipeClickListener(recyclerViewShopList)
    }

    private fun setupSwipeClickListener(recyclerViewShopList: RecyclerView?) {
        val callback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or
                    ItemTouchHelper.RIGHT //or
                    //ItemTouchHelper.DOWN or
                    //ItemTouchHelper.UP
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = shopListAdapter.currentList[viewHolder.adapterPosition]
                viewModel.removeShopItem(item)
            }
        }

        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(recyclerViewShopList)
    }

    private fun setupClickListener() {
        shopListAdapter.onShopItemClickListener = {
            val intent = ShopItemActivity.newIntentEditItem(this, it.id )
            startActivity(intent)
        }
    }

    private fun setupLongClickListener() {
        shopListAdapter.onShopItemLongClickListener = {
            viewModel.changeEnableState(it)
        }
    }
}