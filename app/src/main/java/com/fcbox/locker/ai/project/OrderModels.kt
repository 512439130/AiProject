package com.fcbox.locker.ai.project

data class Order(
    val id: String,
    val time: String,
    val price: String,
    val status: String,
    val statusColor: Int,
    val items: List<OrderItem>,
    val address: String,
    var isExpanded: Boolean = false
)

data class OrderItem(
    val name: String,
    val spec: String,
    val price: String,
    val count: Int,
    val iconRes: Int
)
