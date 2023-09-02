package com.example.householderca.presentation.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.householderca.R
import com.example.householderca.databinding.ActivityMainBinding
import com.example.householderca.presentation.recyclerViewAdapter.ShopListAdapter
import com.example.householderca.presentation.fragment.ShopItemFragment
import com.example.householderca.presentation.viewModel.MainViewModel

class MainActivity : AppCompatActivity(), ShopItemFragment.OnEditingFinishedListener {

    private lateinit var viewModel: MainViewModel
    private lateinit var shopListAdapter: ShopListAdapter
    private lateinit var binding: ActivityMainBinding
    private var statusBarHeight: Int? = null
    private var navigationBarHeight: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpRecyclerView()
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.shopList.observe(this) {
            shopListAdapter.submitList(it)
        }
        addButtonAddClickListener()
        setEdgeToEdgeWindow()
    }

    private fun isOnePaneMode(): Boolean {
        return binding.shopItemContainer == null
    }

    private fun launchFragment(fragment: Fragment){
        supportFragmentManager.popBackStack()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.shop_item_container ,fragment)
            .addToBackStack("shop_item_fragment_stack")
            .commit()
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
        val layoutParamsButtonAdd = binding.addShopItem.layoutParams as ViewGroup.MarginLayoutParams
        navigationBarHeight?.let { height ->
            layoutParamsButtonAdd.bottomMargin += height
        }
        binding.addShopItem.layoutParams = layoutParamsButtonAdd

        if(statusBarHeight != null && navigationBarHeight != null) {
            binding.recyclerViewShopList.setPadding(
                0,
                statusBarHeight!!,
                0,
                navigationBarHeight!!+binding.recyclerViewShopList.paddingBottom
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
        binding.addShopItem.setOnClickListener {
            if (isOnePaneMode()) {
                val intent = ShopItemActivity.newIntentAddItem(this)
                startActivity(intent)
            } else {
                launchFragment(ShopItemFragment.newInstanceAddItem())
            }
        }
    }

    private fun setUpRecyclerView(){
        with(binding.recyclerViewShopList) {
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

        setupSwipeClickListener(binding.recyclerViewShopList)
    }

    private fun setupSwipeClickListener(recyclerViewShopList: RecyclerView?) {
        val callback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or
                    ItemTouchHelper.RIGHT
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
            if (isOnePaneMode()) {
                val intent = ShopItemActivity.newIntentEditItem(this, it.id)
                startActivity(intent)
            } else {
                launchFragment(ShopItemFragment.newInstanceEditItem(it.id))
            }
        }
    }

    private fun setupLongClickListener() {
        shopListAdapter.onShopItemLongClickListener = {
            viewModel.changeEnableState(it)
        }
    }

    override fun onEditingFinished() {
        Toast.makeText(this, "Success editing", Toast.LENGTH_SHORT).show()
        supportFragmentManager.popBackStack()
    }
}