package day19.part2

import day19.part1.findCombinations
import day19.part1.readTowels
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 19/12/2024
 */
fun day19Part2(input: BufferedReader): Any {
  val (towels, patterns) = input.readTowels()
  return patterns.sumOf { it.findCombinations(towels) }
}
