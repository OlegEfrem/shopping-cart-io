package com.shopping.cart.domain.service.implementations

import com.shopping.cart.domain.model._
import com.shopping.cart.domain.service.ShoppingService
import scala.language.postfixOps

class ConfigurableShoppingService(configuration: Configuration) extends ShoppingService {
  override def add(product: ShoppingProduct, quantity: Int, toCart: ShoppingCart): Either[ShoppingCartError, ShoppingCart] = {
    for {
      quantity <- Validator.biggerThanZero(quantity, s"Quantity must be bigger than 0, but was $quantity")
      cost <- (product.price.value * quantity).asPositiveSmallDecimal
      cartItem <- CartItem(product, quantity, cost)
    } yield toCart.copy(products = toCart.products.:+(cartItem))
  }

  override def checkout(cart: ShoppingCart): Either[ShoppingCartError, Receipt] = {
    for {
      products <- Validator.nonEmpty(cart.products, "Cart must not be empty.")
      subtotal <- products.foldLeft(BigDecimal(0)) { (total, next) => total + next.cost.value }.asPositiveSmallDecimal
      tax <- (configuration.tax * subtotal.value / 100).asPositiveSmallDecimal
      total <- (subtotal.value + tax.value).asPositiveSmallDecimal
      totals = ReceiptTotals(subtotal, tax, total)
    } yield Receipt(cart, totals)
  }

  implicit class BigDecimalOps(bigDecimal: BigDecimal) {
    def asPositiveSmallDecimal: Either[ShoppingCartError, PositiveSmallDecimal] = PositiveSmallDecimal(bigDecimal)
  }
}
