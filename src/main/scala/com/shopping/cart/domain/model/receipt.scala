package com.shopping.cart.domain.model

case class ReceiptTotals(subtotal: PositiveSmallDecimal, tax: PositiveSmallDecimal, total: PositiveSmallDecimal)

case class Receipt(cart: ShoppingCart, totals: ReceiptTotals)
