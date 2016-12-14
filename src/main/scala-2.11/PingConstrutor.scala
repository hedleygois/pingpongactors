import akka.actor.{Actor, ActorRef, PoisonPill}

class PingConstrutor(pong: ActorRef) extends Actor {

  var contador = 0

  def ping {
    contador += 1
    println("PING COM CONSTRUTOR!!!")
  }

  def receive = {
    case SimpleStartMessage =>
      ping
      pong ! PingMessage
    case PongMessage =>
      ping
      if (contador > 200) {
        sender ! StopMessage
        context stop self
      } else {
        sender ! PingMessage
      }
  }

}
