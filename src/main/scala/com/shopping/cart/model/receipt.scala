package com.shopping.cart.model

case class ReceiptItem(product: PricedProduct, cost: PositiveSmallDecimal)

case class ReceiptTotals(subtotal: PositiveSmallDecimal, tax: PositiveSmallDecimal, total: PositiveSmallDecimal)

case class Receipt(items: Seq[ReceiptItem], totals: ReceiptTotals)
