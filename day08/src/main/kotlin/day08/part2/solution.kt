package day08.part2

import day08.part1.generateAntiNodes
import toCoordinateMap
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 08/12/2024
 */
fun day08Part2(input: BufferedReader): Any {
  val map = input.toCoordinateMap()
  val antennas = map.filter { it.value != '.' }.map { it.key to it.value }.groupBy({ it.second }, { it.first })
  return antennas.generateAntiNodes { left, right, diff ->
    val antiNodes = mutableListOf(left, right)
    var nextLeft = (left + diff)
    while (nextLeft in map) {
      antiNodes.add(nextLeft)
      nextLeft += diff
    }
    var nextRight = (right - diff)
    while (nextRight in map) {
      antiNodes.add(nextRight)
      nextRight -= diff
    }
    antiNodes
  }.toSet().size
}
