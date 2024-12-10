package day10.part2

import Coordinate
import adjacentCoordinates
import priorityQueueOf
import toCoordinateMap
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 10/12/2024
 */
fun day10Part2(input: BufferedReader): Any {
  val maze = input.toCoordinateMap()
  val starts = maze.filterValues { it == '0' }.keys
  return starts.sumOf { maze.countReachableRoutes(it) }
}

data class SearchState(
  val coordinate: Coordinate,
  val path: List<Coordinate> = mutableListOf()
)

fun Map<Coordinate, Char>.countReachableRoutes(start: Coordinate): Int {
  val seen = mutableSetOf<SearchState>()
  val queue = priorityQueueOf<SearchState>({ a, b -> a.path.size.compareTo(b.path.size) })
  queue.add(SearchState(start))
  var count = 0
  while (queue.isNotEmpty()) {
    val current = queue.poll()
    if (current in seen) continue
    seen.add(current)
    val currentValue = this[current.coordinate] ?: continue
    if (currentValue == '9') {
      count++
      continue
    }

    adjacentCoordinates(current.coordinate).filter { this[it] == currentValue + 1 }
      .map { SearchState(it, current.path + it) }
      .toCollection(queue)
  }
  return count
}
