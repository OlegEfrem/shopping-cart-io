package com.shopping.cart.domain.service

import com.shopping.cart.domain.model.{CartItem, Configuration, PositiveSmallDecimal, ProductName, Receipt, ReceiptTotals, ShoppingCart, ShoppingProduct}
import com.shopping.cart.domain.service.implementations.ConfigurableShoppingService
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers

import scala.language.implicitConversions

class ConfigurableShoppingServiceTest extends AnyFreeSpec with Matchers {
  private val service = new ConfigurableShoppingService(Configuration(tax = PositiveSmallDecimal(12.5)))
  "Methods tests" - {
    "add should" - {
      "add product to an empty cart" in new TestData() {
        private val actualCart: ShoppingCart = service.add(product, defaultQuantity, emptyCart)
        actualCart shouldBe cart
        actualCart shouldNot be(emptyCart)
      }

      "append product to a non empty cart" in new TestData() {
        private val oldCartItem = cartItem
        private val newQuantity: Int = oldCartItem.quantity + 1
        private val newCartItem = oldCartItem.copy(quantity = newQuantity, cost = oldCartItem.product.price.value * newQuantity)
        private val actualCart: ShoppingCart = service.add(newCartItem.product, newCartItem.quantity, ShoppingCart(Seq(oldCartItem)))
        actualCart.products.head shouldBe oldCartItem
        actualCart.products.last shouldBe newCartItem

      }

      "fail if price is zero" in new TestData(quantity = 2) {
        an[IllegalArgumentException] shouldBe thrownBy(service.add(product.copy(price = 0), 2, emptyCart))
      }

      "fail if quantity is zero" in new TestData() {
        an[IllegalArgumentException] shouldBe thrownBy(service.add(product, 0, emptyCart))
      }
    }

    "checkout should" - {
      "fail on an empty cart" in new TestData() {
        an[IllegalArgumentException] shouldBe thrownBy(service.checkout(emptyCart))
      }

      "calculate a non empty cart" in new TestData() {
        service.checkout(cart) shouldBe Receipt(cart, ReceiptTotals(2.46, 0.31, 2.77))
      }

      "fail if price is zero" in new TestData(quantity = 2) {
        an[IllegalArgumentException] shouldBe thrownBy(service.add(product.copy(price = 0), 2, emptyCart))
      }

      "fail if quantity is zero" in new TestData() {
        an[IllegalArgumentException] shouldBe thrownBy(service.add(product, 0, emptyCart))
      }
    }
  }

  class TestData(productName: ProductName = ProductName.Cheerios, price: Double = 1.23, quantity: Int = 2) {
    val defaultQuantity: Int = quantity
    val product: ShoppingProduct = ShoppingProduct(ProductName.Cheerios, PositiveSmallDecimal(price))
    val cost: PositiveSmallDecimal = PositiveSmallDecimal(product.price.value * quantity)
    val cartItem: CartItem = CartItem(product, quantity, cost)
    val cart: ShoppingCart = multiItemCart()
    val emptyCart: ShoppingCart = ShoppingCart(Seq())

    def multiItemCart(noOfItems: Int = 1): ShoppingCart = {
      val cartItems = (1 to noOfItems).map(_ => cartItem)
      ShoppingCart(cartItems)
    }

    implicit def intToPositiveSmallDecimal(int: Int): PositiveSmallDecimal = PositiveSmallDecimal(int)

    implicit def bigDecimalToPositiveSmallDecimal(bigDecimal: BigDecimal): PositiveSmallDecimal = PositiveSmallDecimal(bigDecimal)
    implicit def doubleToPositiveSmallDecimal(double: Double): PositiveSmallDecimal = PositiveSmallDecimal(double)
  }
}
