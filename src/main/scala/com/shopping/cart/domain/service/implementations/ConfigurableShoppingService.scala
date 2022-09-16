package com.shopping.cart.domain.service.implementations

import com.shopping.cart.domain.model._
import com.shopping.cart.domain.service.ShoppingService
import scala.language.postfixOps

class ConfigurableShoppingService(configuration: Configuration) extends ShoppingService {
  override def add(product: ShoppingProduct, quantity: Int, toCart: ShoppingCart): ShoppingCart = {
    require(quantity > 0, s"Quantity must be bigger than 0, but was $quantity")
    val cost = (product.price.value * quantity) asPositiveSmallDecimal
    val item = CartItem(product, quantity, cost)
    toCart.copy(products = toCart.products.:+(item))
  }

  override def checkout(cart: ShoppingCart): Receipt = {
    require(cart.products.nonEmpty, "Cart must not be empty.")
    val subtotal = cart.products.foldLeft(BigDecimal(0)) { (total, next) => total + next.cost.value } asPositiveSmallDecimal
    val tax = (configuration.tax.value * subtotal.value / 100) asPositiveSmallDecimal
    val total = (subtotal.value + tax.value) asPositiveSmallDecimal
    val totals = ReceiptTotals(subtotal, tax, total)
    Receipt(cart, totals)
  }

  implicit class BigDecimalOps(bigDecimal: BigDecimal) {
    def asPositiveSmallDecimal: PositiveSmallDecimal = PositiveSmallDecimal(bigDecimal)
  }
}
