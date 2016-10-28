package dao

import javax.inject.Inject

import anorm._
import play.api.db.Database


class currencyDao @Inject() (db:Database) {

  def select = {
    db.withConnection { implicit c =>
      SQL"""
         SELECT * FROM "currency"
      """.as(models.Currency.parser.*)
    }
  }

  def insert(form: models.Currency) = {
    db.withConnection { implicit c =>
      SQL"""
        INSERT INTO "currency"("currency_code", "currency_name", "currency_name_en", "currency_symbol", "is_active")
        VALUES(${form.currencyCode}, ${form.currencyName}, ${form.currencyNameEn}, ${form.currencySymbol}, true)
      """.executeUpdate()
    }
  }

  def select(id: String) = {
      db.withConnection { implicit c =>
        SQL"""
        SELECT "currency_code", "currency_name", "currency_name_en", "currency_symbol", "is_active" FROM "currency"
        WHERE "currency_code" = $id
      """.as(models.Currency.parser.singleOpt)
      }
  }

  def update(form: models.Currency) = {
    db.withConnection { implicit c =>
      SQL"""
        UPDATE "currency"
        SET "currency_name"    = ${form.currencyName},
            "currency_name_en" = ${form.currencyNameEn},
            "currency_symbol"  = ${form.currencySymbol}
        WHERE "currency_code" = ${form.currencyCode}
      """.executeUpdate
    }
  }

  // 업데이트 후 해당 객체 리턴해주면 좋겟다
  def updateOne(form: models.Currency) = {
    db.withConnection { implicit c =>
      SQL"""
        UPDATE "currency"
        SET "currency_name"    = ${form.currencyName},
            "currency_name_en" = ${form.currencyNameEn},
            "currency_symbol"  = ${form.currencySymbol}
        WHERE "currency_code" = ${form.currencyCode}
      """.executeUpdate()
    }
  }



  def delete(id: String) = {
    db.withConnection { implicit c =>
      SQL"""
         DELETE FROM "currency"
         WHERE "currency_code" = $id
      """.executeUpdate()
    }
  }
}