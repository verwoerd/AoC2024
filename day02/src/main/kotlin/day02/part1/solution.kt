package day02.part1

import java.io.BufferedReader
import kotlin.Long.Companion.MIN_VALUE
import kotlin.math.max
import kotlin.math.min

/**
 * @author verwoerd
 * @since 02/12/2024
 */
fun day02Part1(input: BufferedReader): Any {
  val reports = input.readReports()
  return reports.count { it.isSafeReport() }
}

fun List<Long>.isSafeReport(): Boolean {
  val (up, down) = fold(Triple(0L, 0L, MIN_VALUE)) { (up, down, last), current ->
    when {
      last == MIN_VALUE -> Triple(up, down, current)
      current > last -> Triple(max(up, current - last), down, current)
      current < last -> Triple(up, min(down, current - last), current)
      else -> return false
    }
  }
  return (up == 0L && down >= -3) || (down == 0L && up <= 3)
}

fun BufferedReader.readReports() =
  readLines().map { it.split(" ").map { a -> a.toLong() } }
