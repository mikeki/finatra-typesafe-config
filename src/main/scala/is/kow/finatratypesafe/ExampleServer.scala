package is.kow.finatratypesafe

import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.filters.CommonFilters
import com.twitter.finatra.http.routing.HttpRouter
import is.kow.finatratypesafe.modules.FinatraTypesafeConfigModule

object ExampleServerMain extends ExampleServer

class ExampleServer extends HttpServer {

  override val modules = Seq(FinatraTypesafeConfigModule)

  override def configureHttp(router: HttpRouter): Unit = {
    //In here is way too late
    router
      .filter[CommonFilters]
      .add[BasicAuthFilter, ExampleController]
  }
}
