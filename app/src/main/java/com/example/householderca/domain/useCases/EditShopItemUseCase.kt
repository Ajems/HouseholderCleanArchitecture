package com.example.householderca.domain.useCases

import com.example.householderca.domain.ShopListRepository
import com.example.householderca.domain.pojo.ShopItem

class EditShopItemUseCase(private val shopListRepository: ShopListRepository) {

    suspend fun editShopItem(shopItem: ShopItem){
        shopListRepository.editShopItem(shopItem)
    }
}