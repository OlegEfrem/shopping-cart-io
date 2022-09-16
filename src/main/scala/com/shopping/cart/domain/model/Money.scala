package com.shopping.cart.domain.model

import scala.math.BigDecimal.RoundingMode

/**
 * Making constructor private to only allow creation via companion object to limit the price to 2 decimal numbers rounded up.
 * */
case class Money private (amount: BigDecimal)

object Money {
  def apply(amount: BigDecimal): Money = {
    require(amount > 0, s"Money amount must be bigger than zero, but it was: $amount")
    new Money(amount.setScale(2, RoundingMode.HALF_UP))
  }
}
