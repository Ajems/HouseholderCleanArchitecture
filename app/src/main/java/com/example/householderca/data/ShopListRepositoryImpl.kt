package com.example.householderca.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.householderca.domain.ShopListRepository
import com.example.householderca.domain.pojo.ShopItem

object ShopListRepositoryImpl: ShopListRepository {

    private val shopListLiveData = MutableLiveData<List<ShopItem>>()
    private val shopList = mutableListOf<ShopItem>()

    private var autoIncrementId = 0

    init {
        for (i in 0..10){
            val item = ShopItem("Name$i", i.toFloat(), true)
            addShopItem(item)
        }
    }

    override fun addShopItem(shopItem: ShopItem) {
        if (shopItem.id == ShopItem.UNDEFINED_ID) {
            shopItem.id = autoIncrementId++
        }
        shopList.add(shopItem)
        updateShopList()
    }

    override fun editShopItem(shopItem: ShopItem) {
        getShopItem(shopItem.id)?.let {
            shopList.remove(it)
        }
        addShopItem(shopItem)
    }

    override fun removeShopItem(shopItem: ShopItem) {
        shopList.remove(shopItem)
        updateShopList()
    }

    override fun getShopItem(shopItemId: Int): ShopItem? {
        return shopList.find { it.id == shopItemId }
    }

    override fun getShopList(): LiveData<List<ShopItem>> {
        return shopListLiveData
    }

    private fun updateShopList(){
        shopListLiveData.value = shopList.toList()
    }
}