package com.example.householderca.domain.useCases

import androidx.lifecycle.LiveData
import com.example.householderca.domain.ShopListRepository
import com.example.householderca.domain.pojo.ShopItem

class GetShopListUseCase(private val shopListRepository: ShopListRepository) {

    fun getShopList(): LiveData<List<ShopItem>>{
        return shopListRepository.getShopList()
    }
}