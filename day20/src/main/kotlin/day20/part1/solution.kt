package day20.part1

import Coordinate
import adjacentCoordinates
import manhattanDistance
import toCoordinateMap
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 20/12/2024
 */
fun day20Part1(input: BufferedReader): Any {
  val path = input.findShortestPath()
  return path.indices.sumOf { i ->
    (i + 102..path.lastIndex).count {
      val distance = manhattanDistance(path[i], path[it])
      distance == 2
    }
  }
}

fun BufferedReader.findShortestPath(): List<Coordinate> {
  val maze = toCoordinateMap()
  val start = maze.entries.first { it.value == 'S' }.key
  val end = maze.entries.first { it.value == 'E' }.key
  val seen = mutableSetOf<Coordinate>()
  val path = mutableListOf<Coordinate>()
  var current = start
  while (current != end) {
    seen.add(current)
    path.add(current)
    current = adjacentCoordinates(current).filter { it !in seen }.filter { maze[it]!! != '#' }.first()
  }
  path.add(end)
  return path
}
