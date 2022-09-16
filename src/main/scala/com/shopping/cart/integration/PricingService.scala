package com.shopping.cart.integration

import cats.effect.IO
import com.shopping.cart.domain.model.{PositiveSmallDecimal, ProductName}

trait PricingService {
  def priceFor(product: ProductName): IO[PositiveSmallDecimal]
}
