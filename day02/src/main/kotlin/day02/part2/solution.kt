package day02.part2

import day02.part1.isSafeReport
import day02.part1.readReports
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 02/12/2024
 */
fun day02Part2(input: BufferedReader): Any {
  val reports = input.readReports()
  return reports.count { it.isDampedSafeReport() }
}

fun List<Long>.isDampedSafeReport(): Boolean {
  if (isSafeReport()) return true
  return indices.any { (take(it) + drop(it + 1)).isSafeReport() }
}
