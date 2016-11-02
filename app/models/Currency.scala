package models

import anorm.SqlParser._
import anorm._
import play.api.libs.json.Json

case class Currency (
  currencyCode   : String,
  currencyName   : String,
  currencyNameEn : Option[String],
  currencySymbol : Option[String],
  isActive       : Boolean,
  currencySign   : Option[Seq[Byte]]
)

object Currency {
  val parser = {
    get[String]("currency_code") ~
    get[String]("currency_name") ~
    get[Option[String]]("currency_name_en") ~
    get[Option[String]]("currency_symbol") ~
    get[Boolean]("is_active") ~
    get[Option[Array[Byte]]]("currency_sign") map { case
      currencyCode ~ currencyName ~ currencyNameEn ~ currencySymbol ~ isActive ~ currencySign => Currency(
        currencyCode, currencyName, currencyNameEn, currencySymbol, isActive, currencySign.map(_.toSeq)
      )
    }
  }
  implicit val jsonWrites = Json.writes[Currency]

}