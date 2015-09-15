package is.kow.finatratypesafe.modules

import com.github.racc.tscg.TypesafeConfigModule
import com.twitter.inject.{Logging, TwitterModule}
import com.typesafe.config.{Config, ConfigFactory}

object FinatraTypesafeConfigModule extends TwitterModule with Logging {
  val configurationFile = flag("config.file", "", "Optional config file to override settings")

  override def configure() = {
    val specified = configurationFile()

    val config = if (specified.nonEmpty) {
      ConfigFactory.load(specified)
    } else {
      ConfigFactory.load()
    }

    install(TypesafeConfigModule.fromConfig(config))
  }
}
