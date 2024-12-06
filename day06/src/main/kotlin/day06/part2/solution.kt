package day06.part2

import Coordinate
import FourDirectionFlipped
import toCoordinateMap
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 06/12/2024
 */
fun day06Part2(input: BufferedReader): Any {
  val maze = input.toCoordinateMap()
  start = maze.keys.first { maze[it] == '^' }
  return maze.keys.count { maze.isLoop(it) }
}

lateinit var start: Coordinate

fun Map<Coordinate, Char>.isLoop(obstacle: Coordinate): Boolean {
  if (get(obstacle) != '.') return false
  var direction = FourDirectionFlipped.UP
  var current = start
  val seen = mutableSetOf<Pair<Coordinate, FourDirectionFlipped>>()
  while (current in this && current to direction !in seen) {
    seen.add(current to direction)
    if (get(direction + current) == '#' || direction + current == obstacle) {
      direction = direction.turnRight()
    } else {
      current = direction + current
    }
  }
  return current in this
}
