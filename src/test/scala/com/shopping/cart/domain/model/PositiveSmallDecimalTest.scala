package com.shopping.cart.domain.model


import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers

class PositiveSmallDecimalTest extends AnyFreeSpec with Matchers {

  "PositiveSmallDecimal should" - {
    "only show 2 decimals" in {
      PositiveSmallDecimal(1.2301).value shouldBe BigDecimal(1.23)
      PositiveSmallDecimal(1.234).value shouldNot be(BigDecimal(1.234))
    }
    "round half up" in {
      PositiveSmallDecimal(1.234).value shouldBe BigDecimal(1.23)
      PositiveSmallDecimal(1.235).value shouldBe BigDecimal(1.24)
    }

    Seq(0, -1).foreach(number =>
      s"allow only bigger than zero thus fail $number" in {
        the[IllegalArgumentException] thrownBy PositiveSmallDecimal(number).value should have message s"requirement failed: Positive small decimal must be bigger than zero, but it was: $number"
      }
    )

  }
}