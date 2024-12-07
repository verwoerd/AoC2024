package day07.part2

import day07.part1.readEquations
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 07/12/2024
 */
fun day07Part2(input: BufferedReader): Any {
  return input.readEquations(::possibleResult)
}

tailrec fun possibleResult(list: List<Long>, results: List<Long> = emptyList()): List<Long> {
  if (list.isEmpty()) return results
  val current = list.first()
  val next = if (results.isEmpty()) listOf(current)
  else results.map { it + current } + results.map { it * current } + results.map { "$it$current".toLong() }
  return possibleResult(list.drop(1), next)
}

