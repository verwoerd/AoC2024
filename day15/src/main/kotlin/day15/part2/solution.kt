package day15.part2

import Coordinate
import FourDirectionFlipped
import FourDirectionFlipped.*
import linkedListOf
import toCoordinateMap
import xRange
import yRange
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 15/12/2024
 */
fun day15Part2(input: BufferedReader): Any {
  val (maze, directions) = input.readMazeAndDirections()
  val start = maze.firstNotNullOf { (k, v) -> k.takeIf { v == '@' } }
  val (result) = directions.fold(maze to start) { (maze, location), direction ->
    maze.executeMove(location, direction)
  }
  return result.filter { it.value == '[' }.map { it.key.y * 100L + it.key.x }.sum()
}

fun Map<Coordinate, Char>.findBoxesToMove(
  location: Coordinate,
  direction: FourDirectionFlipped
): MutableSet<Coordinate> {
  val found = mutableSetOf<Coordinate>()
  val queue = linkedListOf(location)
  while (queue.isNotEmpty()) {
    val current = queue.removeFirst()
    if (current in found) continue
    when (val char = get(current)!!) {
      '#', '.' -> continue
      '[' -> queue.add(RIGHT + current)
      ']' -> queue.add(LEFT + current)
      else -> error("Unknown character: $char")
    }
    queue.add(direction + current)
    found.add(current)
  }
  return found
}


fun MutableMap<Coordinate, Char>.executeMove(
  location: Coordinate,
  direction: FourDirectionFlipped
): Pair<MutableMap<Coordinate, Char>, Coordinate> {
  val next = direction + location
  if (get(next) == '#') return this to location
  if (get(next) == '.') {
    set(location, '.')
    set(next, '@')
    return this to next
  }
  val toMove = findBoxesToMove(next, direction)
  if (toMove.isEmpty()) error("No boxes to move")
  val canMove = when (direction) {
    UP, DOWN -> {
      toMove.xRange().let { (a, b) -> a..b }.all { x ->
        toMove.filter { it.x == x }.let { list ->
          when (direction) {
            UP -> list.minOf { it.y } - 1
            DOWN -> list.maxOf { it.y } + 1
            else -> error("Invalid direction")
          }
        }.let { get(Coordinate(x, it)) == '.' }
      }
    }

    LEFT, RIGHT -> {
      toMove.yRange().let { (a, b) -> a..b }.all { y ->
        toMove.filter { it.y == y }.let { list ->
          when (direction) {
            LEFT -> list.minOf { it.x } - 1
            RIGHT -> list.maxOf { it.x } + 1
            else -> error("Invalid direction")
          }
        }.let { get(Coordinate(it, y)) == '.' }
      }
    }
  }
  if (!canMove) return this to location

  // move the blob
  toMove.sorted().let {
    when (direction) {
      DOWN, RIGHT -> it.reversed()
      else -> it
    }
  }.forEach { set(direction + it, get(it)!!) }
  // clean the borders
  toMove.filter { direction - it !in toMove }.forEach { set(it, '.') }
  set(next, '@')
  set(location, '.')
  return this to next
}


fun BufferedReader.readMazeAndDirections(): Pair<MutableMap<Coordinate, Char>, List<FourDirectionFlipped>> {
  val lines = lines().toList()
  val maze = lines.takeWhile { it.isNotBlank() }.asSequence().map { line ->
    line.asSequence().flatMap {
      when (it) {
        '.' -> listOf('.', '.')
        'O' -> listOf('[', ']')
        '#' -> listOf('#', '#')
        '@' -> listOf('@', '.')
        else -> error("Unknown character: $it")
      }
    }.joinToString("")
  }.toCoordinateMap()
  val directions = lines.dropWhile { it.isNotBlank() }.drop(1)
    .flatMap { line ->
      line.map {
        when (it) {
          '^' -> UP
          'v' -> DOWN
          '<' -> LEFT
          '>' -> RIGHT
          else -> error("Unknown direction: $it")
        }
      }
    }
  return maze.toMutableMap() to directions
}
