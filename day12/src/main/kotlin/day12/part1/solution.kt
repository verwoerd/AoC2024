package day12.part1

import Coordinate
import adjacentCoordinates
import toCoordinateMap
import java.io.BufferedReader
import java.util.*

/**
 * @author verwoerd
 * @since 12/12/2024
 */
fun day12Part1(input: BufferedReader): Any {
  val plot = input.toCoordinateMap()
  val vegtables = plot.values.distinct().associateWith { seed -> plot.filterValues { seed == it }.keys }
  val seen = mutableSetOf<Coordinate>()
  return vegtables.map { (_, set) ->
    set.sumOf { coordinate ->
      if (coordinate !in seen) {
        val grouping = set.findReachable(coordinate)
        seen.addAll(grouping)
        val area = grouping.size
        val peremiter = grouping.findPerimiter()
        area * peremiter
      } else 0
    }
  }.sum()
}

fun Set<Coordinate>.findReachable(from: Coordinate): Set<Coordinate> {
  val queue = LinkedList<Coordinate>()
  val seen = mutableSetOf<Coordinate>()
  queue.add(from)
  while (queue.isNotEmpty()) {
    val current = queue.removeFirst()
    if (current in seen) continue
    seen.add(current)
    adjacentCoordinates(current)
      .filter { it in this }
      .forEach(queue::add)
  }
  return seen
}

fun Set<Coordinate>.findPerimiter(): Int =
  sumOf { location ->
    adjacentCoordinates(location).count { it !in this }
  }

