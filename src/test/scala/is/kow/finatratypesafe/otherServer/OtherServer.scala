package is.kow.finatratypesafe.otherServer

import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.routing.HttpRouter
import is.kow.finatratypesafe.BasicAuthFilter
import is.kow.finatratypesafe.modules.FinatraTypesafeConfigModule

class OtherServer extends HttpServer {

  override val modules = Seq(FinatraTypesafeConfigModule)

  override def configureHttp(router: HttpRouter) = {
    router.filter[BasicAuthFilter]
  }
}
