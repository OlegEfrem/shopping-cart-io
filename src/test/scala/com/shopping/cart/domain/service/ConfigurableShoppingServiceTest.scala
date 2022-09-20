package com.shopping.cart.domain.service

import com.shopping.cart.domain.model.{CartItem, Configuration, PositiveSmallDecimal, ProductName, Receipt, ReceiptTotals, ShoppingCart, ShoppingProduct}
import com.shopping.cart.domain.service.implementations.ConfigurableShoppingService
import org.scalatest.EitherValues
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers

import scala.language.implicitConversions

class ConfigurableShoppingServiceTest extends AnyFreeSpec with Matchers with EitherValues {
  private val service = new ConfigurableShoppingService(Configuration(tax = 12.5))
  "add should" - {
    "add product to an empty cart" in new TestData() {
      private val actualCart = service.add(product, defaultQuantity, emptyCart).value
      actualCart shouldBe cart
      actualCart shouldNot be(emptyCart)
    }

    "append product to a non empty cart" in new TestData() {
      private val oldCartItem = cartItem
      private val newQuantity: Int = oldCartItem.quantity + 1
      private val newCartItem = oldCartItem.copy(quantity = newQuantity, cost = oldCartItem.product.price.value * newQuantity)
      private val actualCart: ShoppingCart = service.add(newCartItem.product, newCartItem.quantity, ShoppingCart(Seq(oldCartItem))).value
      actualCart.products.head shouldBe oldCartItem
      actualCart.products.last shouldBe newCartItem

    }

    "fail if quantity is zero" in new TestData() {
      service.add(product, 0, emptyCart).left.value.getMessage shouldBe "Quantity must be bigger than 0, but was 0"
    }
  }

  "checkout should" - {
    "calculate a non empty cart" in new TestData() {
      service.checkout(cart) shouldBe Right(Receipt(cart, ReceiptTotals(2.46, 0.31, 2.77)))
    }

    "fail on an empty cart" in new TestData() {
      service.checkout(emptyCart).left.value.getMessage shouldBe "Cart must not be empty."
    }
  }

  class TestData(productName: ProductName = ProductName.Cheerios, price: Double = 1.23, quantity: Int = 2) {
    val defaultQuantity: Int = quantity
    val product: ShoppingProduct = ShoppingProduct(ProductName.Cheerios, PositiveSmallDecimal(price).value)
    val cost: PositiveSmallDecimal = PositiveSmallDecimal(product.price.value * quantity).value
    val cartItem: CartItem = CartItem(product, quantity, cost).value
    val cart: ShoppingCart = multiItemCart()
    val emptyCart: ShoppingCart = ShoppingCart(Seq())

    def multiItemCart(noOfItems: Int = 1): ShoppingCart = {
      val cartItems = (1 to noOfItems).map(_ => cartItem)
      ShoppingCart(cartItems)
    }

    implicit def intToPositiveSmallDecimal(int: Int): PositiveSmallDecimal = PositiveSmallDecimal(int).value

    implicit def bigDecimalToPositiveSmallDecimal(bigDecimal: BigDecimal): PositiveSmallDecimal = PositiveSmallDecimal(bigDecimal).value

    implicit def doubleToPositiveSmallDecimal(double: Double): PositiveSmallDecimal = PositiveSmallDecimal(double).value
  }
}
