package day19.part1

import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 19/12/2024
 */
fun day19Part1(input: BufferedReader): Any {
  val (towels, patterns) = input.readTowels()
  return patterns.count { it.findCombinations(towels) > 0 }
}

val cache = mutableMapOf<String, Long>()
fun String.findCombinations(towels: List<String>): Long = cache.getOrPut(this) {
  when {
    this.isEmpty() -> 1
    else -> towels.filter { startsWith(it) }.sumOf { this.drop(it.length).findCombinations(towels) }
  }
}

fun BufferedReader.readTowels(): Pair<List<String>, List<String>> {
  val towels = readLine().split(", ")
  readLine()
  val patterns = readLines()
  return towels to patterns
}
