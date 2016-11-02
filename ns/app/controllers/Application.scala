package controllers

//import play.api.data._
//import play.api.data.Forms._

import play.data._

import play.api._
import play.api.mvc._

class Application extends Controller {

   val demoForm = Form(
     tuple(
       "sampleText" -> nonEmptyText,
       "name" -> text
     )
   )

   // -- Actions -- //

    def index = Action {
      Ok(views.html.index(demoForm))
    }

    //public Result index() {
    //    return ok(index.render("NewsSpike Relation Extractor"));
    //}

    def processText = Action { implicit request => 
      demoForm.bindFromRequest.fold(
        formWithErrors => BadRequest(html.index(formWithErrors)),
        {case (name, repeat, color) => Ok(html.demo(sampleText))}
        )
    }

}
