package com.shopping.cart.domain.model

case class CartItem private(product: ShoppingProduct, quantity: Int, cost: PositiveSmallDecimal)

object CartItem {
  def apply(product: ShoppingProduct, quantity: Int, cost: PositiveSmallDecimal): CartItem = {
    require(quantity > 0, s"Quantity must be bigger than 0, but was $quantity")
    new CartItem(product, quantity, cost)
  }
}

case class ShoppingCart(products: Seq[CartItem])

