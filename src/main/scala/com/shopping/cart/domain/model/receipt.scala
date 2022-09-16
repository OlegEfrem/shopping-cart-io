package com.shopping.cart.domain.model

case class ReceiptItem(product: PricedProduct, cost: Money)

case class ReceiptTotals(subtotal: Money, tax: Money, total: Money)

case class Receipt(items: Seq[ReceiptItem], totals: ReceiptTotals)
