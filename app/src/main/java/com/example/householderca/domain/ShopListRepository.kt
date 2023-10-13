package com.example.householderca.domain

import androidx.lifecycle.LiveData
import com.example.householderca.domain.pojo.ShopItem

interface ShopListRepository {

    suspend fun addShopItem(shopItem: ShopItem)

    suspend fun editShopItem(shopItem: ShopItem)

    suspend fun deleteShopItem(shopItem: ShopItem)

    suspend fun getShopItem(shopItemId: Int): ShopItem?

    fun getShopList(): LiveData<List<ShopItem>>
}