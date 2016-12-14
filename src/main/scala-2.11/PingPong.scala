import akka.actor.{ActorSystem, DeadLetter, Props}

object PingPong extends App {

  val system = ActorSystem("PingPong")
  val pong = system.actorOf(Props[Pong], name = "AtorPong")
  val ping = system.actorOf(Props[Ping], name = "AtorPing")
  //val pingConstrutor = system.actorOf(Props(new PingConstrutor(pong)), name = "AtorPingConstrutor")

  ping ! StartMessage(pong)

}
