package day01.part2

import day01.part1.parseList
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 30/11/2024
 */
fun day01Part2(input: BufferedReader): Any {
  val (leftList, rightList) = input.parseList()
  val frequencyMap = rightList.groupBy { it }.mapValues { it.value.size }
  return leftList.sumOf { it * (frequencyMap[it] ?: 0) }
}
