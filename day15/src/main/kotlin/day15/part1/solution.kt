package day15.part1

import Coordinate
import FourDirectionFlipped
import toCoordinateMap
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 15/12/2024
 */
fun day15Part1(input: BufferedReader): Any {
  val (maze, directions) = input.readMazeAndDirections()
  val start = maze.firstNotNullOf { (k, v) -> k.takeIf { v == '@' } }
  val (result) = directions.fold(maze to start) { (maze, location), direction ->
    maze.executeMove(location, direction)
  }
  return result.filter { it.value == 'O' }.map { it.key.y * 100 + it.key.x }.sum()
}

fun MutableMap<Coordinate, Char>.executeMove(
  location: Coordinate, direction: FourDirectionFlipped
): Pair<MutableMap<Coordinate, Char>, Coordinate> {
  val next = direction + location
  if (get(next) == '#') return this to location
  if (get(next) == '.') {
    set(location, '.')
    set(next, '@')
    return this to next
  }
  var firstFree = direction + next
  while (get(firstFree) == 'O') {
    firstFree = direction + firstFree
  }
  if (get(firstFree) == '#') return this to location
  while (firstFree != next) {
    set(firstFree, 'O')
    firstFree = direction - firstFree
  }
  set(next, '@')
  set(location, '.')
  return this to next
}


fun BufferedReader.readMazeAndDirections(): Pair<MutableMap<Coordinate, Char>, List<FourDirectionFlipped>> {
  val lines = lines().toList()
  val maze = lines.takeWhile { it.isNotBlank() }.asSequence().toCoordinateMap()
  val directions = lines.dropWhile { it.isNotBlank() }.drop(1).flatMap { line ->
    line.map {
      when (it) {
        '^' -> FourDirectionFlipped.UP
        'v' -> FourDirectionFlipped.DOWN
        '<' -> FourDirectionFlipped.LEFT
        '>' -> FourDirectionFlipped.RIGHT
        else -> error("Unknown direction: $it")
      }
    }
  }
  return maze.toMutableMap() to directions
}

