package com.shopping.cart.domain.model

import org.scalatest.EitherValues
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers

class ValidatorTest extends AnyFreeSpec with Matchers with EitherValues {

  "Methods tests" - {
    "nonEmpty should" - {
      "return right for a non empty collection" in {
        val nonEmptyList = List(1, 2, 3)
        Validator.nonEmpty(nonEmptyList, "list must not be empty") shouldBe Right(nonEmptyList)
      }
      "return left for an empty collection" in {
        val errorMessage = "list must not be empty"
        Validator.nonEmpty(List(), errorMessage).left.value.getMessage shouldBe errorMessage
      }
    }

    "biggerThanZero should" - {
      val errorMessage = "should be bigger than zero"

      Set(1, BigDecimal(1.1)).foreach(number =>
        s"return right for a number bigger than zero like: $number" in {
          Validator.biggerThanZero(number, errorMessage) shouldBe Right(number)
        }
      )
      Set(0, -1, BigDecimal(0.0), BigDecimal(-1.1)).foreach(number =>
        s"return left for a number not bigger than zero like $number" in {
          Validator.biggerThanZero(number, errorMessage).left.value.getMessage shouldBe errorMessage
        }
      )
      Set("not a number", 1L, 1.2D).foreach(notSupportedNumber => {
        val elementType = notSupportedNumber.getClass.getName
        s"return not supported type for other types like $elementType" in {
          val errorMessage = s"Not supported type: $elementType"
          Validator.biggerThanZero(notSupportedNumber, errorMessage)
        }
      })
    }
  }
}
