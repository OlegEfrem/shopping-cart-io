package com.shopping.cart.domain.service

import com.shopping.cart.domain.model._

import scala.language.postfixOps

/**
 * Service to allow adding products to a [[ShoppingCart]] and to calculate the total cost.
 *
 * @note this service is intentionally effect free, containing only pure functions.
 *       All side effects like storing data in db or calling the external [[com.shopping.cart.integration.PricingService]] should be done outside of this service.
 *       [[ShoppingService]] needs to be provided with all the data it needs in a wiring Application class.
 * */
trait ShoppingService {
  /**
   * Function to add a product with its quantity to the cart
   *
   * @param product  the product to be added to the cart
   * @param quantity how many items of the given product to be added
   * @param toCart   cart to which the given quantity of a product will be added.
   *                 This is functional style of pure function without side effects (not visible in function signature),
   *                 as opposed to OOP where the cart would have a method to add products to itself as a side effect.
   * @return [[ShoppingCart]] with the new [[CartItem]] added having the total cost of the product quantity calculated.
   * */
  def add(product: ShoppingProduct, quantity: Int, toCart: ShoppingCart): ShoppingCart

  /**
   * Function to calculate the totals for the [[ShoppingCart]].
   *
   * @param cart the [[ShoppingCart]] with all [[CartItem]] representing the cost for all [[ShoppingProduct]] quantity.
   * @return [[Receipt]] which contains the [[ShoppingCart]] with overall cost in [[ReceiptTotals]]
   * */
  def checkout(cart: ShoppingCart): Receipt

}


