package com.shopping.cart.integration.implementations

import com.shopping.cart.domain.model.ProductName
import munit.CatsEffectSuite

class CachedPricingServiceTest extends CatsEffectSuite {
  private val service = new CachedPricingService

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
      service.priceFor(productName).map(price => assertEquals(price.value, expectedPrice))
    }
  }
}
