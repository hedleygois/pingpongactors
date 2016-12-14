import akka.actor.{Actor, ActorRef, Kill, PoisonPill}

import scala.concurrent.{Await, Future}
import akka.pattern.ask
import akka.util.Timeout

import scala.concurrent.duration._

case object PingMessage
case object PongMessage
case object FuturePongMessage
case object StopMessage

case class StartMessage(pong: ActorRef)
case object SimpleStartMessage

class Ping extends Actor {
  import context.dispatcher
  import akka.pattern.pipe

  implicit val timeout = Timeout(100 millis)
  // Mutável, mas lembre-se Actors sofrem menos com mutabilidade
  var contador = 0

  def ping {
    contador += 1
    println("PING!!!")
  }

  def futurePing: Future[Unit] = {
    Future.successful(println("PING ASYNC!"))
  }

  def receive = {
    case StartMessage(pong) =>
      ping
      pong ! PingMessage
      //val future = pong ? PingMessage // - Retorna uma Future!
      //val result = Await.result(future, timeout.duration) // - Blocka! Evite!
      //self ! result
    case PongMessage =>
      ping
      if (contador > 200) {
        sender ! StopMessage
        context stop self
        //context.system.terminate()
        //sender ! PoisonPill // ALTERNATIVA!
        //sender ! Kill       // ALTERNATIVA IGNORANTE!
      } else {
        sender ! PingMessage
      }
    case FuturePongMessage =>
      sender ! PingMessage
      futurePing pipeTo self
  }

  override def postStop = {
    super.postStop
    println("Ping morreu :(")
  }

}
