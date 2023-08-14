package com.example.householderca.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.householderca.data.ShopListRepositoryImpl
import com.example.householderca.domain.pojo.ShopItem
import com.example.householderca.domain.useCases.AddShopItemUseCase
import com.example.householderca.domain.useCases.EditShopItemUseCase
import com.example.householderca.domain.useCases.GetShopItemUseCase
import java.lang.Exception

class ShopItemViewModel: ViewModel() {

    private val repository = ShopListRepositoryImpl

    private val getShopItemUseCase = GetShopItemUseCase(repository)
    private val addShopItemUseCase = AddShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName

    private val _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount: LiveData<Boolean>
        get() = _errorInputCount

    private val _shopItem = MutableLiveData<ShopItem>()
    val shopItem: LiveData<ShopItem>
        get() = _shopItem

    private val _availableClose = MutableLiveData<Unit>()
    val availableClose: LiveData<Unit>
        get() = _availableClose

    fun getShopItem(shopItemId: Int){
        val item = getShopItemUseCase.getShopItem(shopItemId)
        _shopItem.value = item
    }

    fun addShopItem(inputName: String?, inputCount: String?){
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldsValid = validateInput(name, count)
        if (fieldsValid){
            val shopItem = ShopItem(name, count, true)
            addShopItemUseCase.addShopItem(shopItem)
            finishWork()
        }
    }

    fun editShopItem(inputName: String?, inputCount: String?){
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldsValid = validateInput(name, count)
        if (fieldsValid){
            _shopItem.value?.let {
                val item = it.copy(name = name, count = count)
                editShopItemUseCase.editShopItem(item)
                finishWork()
            }
        }
    }

    private fun parseName(inputName: String?): String{
        return inputName?.trim() ?: ""
    }

    private fun parseCount(inputCount: String?): Float {
        return try{
            inputCount?.trim()?.toFloat() ?: 0.toFloat()
        } catch (e: Exception){
            0.toFloat()
        }
    }

    private fun validateInput(name: String, count: Float): Boolean{
        var result = true
        if (name.isBlank()){
            _errorInputName.value = true
            result = false
        }

        if (count <= 0){
            _errorInputCount.value = true
            result = false
        }

        return result
    }

    public fun resetErrorInputName(){
        _errorInputName.value = false
    }

    public fun resetErrorInputCount(){
        _errorInputCount.value = false
    }

    private fun finishWork() {
        _availableClose.value = Unit
    }
}