package reductions

import scala.annotation._
import org.scalameter._
import common._

object ParallelParenthesesBalancingRunner {

  @volatile var seqResult = false

  @volatile var parResult = false

  val standardConfig = config(
    Key.exec.minWarmupRuns -> 40,
    Key.exec.maxWarmupRuns -> 80,
    Key.exec.benchRuns -> 120,
    Key.verbose -> true
  ) withWarmer(new Warmer.Default)

  def main(args: Array[String]): Unit = {
    val length = 100000000
    val chars = new Array[Char](length)
    val threshold = 10000
    val seqtime = standardConfig measure {
      seqResult = ParallelParenthesesBalancing.balance(chars)
    }
    println(s"sequential result = $seqResult")
    println(s"sequential balancing time: $seqtime ms")

    val fjtime = standardConfig measure {
      parResult = ParallelParenthesesBalancing.parBalance(chars, threshold)
    }
    println(s"parallel result = $parResult")
    println(s"parallel balancing time: $fjtime ms")
    println(s"speedup: ${seqtime / fjtime}")
  }
}

object ParallelParenthesesBalancing {

  /** Returns `true` iff the parentheses in the input `chars` are balanced.
   */
  def balance(chars: Array[Char]): Boolean = {
    val sum = chars.foldLeft(0)((x, y) =>
      if (x < 0) return false
      else {
      if (y == '(') x + 1
      else if (y == ')') x - 1
      else x
    })
    sum == 0
//    def nested(idx: Int, acc: Int): Boolean = {
//      if (acc < 0) false
//      else if (idx == chars.length) acc == 0
//      else {
//        if (chars(idx) == '(') nested(idx+1, acc+1)
//        else if (chars(idx) == ')') nested(idx+1, acc-1)
//        else nested(idx+1, acc)
//      }
//    }
//    nested(0, 0)
  }

  /** Returns `true` iff the parentheses in the input `chars` are balanced.
   */
  def parBalance(chars: Array[Char], threshold: Int): Boolean = {

    def traverse(idx: Int, until: Int, sum: Int, min: Int): (Int, Int) = {
      if (idx >= until) (sum, min)
      else {
       if (chars(idx) == '(') traverse(idx+1, until, sum+1, min)
       else if (chars(idx) == ')') {
         if (sum - 1 < min) traverse(idx+1, until, sum-1, sum-1)
         else traverse(idx+1, until, sum-1, min)
       }
       else traverse(idx+1, until, sum, min)
      }
    }

    def reduce(from: Int, until: Int): (Int, Int) = {
      if (until - from <= threshold) {
        traverse(from, until, 0, 0)
      } else {
        val mid = from + (until - from) / 2
        val (pair1, pair2) = parallel(reduce(from, mid), reduce(mid, until))

        (pair1._1 + pair2._1, pair1._2 min (pair1._1 + pair2._2))
      }
    }

    reduce(0, chars.length) == (0, 0)
  }

  // For those who want more:
  // Prove that your reduction operator is associative!

}
