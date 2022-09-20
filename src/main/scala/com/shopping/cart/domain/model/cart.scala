package com.shopping.cart.domain.model

case class CartItem private(product: ShoppingProduct, quantity: Int, cost: PositiveSmallDecimal)

object CartItem {
  def apply(product: ShoppingProduct, quantity: Int, cost: PositiveSmallDecimal): Either[ShoppingCartError, CartItem] = {
    Validator.biggerThanZero(quantity, s"Quantity must be bigger than 0, but was $quantity").map(new CartItem(product, _, cost))
  }
}

case class ShoppingCart(products: Seq[CartItem])

