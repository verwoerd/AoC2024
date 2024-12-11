package day11.part2

import day11.part1.blink
import spaceSeperatedLongs
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 11/12/2024
 */
fun day11Part2(input: BufferedReader): Any {
  val stone = input.readLine().spaceSeperatedLongs(" ")
  var freq = stone.groupBy { it }.mapValues { it.value.size.toLong() }
  repeat(75) { _ ->
    val nextFreq = mutableMapOf<Long, Long>().withDefault { 0L }
    freq.map { (key, value) ->
      val result = key.blink()
      result.map { nextFreq[it] = nextFreq.getValue(it) + value }
    }
    freq = nextFreq
  }
  return freq.values.sum()
}
