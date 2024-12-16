package day16.part1

import Coordinate
import FourDirectionFlipped
import manhattanDistance
import priorityQueueOf
import toCoordinateMap
import java.io.BufferedReader
import java.util.*

/**
 * @author verwoerd
 * @since 16/12/2024
 */
fun day16Part1(input: BufferedReader): Any {
  val (maze, queue) = input.readInput()
  val seen = mutableSetOf<Pair<Coordinate, FourDirectionFlipped>>()
  while (queue.isNotEmpty()) {
    val current = queue.poll()
    if (!seen.add(current.seenState)) continue
    if (current.location == end) return current.score
    maze.validMoves(current).toCollection(queue)
  }
  error("No path found")
}

lateinit var end: Coordinate

fun BufferedReader.readInput(): Pair<Map<Coordinate, Char>, PriorityQueue<ReindeerState>> {
  val maze = lineSequence().toCoordinateMap()
  val start = maze.firstNotNullOf { (point, value) -> point.takeIf { value == 'S' } }
  end = maze.firstNotNullOf { (point, value) -> point.takeIf { value == 'E' } }
  val queue = priorityQueueOf(ReindeerState(start, FourDirectionFlipped.RIGHT, 0))
  return maze to queue
}

fun Map<Coordinate, Char>.validMoves(current: ReindeerState) = listOfNotNull(
  current.copy(
    direction = current.direction.turnLeft(),
    score = current.score + 1000
  ).takeIf { this[it.location] != '#' },
  current.copy(
    direction = current.direction.turnRight(),
    score = current.score + 1000
  ).takeIf { this[it.location] != '#' },
  current.move().takeIf { this[it.location] != '#' }
)


data class ReindeerState(
  val location: Coordinate,
  val direction: FourDirectionFlipped,
  val score: Long,
  val path: Set<Coordinate> = setOf(location)
) : Comparable<ReindeerState> {
  val seenState = location to direction

  fun move() = copy(location = direction + location, path = path + location, score = score + 1)


  override fun compareTo(other: ReindeerState): Int {
    return when (val s = score.compareTo(other.score)) {
      0 -> manhattanDistance(location, end).compareTo(manhattanDistance(other.location, end))
      else -> s
    }
  }
}
