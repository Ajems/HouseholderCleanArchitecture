package com.example.householderca.presentation.recyclerViewAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import com.example.householderca.R
import com.example.householderca.databinding.ShopItemDisabledBinding
import com.example.householderca.databinding.ShopItemEnabledBinding
import com.example.householderca.domain.pojo.ShopItem
import com.example.householderca.presentation.diffUtil.ShopItemDiffCallback

class ShopListAdapter : ListAdapter<ShopItem, ShopItemViewHolder>(ShopItemDiffCallback()) {

    companion object {
        const val VIEW_TYPE_DISABLED = 101
        const val VIEW_TYPE_ENABLED = 102
        const val MAX_POOL_SIZE = 7
    }

    var onShopItemLongClickListener: ((ShopItem) -> Unit)? = null
    var onShopItemClickListener: ((ShopItem) -> Unit)? = null

    private var viewType = hashMapOf<Boolean, Int>(
        false to VIEW_TYPE_DISABLED,
        true to VIEW_TYPE_ENABLED
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val layoutResource = when(viewType){
            VIEW_TYPE_ENABLED -> R.layout.shop_item_enabled
            VIEW_TYPE_DISABLED -> R.layout.shop_item_disabled
            else -> throw RuntimeException("Unknown view type: $viewType")
        }
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            layoutResource,
            parent,
            false
        )
        return ShopItemViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ShopItemViewHolder, position: Int) {
        val shopItem = getItem(position)
        val binding = viewHolder.binding
        binding.root.setOnLongClickListener {
            onShopItemLongClickListener?.invoke(shopItem)
            true
        }

        binding.root.setOnClickListener {
            onShopItemClickListener?.invoke(shopItem)
        }

        when (binding){
            is ShopItemDisabledBinding -> {
                binding.shopItem = shopItem
            }
            is ShopItemEnabledBinding -> {
                binding.shopItem = shopItem
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        viewType[getItem(position).enabled]?.let { return it }
        return VIEW_TYPE_DISABLED
    }
}