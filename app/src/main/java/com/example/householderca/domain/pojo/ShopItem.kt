package com.example.householderca.domain.pojo

data class ShopItem(
    val name: String,
    val count: Float,
    val enabled: Boolean,
    var id: Int = UNDEFINED_ID
) {
    companion object{
        const val UNDEFINED_ID = -1
    }
}
