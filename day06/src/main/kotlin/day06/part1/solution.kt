package day06.part1

import Coordinate
import FourDirectionFlipped
import toCoordinateMap
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 06/12/2024
 */
fun day06Part1(input: BufferedReader): Any {
  val maze = input.toCoordinateMap()
  var direction = FourDirectionFlipped.UP
  var current = maze.keys.first { maze[it] == '^' }
  val seen = mutableSetOf<Coordinate>()
  while (current in maze) {
    seen.add(current)
    if (maze[direction + current] == '#') {
      direction = direction.turnRight()
    } else {
      current = direction + current
    }
  }
  return seen.size
}

