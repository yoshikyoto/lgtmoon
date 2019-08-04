package module

import play.api.libs.concurrent.AkkaGuiceSupport
import com.google.inject.AbstractModule

/** DI */
class ImageActor extends AbstractModule with AkkaGuiceSupport {
  override def configure = {
    bindActor[actor.ImageActor]("image-actor")
  }
}
