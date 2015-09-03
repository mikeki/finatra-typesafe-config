package is.kow.finatratypesafe.modules

import com.github.racc.tscg.TypesafeConfigModule
import com.twitter.inject.TwitterModule
import com.typesafe.config.{Config, ConfigFactory}

object FinatraTypesafeConfigModule extends TwitterModule {
  val configurationFile = flag("config.file", "", "Optional config file to override settings")

  lazy val config: Config = {
    val specified = configurationFile()
    if (specified.nonEmpty) {
      ConfigFactory.load(specified)
    } else {
      ConfigFactory.load()
    }
  }

  override val modules = Seq(TypesafeConfigModule.fromConfig(config))
}
