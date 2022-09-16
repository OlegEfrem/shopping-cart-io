package com.shopping.cart.integration

import cats.effect.IO
import com.shopping.cart.model.{PositiveSmallDecimal, ProductName}
import okhttp3.{OkHttpClient, Request}

trait ProductInfoService {
  def priceFor(product: ProductName): IO[PositiveSmallDecimal]
}

object ProductInfoService {
  def apply(): ProductInfoService = new DefaultProductInfoService()
}

class DefaultProductInfoService extends ProductInfoService {

  import io.circe.generic.auto._
  import io.circe.parser._

  case class ProductInfo(title: String, price: BigDecimal)

  private val client = new OkHttpClient()

  override def priceFor(product: ProductName): IO[PositiveSmallDecimal] = {
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
}