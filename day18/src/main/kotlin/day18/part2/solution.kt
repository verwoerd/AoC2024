package day18.part2

import Coordinate
import adjacentCoordinates
import day18.part1.readBitOrder
import manhattanDistance
import origin
import priorityQueueOf
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 18/12/2024
 */
fun day18Part2(input: BufferedReader, x: Int = 70, y: Int = 70): Any {
  val bits = input.readBitOrder()
  val mazeTime = bits.indices.associateWith { index -> bits.take(index) }
  val start = origin
  val end = Coordinate(x, y)
  val xRange = start.x..end.x
  val yRange = start.y..end.y

  fun List<Coordinate>.findPath(): List<Coordinate> {
    val queue = priorityQueueOf(
      comparator = { a, b ->
        when (val s = a.second.compareTo(b.second)) {
          0 -> manhattanDistance(a.first, end).compareTo(manhattanDistance(b.first, end))
          else -> s
        }
      }, Triple(start, 0, listOf(start))
    )
    val seen = mutableSetOf<Coordinate>()
    while (queue.isNotEmpty()) {
      val (current, steps, path) = queue.poll()
      if (!seen.add(current)) continue
      if (current == end) return path
      adjacentCoordinates(current).filter { it !in this }
        .filter { it.x in xRange && it.y in yRange }
        .map { Triple(it, steps + 1, path + it) }
        .toCollection(queue)
    }
    return emptyList()
  }

  var path = mazeTime[1]!!.findPath()
  var problem = origin
  while (path.isNotEmpty()) {
    problem = bits.first { it in path }
    path = mazeTime[bits.indexOf(problem) + 1]!!.findPath()
  }
  return bits[bits.indexOf(problem)].let { "${it.x},${it.y}" }
}
