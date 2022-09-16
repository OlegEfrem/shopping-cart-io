package com.shopping.cart.model

import enumeratum._

sealed trait ProductName extends EnumEntry

object ProductName extends Enum[ProductName] with CirceEnum[ProductName] {

  val values: IndexedSeq[ProductName] = findValues

  case object Cheerios extends ProductName
  case object Cornflakes extends ProductName
  case object Frosties extends ProductName
  case object Shreddies extends ProductName
  case object Weetabix extends ProductName
}

case class NotPricedProduct(name: ProductName, quantity: Int)

case class PricedProduct(product: NotPricedProduct, price: PositiveSmallDecimal)

case class ShoppingCart(products: Seq[PricedProduct])
