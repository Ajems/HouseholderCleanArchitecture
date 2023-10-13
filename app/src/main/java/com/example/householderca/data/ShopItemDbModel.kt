package com.example.householderca.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shop_Items")
data class ShopItemDbModel (
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    val name: String,
    val count: Float,
    val enabled: Boolean
)