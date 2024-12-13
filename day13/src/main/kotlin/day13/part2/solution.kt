package day13.part2

import day13.part1.parseInput
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 13/12/2024
 */
fun day13Part2(input: BufferedReader): Any {
  val machines =
    input.parseInput().map { it.copy(priceX = 10000000000000 + it.priceX, priceY = 10000000000000 + it.priceY) }
  return machines.sumOf { it.solve() }
}
