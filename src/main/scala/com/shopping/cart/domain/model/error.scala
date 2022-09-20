package com.shopping.cart.domain.model


sealed abstract class ShoppingCartError(message: String, cause: Throwable) extends Exception(message, cause)

case class InputError(message: String, cause: Throwable) extends ShoppingCartError(message, cause)
