package day20.part2

import day20.part1.findShortestPath
import manhattanDistance
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 20/12/2024
 */
fun day20Part2(input: BufferedReader): Any {
  val path = input.findShortestPath()
  return path.indices.sumOf { first ->
    (first + 102..path.lastIndex).count {
      val distance = manhattanDistance(path[first], path[it])
      distance <= 20 && (it - first) - distance >= 100
    }
  }
}

