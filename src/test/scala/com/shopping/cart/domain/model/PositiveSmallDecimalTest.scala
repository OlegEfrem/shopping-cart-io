package com.shopping.cart.domain.model


import org.scalatest.EitherValues
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers

class PositiveSmallDecimalTest extends AnyFreeSpec with Matchers with EitherValues {

  "should" - {
    "only show 2 decimals" in {
      PositiveSmallDecimal(1.2301) shouldBe PositiveSmallDecimal(BigDecimal(1.23))
      PositiveSmallDecimal(1.234).value.value shouldNot be(BigDecimal(1.234))
    }
    "round half up" in {
      PositiveSmallDecimal(1.234) shouldBe PositiveSmallDecimal(BigDecimal(1.23))
      PositiveSmallDecimal(1.235) shouldBe PositiveSmallDecimal(BigDecimal(1.24))
    }

    Seq(0, -1).foreach(number =>
      s"fail for numbers not bigger than zero like $number" in {
        val errorMessage = s"Positive small decimal must be bigger than zero, but was: $number"
        PositiveSmallDecimal(number).left.value.getMessage shouldBe errorMessage
      }
    )

  }
}