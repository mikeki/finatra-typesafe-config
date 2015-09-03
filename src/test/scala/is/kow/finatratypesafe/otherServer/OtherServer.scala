package is.kow.finatratypesafe.otherServer

import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.routing.HttpRouter
import is.kow.finatratypesafe.BasicAuthFilter

class OtherServer extends HttpServer {
  override def configureHttp(router: HttpRouter) = {
    router.filter[BasicAuthFilter]

  }
}
