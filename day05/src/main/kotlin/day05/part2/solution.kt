package day05.part2

import day05.part1.filterValidPageOrders
import day05.part1.parse
import swap
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 05/12/2024
 */
fun day05Part2(input: BufferedReader): Any {
  val (order, print) = input.parse()
  val outOfOrder = print.filter { !it.filterValidPageOrders(order) }
  return outOfOrder.map { list ->
    val current = list.toMutableList()
    var index = 0
    var seen = mutableSetOf<Int>()
    while (index < current.size) {
      if (order.containsKey(current[index])) {
        val position =
          order[current[index]]!!.filter { it in seen && it in current }.minOfOrNull { current.indexOf(it) }
        if (position != null) {
          current.swap(position, index)
          index = 0
          seen = mutableSetOf()
        }
      }
      seen.add(current[index])
      index++
    }
    current
  }.sumOf { it[it.size / 2] }
}
