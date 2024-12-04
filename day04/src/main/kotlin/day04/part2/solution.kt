package day04.part2

import rawMaze
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 04/12/2024
 */
fun day04Part2(input: BufferedReader): Any {
  val grid = input.rawMaze()
  return grid.windowed(size = 3, step = 1, partialWindows = false).sumOf { (l1, l2, l3) ->
    l2.withIndex().filter { (_, c) -> c == 'A' }.map { it.index }.filter { it != 0 && it != l2.indices.last }.count {
      (l1[it - 1] == 'M' && l1[it + 1] == 'M' && l3[it - 1] == 'S' && l3[it + 1] == 'S')
          || (l1[it - 1] == 'M' && l1[it + 1] == 'S' && l3[it - 1] == 'M' && l3[it + 1] == 'S')
          || (l1[it - 1] == 'S' && l1[it + 1] == 'M' && l3[it - 1] == 'S' && l3[it + 1] == 'M')
          || (l1[it - 1] == 'S' && l1[it + 1] == 'S' && l3[it - 1] == 'M' && l3[it + 1] == 'M')
    }
  }
}
