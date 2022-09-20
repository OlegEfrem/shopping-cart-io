package com.shopping.cart.domain.model

import scala.math.BigDecimal.RoundingMode

/**
 * Making constructor private to only allow creation via companion object to limit the number to positive 2 decimals numbers with [[BigDecimal.RoundingMode.HALF_UP]].
 *
 * @note initially there were arithmetic methods added to [[PositiveSmallDecimal]] class, like addition, subtraction, multiplication, division.
 *       But those operating on [[PositiveSmallDecimal]] numbers which are rounded, could easily lead to accumulating large sums of money, so those were removed.
 *       Recommendation is to do those operations on [[BigDecimal]] and only do the rounding on the result, as a last operation.
 * */
case class PositiveSmallDecimal private(value: BigDecimal)

object PositiveSmallDecimal {
  def apply(number: BigDecimal): Either[ShoppingCartError, PositiveSmallDecimal] = {
    Validator.biggerThanZero(number, s"Positive small decimal must be bigger than zero, but was: $number").map(
      n => new PositiveSmallDecimal(n.setScale(2, RoundingMode.HALF_UP)))
  }
}
