package com.example.householderca.domain.useCases

import com.example.householderca.domain.ShopListRepository
import com.example.householderca.domain.pojo.ShopItem

class RemoveShopItemUseCase(private val shopListRepository: ShopListRepository) {

    fun removeShopItem(shopItem: ShopItem){
        shopListRepository.removeShopItem(shopItem)
    }
}