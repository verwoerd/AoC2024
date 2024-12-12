package day12.part2

import Coordinate
import FourDirections
import FourDirections.*
import adjacentCoordinates
import day12.part1.findReachable
import toCoordinateMap
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 12/12/2024
 */
fun day12Part2(input: BufferedReader): Any {
  val plot = input.toCoordinateMap()
  val vegtables = plot.values.distinct().associateWith { seed -> plot.filterValues { seed == it }.keys }
  val seen = mutableSetOf<Coordinate>()
  return vegtables.map { (_, set) ->
    set.sumOf { coordinate ->
      if (coordinate !in seen) {
        val grouping = set.findReachable(coordinate)
        seen.addAll(grouping)
        val area = grouping.size
        val sides = grouping.countSides()
        area * sides
      } else 0
    }
  }.sum()
}

fun Set<Coordinate>.countSides(): Int {
  val fencePerCoordinate = map { location ->
    location to adjacentCoordinates(location).filter { it !in this }
      .map { (location - it).toDirection() ?: error("Weird turn") }.toMutableList()
  }.toMap()
  val result = mutableListOf<Pair<Coordinate, FourDirections>>()
  forEach { coordinate ->
    val fences = fencePerCoordinate[coordinate] ?: return@forEach
    if (UP in fences) {
      generateSequence(LEFT + coordinate) { LEFT + it }.takeWhile { it in this && UP in fencePerCoordinate[it]!! }
        .forEach { fencePerCoordinate[it]!!.remove(UP) }
      generateSequence(RIGHT + coordinate) { RIGHT + it }.takeWhile { it in this && UP in fencePerCoordinate[it]!! }
        .forEach { fencePerCoordinate[it]!!.remove(UP) }
      fencePerCoordinate[coordinate]!!.remove(UP)
      result.add(coordinate to UP)
    }
    if (DOWN in fences) {
      generateSequence(LEFT + coordinate) { LEFT + it }.takeWhile { it in this && DOWN in fencePerCoordinate[it]!! }
        .forEach { fencePerCoordinate[it]!!.remove(DOWN) }
      generateSequence(RIGHT + coordinate) { RIGHT + it }.takeWhile { it in this && DOWN in fencePerCoordinate[it]!! }
        .forEach { fencePerCoordinate[it]!!.remove(DOWN) }
      fencePerCoordinate[coordinate]!!.remove(DOWN)
      result.add(coordinate to DOWN)
    }
    if (LEFT in fences) {
      generateSequence(UP + coordinate) { UP + it }.takeWhile { it in this && LEFT in fencePerCoordinate[it]!! }
        .forEach { fencePerCoordinate[it]!!.remove(LEFT) }
      generateSequence(DOWN + coordinate) { DOWN + it }.takeWhile { it in this && LEFT in fencePerCoordinate[it]!! }
        .forEach { fencePerCoordinate[it]!!.remove(LEFT) }
      fencePerCoordinate[coordinate]!!.remove(LEFT)
      result.add(coordinate to LEFT)
    }
    if (RIGHT in fences) {
      generateSequence(UP + coordinate) { UP + it }.takeWhile { it in this && RIGHT in fencePerCoordinate[it]!! }
        .forEach { fencePerCoordinate[it]!!.remove(RIGHT) }
      generateSequence(DOWN + coordinate) { DOWN + it }.takeWhile { it in this && RIGHT in fencePerCoordinate[it]!! }
        .forEach { fencePerCoordinate[it]!!.remove(RIGHT) }
      fencePerCoordinate[coordinate]!!.remove(RIGHT)
      result.add(coordinate to RIGHT)
    }
  }
  return result.size
}
