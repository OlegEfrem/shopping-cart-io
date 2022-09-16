package com.shopping.cart.integration.implementations

import cats.effect.IO
import com.shopping.cart.ShoppingCartApplication.ProductPrice
import com.shopping.cart.domain.model.{PositiveSmallDecimal, ProductName}
import com.shopping.cart.integration.PricingService
import okhttp3.{OkHttpClient, Request}
import scala.annotation.tailrec

class CachedPricingService extends PricingService {
  private val prices: IO[Map[ProductName, ProductPrice]] = addPrices(toPriceMap = IO.pure(Map.empty))

  import io.circe.generic.auto._
  import io.circe.parser._

  case class ProductInfo(title: String, price: BigDecimal)

  private val client = new OkHttpClient()

  override def priceFor(product: ProductName): IO[PositiveSmallDecimal] = {
    prices.map(_.apply(product))
  }
  def getPriceFor(product: ProductName): IO[PositiveSmallDecimal] = {
    for {
      response <- getProductInfo(product)
      productInfo: ProductInfo <- IO.fromEither(decode[ProductInfo](response))
    } yield PositiveSmallDecimal(productInfo.price)
  }

  type ResponsePayload = String

  private def getProductInfo(product: ProductName): IO[ResponsePayload] = IO {
    val url = s"https://raw.githubusercontent.com/mattjanks16/shopping-cart-test-data/main/${product.entryName.toLowerCase}.json"
    val request = new Request.Builder()
      .url(url)
      .build()
    client.newCall(request).execute().body().string()
  }

  @tailrec
  private def addPrices(
                         forProducts: List[ProductName] = ProductName.values.toList,
                         toPriceMap: IO[Map[ProductName, ProductPrice]] = prices
                       ): IO[Map[ProductName, ProductPrice]] = {
    forProducts match {
      case Nil => toPriceMap
      case h :: t => addPrices(t, addPrice(h, toPriceMap))
    }
  }

  private def addPrice(forProduct: ProductName, toPriceMap: IO[Map[ProductName, ProductPrice]]): IO[Map[ProductName, ProductPrice]] = {
    for {
      priceMap <- toPriceMap
      price <- getPriceFor(forProduct)
    } yield priceMap.+(forProduct -> price)
  }
}
