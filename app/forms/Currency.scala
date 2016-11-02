package forms

import play.api.data.Form
import play.api.data.Forms._

object Currency {
  def currency(isAdd: Boolean) = Form(mapping(
    "currency_code"    -> text(minLength = 2),
    "currency_name"    -> text(minLength = 2),
    "currency_name_en" -> optional(text),
    "currency_symbol"  -> optional(text),
    "is_active" -> { if (isAdd) ignored(null.asInstanceOf[Boolean]) else boolean },
    "currency_sign" -> optional(seq(byteNumber))

  )(models.Currency.apply)(models.Currency.unapply))
}
