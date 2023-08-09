package com.example.householderca.presentation.viewModel

import androidx.lifecycle.ViewModel
import com.example.householderca.data.ShopListRepositoryImpl //TODO fix by DI
import com.example.householderca.domain.pojo.ShopItem
import com.example.householderca.domain.useCases.EditShopItemUseCase
import com.example.householderca.domain.useCases.GetShopListUseCase
import com.example.householderca.domain.useCases.RemoveShopItemUseCase

class MainViewModel: ViewModel() {

    //TODO fix by DI
    private val repository = ShopListRepositoryImpl

    private val getShopListUseCase = GetShopListUseCase(repository)
    private val removeShopItemUseCase = RemoveShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    val shopList = getShopListUseCase.getShopList()

    fun removeShopItem(shopItem: ShopItem){
        removeShopItemUseCase.removeShopItem(shopItem)
    }

    fun changeEnableState(shopItem: ShopItem){
        val newShopItem = shopItem.copy(enabled = !shopItem.enabled)
        editShopItemUseCase.editShopItem(newShopItem)
    }
}