package com.example.householderca.domain.useCases

import com.example.householderca.domain.ShopListRepository
import com.example.householderca.domain.pojo.ShopItem

class GetShopItemUseCase(private val shopListRepository: ShopListRepository) {

    suspend fun getShopItem(shopItemId: Int): ShopItem? {
        return shopListRepository.getShopItem(shopItemId)
    }
}