package play.mvc.results

import java.lang.reflect.{Constructor=>JConstructor}
import play.mvc.ControllerDelegate
import play.mvc.Http
import play.classloading.enhancers.LocalvariablesNamesEnhancer
import net.liftweb.json.ParameterNameReader
import net.liftweb.json.DefaultFormats
import net.liftweb.json.FieldSerializer
import net.liftweb.json.Serialization.write

class ScalaRenderJson(obj: AnyRef) extends Result {
    var json = ScalaRenderJson.toJson(obj)
    
    def apply(request: Http.Request, response: Http.Response) {
        setContentTypeIfNotSet(response, "application/json; charset=utf-8");
        response.out.write(json.getBytes("utf-8"));
    }
}

object ScalaRenderJson {
    object PlayParameterNameReader extends ParameterNameReader
    {
        def lookupParameterNames(constructor: JConstructor[_]): Traversable[String] = {
            import collection.JavaConversions._
            return LocalvariablesNamesEnhancer.lookupParameterNames(constructor)
        }
    }

    def toJson(obj: AnyRef) = obj match {
        case s:String => s
        case o        => {
            implicit val formats = new DefaultFormats {
                override val parameterNameReader = PlayParameterNameReader
            } + FieldSerializer[AnyRef]()
            write(o)
        }
    }
}