package day10.part1

import Coordinate
import adjacentCoordinates
import priorityQueueOf
import toCoordinateMap
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 10/12/2024
 */
fun day10Part1(input: BufferedReader): Any {
  val maze = input.toCoordinateMap()
  val starts = maze.filterValues { it == '0' }.keys
  return starts.sumOf { maze.countReachableHills(it) }
}

fun Map<Coordinate, Char>.countReachableHills(start: Coordinate): Int {
  val seen = mutableSetOf<Coordinate>()
  val queue = priorityQueueOf<Coordinate>()
  queue.add(start)
  var count = 0
  while (queue.isNotEmpty()) {
    val current = queue.poll()
    if (current in seen) continue
    seen.add(current)
    val currentValue = this[current] ?: continue
    if (currentValue == '9') {
      count++
      continue
    }
    adjacentCoordinates(current).filter { this[it] == currentValue + 1 }.toCollection(queue)
  }
  return count
}
