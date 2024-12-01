package day01.part1

import java.io.BufferedReader
import kotlin.math.abs

/**
 * @author verwoerd
 * @since 30/11/2024
 */
fun day01Part1(input: BufferedReader): Any {
  val (leftList, rightList) = input.parseList()
  return leftList.zip(rightList).sumOf { abs(it.first - it.second) }
}

val regex = Regex("(\\d+)\\s+(\\d+)")
fun BufferedReader.parseList(): Pair<List<Long>, List<Long>> {
  val lists =
    lineSequence().map { regex.matchEntire(it)!!.destructured }.map { (a, b) -> a.toLong() to b.toLong() }.toList()
  return lists.map { it.first }.sorted() to lists.map { it.second }.sorted()
}


