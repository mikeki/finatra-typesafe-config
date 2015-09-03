package is.kow.finatratypesafe.modules

import com.github.racc.tscg.TypesafeConfigModule
import com.twitter.inject.{Logging, TwitterModule}
import com.typesafe.config.{Config, ConfigFactory}

object FinatraTypesafeConfigModule extends TwitterModule with Logging {
  val configurationFile = flag("config.file", "", "Optional config file to override settings")

  //TODO: this is still reading the flag too soon, so it'll always be ""
  lazy val config: Config = {
    val specified = configurationFile()
    logger.info(s"FLAG VALUE: $specified")
    if (specified.nonEmpty) {
      logger.info(s"LOADING CONFIG FILE: $specified")
      ConfigFactory.load(specified)
    } else {
      logger.warn("LOADING DEFAULT CONFIGURATION ONLY!")
      ConfigFactory.load()
    }
  }

  override val modules = Seq(TypesafeConfigModule.fromConfig(config))
}
