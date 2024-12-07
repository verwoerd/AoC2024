package day07.part1

import spaceSeperatedLongs
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 07/12/2024
 */

fun day07Part1(input: BufferedReader): Any {
  return input.readEquations(::possibleResult)
}


fun BufferedReader.readEquations(body: (List<Long>, List<Long>) -> List<Long>) = lineSequence().map { line ->
  val (left, right) = line.split(":")
  left.toLong() to right.trim().spaceSeperatedLongs()
}.map { it.first to body(it.second, emptyList()) }
  .filter { (left, right) -> right.any { it == left } }.sumOf { it.first }


tailrec fun possibleResult(list: List<Long>, results: List<Long> = emptyList()): List<Long> {
  if (list.isEmpty()) return results
  val current = list.first()
  val next = if (results.isEmpty()) listOf(current)
  else results.map { it + current } + results.map { it * current }
  return possibleResult(list.drop(1), next)
}

