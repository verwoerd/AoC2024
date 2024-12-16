package day16.part2

import Coordinate
import FourDirectionFlipped
import day16.part1.end
import day16.part1.readInput
import day16.part1.validMoves
import java.io.BufferedReader
import kotlin.math.min

/**
 * @author verwoerd
 * @since 16/12/2024
 */
fun day16Part2(input: BufferedReader): Any {
  val (maze, queue) = input.readInput()
  val seen = mutableMapOf<Pair<Coordinate, FourDirectionFlipped>, Long>().withDefault { Long.MAX_VALUE }
  var best = Long.MAX_VALUE
  val locations = mutableSetOf<Coordinate>()
  while (queue.isNotEmpty()) {
    val current = queue.poll()
    if (current.score > seen.getValue(current.seenState)) continue
    seen[current.seenState] = min(seen.getValue(current.seenState), current.score)
    if (current.score > best) continue
    if (current.location == end) {
      best = current.score
      locations.addAll(current.path)
      continue
    }
    maze.validMoves(current).toCollection(queue)
  }
  return locations.size + 1
}

