package is.kow.finatratypesafe

import javax.inject.{Inject, Singleton}

import com.twitter.finagle.httpx.Request
import com.twitter.finatra.http.Controller
import is.kow.finatratypesafe.services.ExampleService

@Singleton
class ExampleController @Inject()(exampleService: ExampleService) extends Controller {
  get("/examples") { request: Request =>
    response.ok(exampleService.listExamples())
  }
}
