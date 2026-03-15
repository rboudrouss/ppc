package upmc.akka.ppc

import akka.actor.{Actor, ActorRef}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

object ConductorActor {
  case object StartGame
}

class ConductorActor(player: ActorRef, provider: ActorRef) extends Actor {
  import DataBaseActor._
  import ConductorActor._

  val random = new scala.util.Random()

  def rollDice(): Int = random.nextInt(6) + 1 + random.nextInt(6) + 1

  def receive = {
    case StartGame => {
      val diceResult = rollDice()
      println(s"Dice rolled: $diceResult")
      provider ! GetMeasure(diceResult)
    }
    case m: Measure => {
      player ! m
      context.system.scheduler.scheduleOnce(1800 milliseconds, self, StartGame)
    }
  }
}
