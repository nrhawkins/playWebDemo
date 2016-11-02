package controllers

import play.api._
import play.api.mvc._

import play.api.data._
import play.api.data.Forms._

import javax.inject.Inject
import play.api.i18n.{MessagesApi, I18nSupport}
import play.api.mvc.Controller

import models.DemoData
//import utils.TextUtilities
import edu.washington.nsre.NewsSpikeSentencePredict

class Application @Inject() (val messagesApi: MessagesApi) 
  extends Controller with I18nSupport{

  val demoForm = Form(
     mapping(
       "sampleText" -> text,
       "processedText" -> text
     )(DemoData.apply)(DemoData.unapply)
  )

  val predictionString = "Confidence .. Relation .. Argument1 .. Argument2" + "\n\n";  

//  val defaultFilledForm = demoForm.fill(DemoData("Bob spent Monday night in Miami.", 
//    "spent/Bob/MondayNight"))

  val defaultFilledForm = demoForm.fill(DemoData("DES MOINESÂ â $ '' Â Gov. Terry Branstad spent Monday night in a Des Moines hospital after falling ill during a speaking engagement ." + 
      "\n\n" + 
      "LOS ANGELES â $ `` Austin Rivers joined the Los Angeles Clippers to play for Doc Rivers in the first father-son , player-coach combination in NBA history ." +
      "\n\n" +
      "-LRB- AP Photo\\/Wal-Mart Inc. via The Grayson County -LRB- Ky. -RRB- Sheriff 's Office -RRB- In this January 2015 photo made from surveillance video and released by the Grayson County Sheriff 's Office , in Kentucky , 18-year-old Dalton Hayes and 13-year-old Cheyenne Phillips walk into a South Carolina Wal-Mart ." +
      "\n\n" + 
      "Deadspin posted the full exchange , in which 73-year-old Irving Bierman wrote to Dolan asking him to sell the team in order to give them better leadership , and Dolan wrote back -LRB- sic throughout -RRB- , `` You are a sad person ." +
      "\n\n" + 
      "WASHINGTON -- A unanimous Supreme Court ruled Tuesday that a Muslim prison inmate in Arkansas can grow a short beard for religious reasons .",
      predictionString +
      "1.0 .. spend@/person@/time .. $ '' Â Gov. Terry Branstad .. Monday night" + 
      "\n" +
      "1.0 .. sign with@/person@/organization .. $ \'\' Austin Rivers .. the Los Angeles Clippers" +
      "\n" + 
      "1.0 .. enter@/person@/organization .. 18-year-old Dalton Hayes and 13-year-old Cheyenne Phillips .. a South Carolina Wal-Mart" + 
      "\n" +  
      "1.0 .. reach out to@/person@/person .. 73-year-old Irving Bierman .. Dolan" +
      "\n" +
      "1.0 .. rule@/organization@/time .. A unanimous Supreme Court .. Tuesday"
))

// val defaultFilledForm = demoForm.fill(DemoData("LOS ANGELES â $ Austin Rivers joined the Los Angeles Clippers to play for Doc Rivers in the first father-son , player-coach combination in NBA history .",
//        "sign with@/person@/organization:1.0,	$ Austin Rivers@the Los Angeles Clippers@20150116"))
 
//  val defaultFilledForm = demoForm.fill(DemoData("Bananas are an excellent source of potassium .",
//        "aresourceof@/fruit@/substance:1.0, Bananas@potassium@noDate"))

  val demoFormConstraints = Form(
    mapping(
      "sampleText" -> nonEmptyText,
      "processedText" -> nonEmptyText
    )(DemoData.apply)(DemoData.unapply)
  )

 def processText = Action { implicit request =>
  demoForm.bindFromRequest.fold(
    formWithErrors => {
      // binding failure, retrieve the form containing errors
      BadRequest(views.html.index(formWithErrors))
    },   
    demoData => {
      // binding success, you get the actual value
      //Redirect(routes.Application.demo(demoForm))
      //val processedText = "Extracted Relations"
      val nssp = new NewsSpikeSentencePredict()
      //val processedText = "testing... Processed Text"
      val processedText = nssp.predict(demoData.sampleText)
      //val processedText = TextUtilities.reverseString(demoData.sampleText)
      val filledForm = demoForm.fill(DemoData(demoData.sampleText, processedText))
      Ok(views.html.demo(filledForm))
    }
  )
 }

  def index = Action {
    Ok(views.html.index(defaultFilledForm))
  }

  // Handles the form submission

//  def processText = Action {
//    Ok(views.html.demo(demoForm))
//  }


//  def processText = Action { implicit request =>
//    demoForm.bindFromRequest.fold(
//      formWithErrors => // ,
//      {case (sampleText, name) => // 
//      }
//    )
//  }

//  def processText = Action { implicit request =>
//    demoForm.bindFromRequest.fold(
//      formWithErrors => BadRequest(html.index(formWithErrors)),
//      {case (sampleText, name) => Ok(html.demo(sampleText, name))}
//    )
//  }

}
