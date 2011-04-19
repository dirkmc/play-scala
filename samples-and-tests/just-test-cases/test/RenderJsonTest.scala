import org.junit._
import play.test.{FunctionalTest, FunctionalTestCase, Matchers => PlayMatchers}
import play.db.jpa._
import play.mvc._
import play.mvc.Http._
import models._
import net.liftweb.json._
import org.scalatest.junit._
import org.scalatest.matchers._


class Cat(var name: String) {
    var weight = 5
}

case class ResponseUser(id:Long, email:String, password:String, fullname:String, isAdmin:Boolean)

class RenderJsonTest extends FunctionalTestCase with PlayMatchers with ShouldMatchersForJUnit {

    @Test
    def nullObject {
        var response = GET("/JsonTests/nullObject")
        response shouldBeOk()
        response contentTypeShouldBe("application/json")
        response charsetShouldBe("utf-8")
        response contentShouldBe("null")
    }
    
    @Test
    def string {
        var response = GET("/JsonTests/string")
        response shouldBeOk()
        response contentTypeShouldBe("application/json")
        response charsetShouldBe("utf-8")
        response contentShouldBe("{'name':'guillaume'}")
    }
    
    @Test
    def pojo {
        var response = GET("/JsonTests/pojo") 
        assertIsOk(response)
        assertContentType("application/json", response)
        assertCharset("utf-8", response)
        
        implicit val formats = DefaultFormats
        val received = parse(FunctionalTest.getContent(response)).values.asInstanceOf[Map[String, Any]]
        received should have size (3)
        received("name") should be ("No balls")
        received("kittenCount") should be (3)
        received("isNeutered").asInstanceOf[Boolean] should be (true)
    }
    
    @Test
    def model {
        var response = GET("/JsonTests/javaModel") 
        assertIsOk(response)
        assertContentType("application/json", response)
        assertCharset("utf-8", response)
        
        implicit val formats = DefaultFormats
        val received = parse(FunctionalTest.getContent(response)).values.asInstanceOf[Map[String, Any]]
        received should have size (5)
        received should contain key ("id")
        received("email") should be ("guillaume@gmail.com")
        received("password") should be ("12e")
        received("fullname") should be ("Guillaume")
        received("isAdmin").asInstanceOf[Boolean] should be (false)
    }
    
    @Test
    def poso {
        var response = GET("/JsonTests/poso") 
        assertIsOk(response)
        assertContentType("application/json", response)
        assertCharset("utf-8", response)
        
        implicit val formats = DefaultFormats
        val received = parse(FunctionalTest.getContent(response)).values.asInstanceOf[Map[String, Any]]
        received should have size (4)
        received("name") should be ("Sylvester")
        received("kittenCount") should be (5)
        received("isNeutered").asInstanceOf[Boolean] should be (false)
    }
    
    @Test
    def scalaModel {
        var response = GET("/JsonTests/scalaModel") 
        assertIsOk(response)
        assertContentType("application/json", response)
        assertCharset("utf-8", response)
        
        implicit val formats = DefaultFormats
        val received = parse(FunctionalTest.getContent(response)).values.asInstanceOf[Map[String, Any]]
        received should have size (5)
        received should contain key ("id")
        received("email") should be ("guillaume@gmail.com")
        received("password") should be ("12e")
        received("fullname") should be ("Guillaume")
        received("isAdmin").asInstanceOf[Boolean] should be (false)
    }
    
    @Test
    def caseClass {
        var response = GET("/JsonTests/caseClass") 
        assertIsOk(response)
        assertContentType("application/json", response)
        assertCharset("utf-8", response)
        assertContentEquals("""{"id":7,"email":"guillaume@gmail.com","fullname":"Guillaume"}""", response)
    }
    
    @Test
    def list {
        var response = GET("/JsonTests/list") 
        assertIsOk(response)
        assertContentType("application/json", response)
        assertCharset("utf-8", response)
        
        implicit val formats = DefaultFormats
        val received = parse(FunctionalTest.getContent(response)).values.asInstanceOf[List[Any]]
        received should have size (2)
        
        val first = received(0).asInstanceOf[Map[String, Any]]
        first("id") should be (7)
        first("email") should be ("guillaume@gmail.com")
        first("fullname") should be ("Guillaume")
        
        val second = received(1).asInstanceOf[Map[String, Any]]
        second("id") should be (10)
        second("email") should be ("bob@gmail.com")
        second("fullname") should be ("Bob")
    }
}
