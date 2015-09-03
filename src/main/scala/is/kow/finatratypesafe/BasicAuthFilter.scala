package is.kow.finatratypesafe

import java.nio.charset.StandardCharsets
import java.util.Base64
import javax.inject.Inject

import com.github.racc.tscg.TypesafeConfig
import com.twitter.finagle.http.{Request, Response}
import com.twitter.finagle.{Service, SimpleFilter}
import com.twitter.finatra.http.response.ResponseBuilder
import com.twitter.util.Future
import com.typesafe.scalalogging.slf4j.LazyLogging

class BasicAuthFilter @Inject()
(responseBuilder: ResponseBuilder,
 @TypesafeConfig("auth.user") authUser: String,
 @TypesafeConfig("auth.password") authPass: String
  ) extends SimpleFilter[Request, Response] with LazyLogging {

  def apply(request: Request, service: Service[Request, Response]): Future[Response] = {
    if (authUser.nonEmpty) {
      val authHeader: Option[String] = Option(request.headers().get("Authorization"))
      authHeader.map { value =>
        val base64Creds: String = value.substring("Basic".length()).trim()
        val creds = new String(Base64.getDecoder.decode(base64Creds), StandardCharsets.UTF_8)
        val split = creds.split(":", 2)

        val user = split(0)
        val pass = split(1)

        if (user == authUser && pass == authPass) {
          service(request)
        } else {
          responseBuilder.unauthorized(s"Invalid Credentials!").toFuture
        }
      } getOrElse {
        responseBuilder.unauthorized("Authentication required!").header("WWW-Authenticate", "Basic realm=\"jenkins\"").toFuture
      }
    } else {
      //The authUser is empty, and so we're going to just do the thing
      service(request)
    }
  }
}
