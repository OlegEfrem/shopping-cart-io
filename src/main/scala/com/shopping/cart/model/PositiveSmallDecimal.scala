package com.shopping.cart.model

import scala.math.BigDecimal.RoundingMode

/**
 * Making constructor private to only allow creation via companion object to limit the number to positive 2 decimals numbers rounded up.
 * */
case class PositiveSmallDecimal private(number: BigDecimal)

object PositiveSmallDecimal {
  def apply(number: BigDecimal): PositiveSmallDecimal = {
    require(number > 0, s"Positive small decimal must be bigger than zero, but it was: $number")
    new PositiveSmallDecimal(number.setScale(2, RoundingMode.HALF_UP))
  }
}
