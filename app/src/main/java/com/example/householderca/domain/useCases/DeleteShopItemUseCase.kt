package com.example.householderca.domain.useCases

import com.example.householderca.domain.ShopListRepository
import com.example.householderca.domain.pojo.ShopItem

class DeleteShopItemUseCase(private val shopListRepository: ShopListRepository) {

    suspend fun deleteShopItem(shopItem: ShopItem){
        shopListRepository.deleteShopItem(shopItem)
    }
}