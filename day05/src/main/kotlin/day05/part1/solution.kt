package day05.part1

import splitSeperatedInts
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 05/12/2024
 */
fun day05Part1(input: BufferedReader): Any {
  val (order, print) = input.parse()
  return print.filter { it.filterValidPageOrders(order) }.sumOf { it[it.size / 2] }
}

fun List<Int>.filterValidPageOrders(order: Map<Int, List<Int>>): Boolean {
  val seen = mutableSetOf<Int>()
  var valid = true
  forEach { page ->
    if (order.containsKey(page)) {
      valid = valid && order[page]!!.all { it !in seen }
    }
    seen.add(page)
  }
  return valid
}

fun BufferedReader.parse(): Pair<Map<Int, List<Int>>, List<List<Int>>> {
  val raw = readLines()
  return raw.takeWhile { it.isNotBlank() }.map { line -> line.splitSeperatedInts("|") }.groupBy { it.first() }
    .mapValues { entry -> entry.value.map { it[1] } } to raw.reversed().takeWhile { it.isNotBlank() }.reversed()
    .map { it.splitSeperatedInts(",") }
}
