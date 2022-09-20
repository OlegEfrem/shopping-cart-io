package com.shopping.cart.domain.model

import scala.util.{Failure, Success, Try}

object Validator {
  def biggerThanZero[T](number: T, failureMessage: String): Either[ShoppingCartError, T] = Try {
    number match {
      case n: Int => require(n > 0, failureMessage) //using require to throw an exception which has the stack trace
      case n: BigDecimal => require(n > 0, failureMessage)
      case _ =>
        val errorMsg = s"Not supported type: ${number.getClass.getName}"
        Left(InputError(errorMsg, new IllegalArgumentException(errorMsg)))
    }
  } match {
    case Success(_) => Right(number)
    case Failure(e) => Left(InputError(failureMessage, e))
  }

  def nonEmpty[A](iterable: Iterable[A], failureMessage: String): Either[ShoppingCartError, Iterable[A]] = Try {
    require(iterable.nonEmpty, failureMessage)
  } match {
    case Success(_) => Right(iterable)
    case Failure(exception) => Left(InputError(failureMessage, exception))
  }
}
