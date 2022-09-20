package com.shopping.cart

import com.shopping.cart.domain.model.{PositiveSmallDecimal, ProductName, ReceiptTotals}
import munit.CatsEffectSuite
import org.scalatest.EitherValues
import org.scalatest.prop.TableDrivenPropertyChecks._

import scala.language.implicitConversions

class ShoppingCartApplicationTest extends CatsEffectSuite with EitherValues {
  private val shoppingCartApplication = ShoppingCartApplication

  private val testData = Table(
    ("products", "totals"),
    (Seq(ProductName.Cornflakes -> 2, ProductName.Weetabix -> 1), ReceiptTotals(subtotal = 15.02, tax = 1.88, total = 16.90))
  )

  forAll(testData){ (products, totals) =>
    test(s"should calculate totals for $products to be: $totals") {
      shoppingCartApplication.buy(products: _*).map(receipt => assertEquals(receipt.totals, totals))
    }
  }
  Seq(0, -1).foreach{ quantity =>
    test(s"should require quantity greater than zero, like $quantity") {
      val expectedErrorMessage = s"Quantity must be bigger than 0, but was $quantity"
      interceptMessageIO[IllegalArgumentException](expectedErrorMessage)(shoppingCartApplication.buy(ProductName.Cheerios -> quantity))
    }
  }



  private implicit def doubleToPositiveSmallDecimal(double: Double): PositiveSmallDecimal = PositiveSmallDecimal(double).value

}
