package is.kow.finatratypesafe.services

import javax.inject.{Inject, Singleton}

import com.github.racc.tscg.TypesafeConfig
import com.typesafe.scalalogging.slf4j.LazyLogging

@Singleton
class ExampleService @Inject()
(
  @TypesafeConfig("example.thingy") thingy: String,
  @TypesafeConfig("example.thingy2") thingy2: String,
  @TypesafeConfig("example.magicNumber") magicNumber: Int
  ) extends LazyLogging {

  def listExamples(): Seq[String] = {
    Seq(
      thingy,
      thingy2,
      magicNumber.toString
    )
  }
}
