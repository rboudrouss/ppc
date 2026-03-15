package upmc.akka.ppc

import akka.actor.{Actor, ActorRef}

class ProviderActor(database: ActorRef) extends Actor {
  import DataBaseActor._

  // Mozart dice game tables (1-indexed measure numbers)
  val table1 = Array(
    Array(96,  22,  141, 41,  105, 122, 11,  30),  // dice = 2
    Array(32,  6,   128, 63,  146, 46,  134, 81),  // dice = 3
    Array(69,  95,  158, 13,  153, 55,  110, 24),  // dice = 4
    Array(40,  17,  113, 85,  161, 2,   159, 100), // dice = 5
    Array(148, 74,  163, 45,  80,  97,  36,  107), // dice = 6
    Array(104, 157, 27,  167, 154, 68,  96,  26),  // dice = 7
    Array(152, 60,  171, 89,  176, 130, 53,  70),  // dice = 8
    Array(119, 84,  114, 50,  140, 86,  169, 94),  // dice = 9
    Array(98,  142, 42,  91,  139, 60,  148, 72),  // dice = 10
    Array(3,   87,  165, 124, 173, 34,  67,  105), // dice = 11
    Array(54,  130, 10,  103, 28,  37,  106, 5)    // dice = 12
  )

  val table2 = Array(
    Array(70,  121, 26,  9,   112, 49,  109, 14),  // dice = 2
    Array(117, 39,  126, 56,  174, 18,  116, 83),  // dice = 3
    Array(66,  139, 15,  132, 73,  58,  145, 79),  // dice = 4
    Array(90,  176, 7,   34,  67,  160, 52,  170), // dice = 5
    Array(25,  143, 64,  125, 76,  136, 1,   93),  // dice = 6
    Array(138, 71,  150, 29,  101, 162, 23,  151), // dice = 7
    Array(16,  155, 57,  175, 43,  168, 89,  172), // dice = 8
    Array(120, 33,  65,  77,  164, 103, 128, 161), // dice = 9
    Array(82,  45,  44,  48,  38,  31,  75,  127), // dice = 10
    Array(154, 141, 100, 13,  87,  166, 21,  59),  // dice = 11
    Array(135, 110, 47,  97,  122, 4,   35,  20)   // dice = 12
  )

  var currentPosition = 0
  var conductor: ActorRef = _

  def receive = {
    case GetMeasure(diceResult) => {
      conductor = sender()
      val part   = currentPosition / 8
      val column = currentPosition % 8
      val row    = diceResult - 2
      val table  = if (part == 0) table1 else table2
      val measureIndex = table(row)(column) - 1  // convert to 0-indexed
      currentPosition = (currentPosition + 1) % 16
      database ! GetMeasure(measureIndex)
    }
    case m: Measure => {
      conductor ! m
    }
  }
}
