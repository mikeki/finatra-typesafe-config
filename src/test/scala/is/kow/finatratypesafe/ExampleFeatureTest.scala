package is.kow.finatratypesafe

import java.nio.charset.StandardCharsets
import java.nio.file.Files

import com.twitter.finagle.http.Status._
import com.twitter.finatra.http.test.{EmbeddedHttpServer, HttpTest}

class ExampleFeatureTest extends HttpTest {

  override def afterAll() = {
    server.close()
  }

  //Create a new configuration file, and use the data from here, and then barf it out to a file that I can pass in
  def testConfig =
    s"""
       |example {
       |thingy: test1
       |thingy2: "test2"
       |magicNumber: 4321
       |}
       |""".stripMargin

  //Write the test config out to a test file
  val tempFilePath = Files.createTempFile("exampleConfig", ".config")
  Files.write(tempFilePath, testConfig.getBytes(StandardCharsets.UTF_8))


  val server = new EmbeddedHttpServer(new ExampleServer,
    clientFlags = Map(
      "config.file" -> tempFilePath.toString
    )
  )

  "Gets the example strings as configured" in {
    server.httpGet(
      path = "/examples",
      andExpect = Ok,
      withJsonBody =
        """
          |[ "test1", "test2", "4321"]
        """.stripMargin
    )
  }
}
