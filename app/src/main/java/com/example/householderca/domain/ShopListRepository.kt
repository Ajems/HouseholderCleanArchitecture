package com.example.householderca.domain

import androidx.lifecycle.LiveData
import com.example.householderca.domain.pojo.ShopItem

interface ShopListRepository {

    fun addShopItem(shopItem: ShopItem)

    fun editShopItem(shopItem: ShopItem)

    fun removeShopItem(shopItem: ShopItem)

    fun getShopItem(shopItemId: Int): ShopItem?

    fun getShopList(): LiveData<List<ShopItem>>
}