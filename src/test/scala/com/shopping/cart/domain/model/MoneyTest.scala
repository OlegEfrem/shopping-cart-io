package com.shopping.cart.domain.model

import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers

class MoneyTest extends AnyFreeSpec with Matchers {

  "Money should" - {
    "only show 2 decimals" in {
      Money(1.2301).amount shouldBe BigDecimal(1.23)
      Money(1.234).amount shouldNot be(BigDecimal(1.234))
    }
    "round half up" in {
      Money(1.234).amount shouldBe BigDecimal(1.23)
      Money(1.235).amount shouldBe BigDecimal(1.24)
    }

    "bigger than zero" in {
      the[IllegalArgumentException] thrownBy Money(0).amount should have message "requirement failed: Money amount must be bigger than zero, but it was: 0"
    }
  }
}
