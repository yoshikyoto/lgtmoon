package actors

import akka.actor.Actor
import externals.google.CustomSearchAdapter

case class ImageGenerateMessage(id: Long, keyword: String)

/**
 * 
 */
class ImageActor extends Actor {

  override def receive: Receive = {
    case ImageGenerateMessage(id, keyword) => {
      val searchFuture = CustomSearchAdapter.imageUrl(keyword)

    }
  }

}
