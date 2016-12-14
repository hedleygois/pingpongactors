import akka.actor.Actor

class Pong extends Actor {

  def receive = {
    case PingMessage =>
      println(" PONG!!!")
      sender ! PongMessage
      //sender ! FuturePongMessage
    case StopMessage =>
      println("Ator pong morreu!")
      context stop self
  }

  override def postStop = {
    super.postStop
    println("Ator pong não está entre nós")
  }

}
