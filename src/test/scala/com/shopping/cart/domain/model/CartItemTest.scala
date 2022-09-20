package com.shopping.cart.domain.model

import org.scalatest.EitherValues
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers

class CartItemTest extends AnyFreeSpec with Matchers with EitherValues {
 "cartItem should" - {
   val price = PositiveSmallDecimal(1.10).value
   val shoppingProduct = ShoppingProduct(ProductName.Cheerios, price)

   "be created with quantity bigger than zero" in {
     val cartItem = CartItem(shoppingProduct, 1, price)
     cartItem shouldBe cartItem
   }

   "not be created with quantity not bigger than zero" in {
     CartItem(shoppingProduct, 0, price).left.value.getMessage shouldBe "Quantity must be bigger than 0, but was 0"
   }
 }
}
