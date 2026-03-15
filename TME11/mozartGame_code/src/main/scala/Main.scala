package upmc.akka.ppc

import akka.actor.{Props, Actor, ActorRef, ActorSystem}

object Concert extends App {
  println("starting Mozart's game")

  val system = ActorSystem("MozartGame")

  val database  = system.actorOf(Props[DataBaseActor],                     "database")
  val player    = system.actorOf(Props[PlayerActor],                       "player")
  val provider  = system.actorOf(Props(new ProviderActor(database)),       "provider")
  val conductor = system.actorOf(Props(new ConductorActor(player, provider)), "conductor")

  conductor ! ConductorActor.StartGame
}
