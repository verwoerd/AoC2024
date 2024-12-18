package day18.part1

import Coordinate
import adjacentCoordinates
import manhattanDistance
import origin
import priorityQueueOf
import splitSeperatedInts
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 18/12/2024
 */
fun day18Part1(input: BufferedReader, x: Int = 70, y: Int = 70, time: Int = 1024): Any {
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

  val path = mazeTime[time]!!.findPath()
  return path.size - 1
}

fun BufferedReader.readBitOrder() =
  lineSequence().map { line -> line.splitSeperatedInts(",").let { (x, y) -> Coordinate(x, y) } }.toList()
