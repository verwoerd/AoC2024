package day13.part1

import java.io.BufferedReader
import kotlin.math.abs

/**
 * @author verwoerd
 * @since 13/12/2024
 */
fun day13Part1(input: BufferedReader): Any {
  val machines = input.parseInput()
  return machines.sumOf { it.solve() }
}

val aRegex = Regex("""Button A: X\+(\d+), Y\+(\d+)""")
val bRegex = Regex("""Button B: X\+(\d+), Y\+(\d+)""")
val priceRegex = Regex("""Prize: X=(\d+), Y=(\d+)""")

fun BufferedReader.parseInput(): List<ClawMachine> =
  lineSequence().windowed(size = 4, partialWindows = true, step = 4) { (l1, l2, l3) ->
    val (aX, aY) = aRegex.matchEntire(l1)!!.destructured
    val (bX, bY) = bRegex.matchEntire(l2)!!.destructured
    val (priceX, priceY) = priceRegex.matchEntire(l3)!!.destructured
    ClawMachine(aX.toLong(), aY.toLong(), bX.toLong(), bY.toLong(), priceX.toLong(), priceY.toLong())
  }.toList()

data class ClawMachine(
  val aX: Long,
  val aY: Long,
  val bX: Long,
  val bY: Long,
  val priceX: Long,
  val priceY: Long
) {
  fun solve(): Long {
    val bD = (aX * priceY - aY * priceX).toDouble() / (aX * bY - aY * bX)
    val b = bD.toLong()
    if (abs(b - bD) > 10e-6) return 0
    val aD = (priceX - b * bX).toDouble() / aX
    val a = aD.toLong()
    if (abs(a - aD) > 10e-6) return 0
    return 3 * a + b
  }
}
