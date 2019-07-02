package module

import com.google.inject.AbstractModule
import play.api.libs.concurrent.AkkaGuiceSupport

/** DI */
class ImageActor extends AbstractModule with AkkaGuiceSupport {
  override def configure = {
    bindActor[actor.ImageActor]("image-actor")
  }
}
