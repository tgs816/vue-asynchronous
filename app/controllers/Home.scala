package controllers

import javax.inject.Inject


import play.api.Logger
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.libs.json.Json


import play.api.mvc.{Action, Controller}
import play.api.routing.JavaScriptReverseRouter

import views.html.helper.form


class Home @Inject()(val messagesApi: MessagesApi, currencyDao: dao.currencyDao) extends Controller with I18nSupport {


  def index = Action { implicit request =>
    Ok(views.html.index(forms.Currency.currency(isAdd = true)))
  }

  def currency = Action { implicit request =>
    //Ok(views.html.currency(currencyDao.select))
    Ok(Json.toJson(currencyDao.select))
  }

  def currencyAddPage = Action { implicit request =>
    Ok(views.html.currencyAdd(forms.Currency.currency(isAdd = true)))
  }

  // POST -> 데이터 추가하기
  def currencyAdd = Action { implicit request =>
    forms.Currency.currency(isAdd = true).bindFromRequest.fold(
      formWithErrors => BadRequest(formWithErrors.errorsAsJson), {
        case currencyForm => currencyDao.insert(currencyForm) match {
          case 1 => Ok(views.html.currency(currencyDao.select))
          case _ => BadRequest
        }
      }
    )
  }

  def currencyEditPage(id: String) = Action { implicit request =>
    currencyDao.select(id).map { currency =>
      Ok(views.html.currencyEdit(forms.Currency.currency(isAdd=false).fill(currency)))
    }.getOrElse(BadRequest)
  }

  // POST -> 데이터 수정하기
  def currencyEdit = Action { implicit request =>
    forms.Currency.currency(isAdd = false).bindFromRequest.fold(
      formWithErrors => BadRequest(formWithErrors.errorsAsJson), {
        case currencyForm => currencyDao.updateOne(currencyForm) match {
          //case r => Ok(Json.toJson(r))
          //case 1 => Ok(views.html.currency(currencyDao.select))
          case 1 => Ok(Json.toJson(currencyDao.select))
          case _ => BadRequest
        }
      }
    )
  }

  // 버튼 눌렀을 때 한개 가져오기
  def currencyEditOne(id: String) = Action { implicit request =>
    currencyDao.select(id) match {
        case r => Ok(Json.toJson(r))
        case _ => BadRequest
    }
  }

  def currencyDelete(id: String) = Action { implicit  request =>
    currencyDao.delete(id) match {
      case 1 => Ok(views.html.currency(currencyDao.select))
      case _ => BadRequest
    }
  }

  def home = Action { implicit request =>
    Ok(views.html.home())
  }

  def currencyEditSet(id: String) = Action { implicit request =>
    currencyDao.select(id) match {
      case r => Ok(Json.toJson(r))
      case _ => BadRequest
    }
  }



  /*// javaScript router
    def javascriptRoutes = Action { implicit request =>
    Ok(JavaScriptReverseRouter("jsRoutes")(
      routes.javascript.Home.index
    )
    ).as("text/javascript");
    /*).as(JAVASCRIPT);*/
  }*/






}
























