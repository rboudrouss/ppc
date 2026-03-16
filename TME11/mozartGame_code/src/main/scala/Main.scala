package upmc.akka.ppc

import akka.actor.{Props,  Actor,  ActorRef,  ActorSystem}

object Concert extends App {
  println("starting Mozart's game")

  val system = ActorSystem("MozartGame")

  val dataBase = system.actorOf(Props[DataBaseActor], "DataBaseActor")
  val provider = system.actorOf(Props(new ProviderActor(dataBase)), "ProviderActor")
  val player = system.actorOf(Props[PlayerActor], "PlayerActor")
  val conductor = system.actorOf(Props(new ConductorActor(provider, player)), "ConductorActor")

  conductor ! ConductorActor.StartGame
 }
