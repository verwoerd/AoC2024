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


/*
Optimized code is wrong for part 1 while it passes the example. Don't feel like debugging, this was the monster that
solved it.

package day04.part1

import rawMaze
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 04/12/2024
 */
fun day04Part1(input: BufferedReader): Any {
  val grid = input.rawMaze()
  var count = 0
  // searching left to right and right to left
  count += grid.sumOf { line ->
    val a = line.windowed(size=4, partialWindows = false, step = 1).count { it == "XMAS" }
    val b = line.reversed().windowed(size=4, partialWindows = false, step = 1).count { it == "XMAS" }
    println("$line: ltr: $a, rtl: $b")
    a + b
  }
  // transpose
//  println(count)
  val transposed = grid.first().indices.map { col -> grid.map { it[col] }.joinToString(separator = "") }
//  println(transposed)
  count += transposed.sumOf { line ->
    val a = line.windowed(size=4, partialWindows = false, step = 1).count { it == "XMAS" }
    val b = line.reversed().windowed(size=4, partialWindows = false, step = 1).count { it == "XMAS" }
    println("$line: utd: $a, dtu: $b")
    a+b
  }
  // create diagonal strings
  val list = mutableListOf<String>()
  val lastX = grid.first().indices.last
  // diagonal down and right
  for (yStart in grid.indices.reversed()) {
    list += generateSequence(yStart to 0) {(y,x) ->
      y+1 to x+1
    }.takeWhile { it.first in grid.indices && it.second in grid.first().indices }
      .map { grid[it.first][it.second] }.joinToString(separator = "")
  }
  for (xStart in (1..grid.first().indices.last)) {
    list += generateSequence(0 to xStart) {(y,x) ->
      y+1 to x+1
    }.takeWhile { it.first in grid.indices && it.second in grid.first().indices }
      .map { grid[it.first][it.second] }.joinToString(separator = "")
  }
  // diagonal down and left
  for (yStart in grid.indices) {
    list += generateSequence(yStart to lastX) {(y,x) ->
      y+1 to x-1
    }.takeWhile { it.first in grid.indices && it.second in grid.first().indices }
      .map { grid[it.first][it.second] }.joinToString(separator = "")
  }
  for (xStart in (0 until grid.first().indices.last)) {
    list += generateSequence(0 to xStart) {(y,x) ->
      y+1 to x-1
    }.takeWhile { it.first in grid.indices && it.second in grid.first().indices }
      .map { grid[it.first][it.second] }.joinToString(separator = "")
  }

  // diagonal up and left
  for (yStart in grid.indices) {
    list += generateSequence(yStart to grid.first().indices.last) {(y,x) ->
      y-1 to x-1
    }.takeWhile { it.first in grid.indices && it.second in grid.first().indices }
      .map { grid[it.first][it.second] }.joinToString(separator = "")
  }
  for (xStart in (grid.first().indices.last - 1 downTo 0)) {
    list += generateSequence(grid.indices.last to xStart) {(y,x) ->
      y-1 to x-1
    }.takeWhile { it.first in grid.indices && it.second in grid.first().indices }
      .map { grid[it.first][it.second] }.joinToString(separator = "")
  }
  // diagonal up and left
  for (yStart in grid.indices) {
    list += generateSequence(yStart to 0) {(y,x) ->
      y-1 to x+1
    }.takeWhile { it.first in grid.indices && it.second in grid.first().indices }
      .map { grid[it.first][it.second] }.joinToString(separator = "")
  }
  for (xStart in (1 .. lastX)) {
    list += generateSequence(grid.indices.last to xStart) {(y,x) ->
      y-1 to x+1
    }.takeWhile { it.first in grid.indices && it.second in grid.first().indices }
      .map { grid[it.first][it.second] }.joinToString(separator = "")
  }

//  println(list)
  count += list.sumOf { line ->
    val a = line.windowed(size=4, partialWindows = false, step = 1).count { it == "XMAS" }
    println("$line: diag: $a")
    a
  }
  // diagonals
//  for (yStart in grid.indices) {
//    count+= findDiagonal(xStart = 0, yStart = yStart, grid = grid, direction = 1)
//    count+= findDiagonal(xStart = grid.first().lastIndex, yStart = yStart, grid = grid, direction = -1)
//    println("y: $yStart -> $count")
//  }
//  for (xStart in grid.first().indices) {
//    count+= findDiagonal(xStart = xStart, yStart = 0, grid = grid, direction = 1)
//    count+= findDiagonal(xStart = xStart, yStart = grid.lastIndex, grid = grid, direction = -1)
//    println("x: $xStart -> $count")
//  }

  return count
}

fun findDiagonal(xStart: Int, yStart : Int, grid: List<String>, direction: Int): Int {
  var x = xStart
  var y = yStart
  var lastFound = '0'
  var count = 0
  while (x in grid.indices && y in grid.first().indices) {
    if (lastFound == '0' && grid[y][x] == 'X') {
      lastFound = 'X'
    } else if(lastFound == 'X' && grid[y][x] == 'M') {
      lastFound = 'M'
    } else if(lastFound == 'M' && grid[y][x] == 'A') {
      lastFound = 'A'
    } else if(lastFound == 'A' && grid[y][x] == 'S') {
      count++
      lastFound = '0'
    } else {
      lastFound = '0'
    }
    x+= direction
    y+= direction
  }
  return count
}

 */
