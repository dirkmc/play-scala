package controllers 

import play._
import play.mvc._
import play.mvc.results.ScalaRenderJson
import play.db.jpa._
import play.data.validation._
import play.libs._
import play.utils.Scala._
import models._

object JsonTests extends Controller {
    def nullObject = Json(null)
    
    def string = Json("{'name':'guillaume'}")
    
    def pojo = Json(new Cat("No balls", 3, true))
    
    def javaModel = Json(new JUser("guillaume@gmail.com", "12e", "Guillaume"))
    
    def poso = Json(new ScalaCat("Sylvester", 5, false, "ignored"))
    
    def scalaModel = Json(new User("guillaume@gmail.com", "12e", "Guillaume"))
    
    def caseClass = Json(new PublicUser(7, "guillaume@gmail.com", "Guillaume"))
    
    def list = {
        val user1 = new PublicUser(7, "guillaume@gmail.com", "Guillaume")
        val user2 = new PublicUser(10, "bob@gmail.com", "Bob")
        Json(List(user1, user2))
    }
}
