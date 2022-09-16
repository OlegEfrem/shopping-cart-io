package com.shopping.cart.integration

import com.shopping.cart.model.ProductName
import munit.CatsEffectSuite

class DefaultProductInfoServiceTest extends CatsEffectSuite {
  private val service = ProductInfoService()

  private val expectedPrices: Map[ProductName, BigDecimal] = Map(
    ProductName.Cheerios -> 8.43,
    ProductName.Cornflakes -> 2.52,
    ProductName.Frosties -> 4.99,
    ProductName.Shreddies -> 4.68,
    ProductName.Weetabix -> 9.98
  )

  ProductName.values.foreach { productName =>
    val expectedPrice: BigDecimal = expectedPrices(productName)
    test(s"should return $expectedPrice for $productName") {
      service.priceFor(productName).map(price => assertEquals(price.number, expectedPrice))
    }
  }
}
