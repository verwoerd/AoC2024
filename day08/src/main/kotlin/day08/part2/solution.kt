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
    generateSequence(left) { it + diff }.takeWhile { it in map }
      .toList() + generateSequence(right) { it - diff }.takeWhile { it in map }.toList()
  }.toSet().size
}
