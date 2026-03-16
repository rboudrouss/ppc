package upmc.akka.ppc

import akka.actor.{Actor, ActorRef}
import scala.util.Random
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

object ConductorActor {
  case object StartGame
}

class ConductorActor(provider: ActorRef, player: ActorRef) extends Actor {

  import DataBaseActor._
  import ConductorActor._

  val scheduler = context.system.scheduler
  val DUREE_MESURE = 1800.milliseconds

  def diceSum(): Int = Random.nextInt(6) + 1 + Random.nextInt(6) + 1

  def receive = {
    case StartGame =>
      provider ! GetMeasure(diceSum())

    case Measure(chords) =>
      player ! Measure(chords)
      scheduler.scheduleOnce(DUREE_MESURE, self, StartGame)
  }
}
