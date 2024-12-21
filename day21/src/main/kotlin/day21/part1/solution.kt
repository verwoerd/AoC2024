package day21.part1

import Coordinate
import FourDirections.*
import origin
import priorityQueueOf
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 21/12/2024
 */
fun day21Part1(input: BufferedReader): Any {
  val codes = input.readInput()
  return codes.sumOf { (code, value) ->
    code.findComplexity(depth = 2) * value
  }
}

val numericKeyboard = mapOf(
  'A' to origin,
  '0' to LEFT + origin,
  '1' to UP + LEFT * 2 + origin,
  '2' to UP + LEFT + origin,
  '3' to UP + origin,
  '4' to UP * 2 + LEFT * 2 + origin,
  '5' to UP * 2 + LEFT * 1 + origin,
  '6' to UP * 2 + origin,
  '7' to UP * 3 + LEFT * 2 + origin,
  '8' to UP * 3 + LEFT * 1 + origin,
  '9' to UP * 3 + origin,
)

val arrowKeyboard = mapOf(
  'A' to origin,
  '^' to LEFT + origin,
  '<' to DOWN + LEFT * 2 + origin,
  'v' to DOWN + LEFT + origin,
  '>' to DOWN + origin,
)
val forbiddenSquare = LEFT * 2 + origin

val directions = listOf(UP to '^', DOWN to 'v', LEFT to '<', RIGHT to '>')

fun Map<Char, Coordinate>.createPath(from: Coordinate, to: Coordinate): List<List<Char>> {
  val paths = mutableListOf<List<Char>>()
  var bestSize = Int.MAX_VALUE
  var best = 0
  val queue = priorityQueueOf(comparator = Comparator.comparing { it.first.size }, listOf<Char>() to from)
  while (queue.isNotEmpty() && best <= bestSize) {
    val (path, location) = queue.poll()
    best = path.size
    if (location == forbiddenSquare) continue
    if (location == to) {
      bestSize = path.size
      paths.add(path + listOf('A'))
    }
    directions.map { (direction, char) -> direction + location to char }
      .filter { (location, _) -> location in values }
      .map { (location, char) -> path + char to location }
      .toCollection(queue)
  }
  return paths
}

val numericPaths = numericKeyboard.entries
  .flatMap { from ->
    numericKeyboard.entries.map { to ->
      (from.key to to.key) to numericKeyboard.createPath(from.value, to.value)
    }
  }.toMap()

val arrowPaths = arrowKeyboard.entries
  .flatMap { from ->
    arrowKeyboard.entries.map { to ->
      (from.key to to.key) to arrowKeyboard.createPath(from.value, to.value)
    }
  }.toMap()

fun List<Char>.findComplexity(depth: Int): Long {
  return fold('A' to 0L) { acc, char ->
    val paths = numericPaths[acc.first to char]!!
    char to (acc.second + paths.minOf { it.calculateComplexityArrows(depth) })
  }.second
}

val cache = mutableMapOf<Pair<List<Char>, Int>, Long>()
val subCache = mutableMapOf<Pair<Pair<Char, Char>, Int>, Long>()
fun List<Char>.calculateComplexityArrows(depth: Int): Long = cache.getOrPut(this to depth) {
  if (depth == 0) return size.toLong()
  return fold('A' to 0L) { acc, char ->
    char to acc.second + subCache.getOrPut(acc.first to char to depth) {
      val paths = arrowPaths[acc.first to char]!!
      paths.minOf { it.calculateComplexityArrows(depth - 1) }
    }
  }.second
}

fun BufferedReader.readInput() = readLines().map { it.toList() to it.dropLast(1).toLong() }
