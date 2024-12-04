package day04.part1

import rawMaze
import transposeGrid
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 04/12/2024
 */
fun day04Part1(input: BufferedReader): Any {
  val grid = input.rawMaze()
  var count = 0
  // searching left to right and right to left
  count += grid.countXmas()
  // transpose
  val transposed = grid.transposeGrid()
  count += transposed.countXmas()
  // create diagonal strings

  val lastX = grid.first().indices.last
  val lastY = grid.indices.last

  count += (grid.createDiagonalString(0, 0, 1, 1..lastX)  // diagonal down and right
      + grid.createDiagonalString(lastX, lastY, -1, 0 until lastX)  // diagonal up and left
      ).countXmas()
  return count
}

fun List<String>.countXmas() = sumOf { line ->
  line.windowed(size = 4, partialWindows = false, step = 1).count { it == "XMAS" } + line.reversed()
    .windowed(size = 4, partialWindows = false, step = 1).count { it == "XMAS" }
}

fun Sequence<Pair<Int, Int>>.createDiagonalString(grid: List<String>): String =
  takeWhile { it.first in grid.indices && it.second in grid.first().indices }.map { grid[it.first][it.second] }
    .joinToString(separator = "")

fun List<String>.createDiagonalString(xRoot: Int, yRoot: Int, direction: Int, xRange: IntRange): List<String> {
  val list = mutableListOf<String>()
  for (yStart in indices) {
    list += generateSequence(yStart to xRoot) { (y, x) -> y + direction to x + direction }.createDiagonalString(this)
  }
  for (xStart in (xRange)) {
    list += generateSequence(yRoot to xStart) { (y, x) -> y + direction to x + direction }.createDiagonalString(this)
  }
  return list
}
