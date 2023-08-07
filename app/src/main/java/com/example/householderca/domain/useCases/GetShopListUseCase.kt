package com.example.householderca.domain.useCases

import com.example.householderca.domain.ShopListRepository
import com.example.householderca.domain.pojo.ShopItem

class GetShopListUseCase(private val shopListRepository: ShopListRepository) {

    fun getShopList(): List<ShopItem>{
        return shopListRepository.getShopList()
    }
}