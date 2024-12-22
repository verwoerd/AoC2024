package day22.part2

import day22.part1.calculateSecretPrice
import day22.part1.readStartPrices
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 22/12/2024
 */
fun day22Part2(input: BufferedReader): Any {
  val prices = input.readStartPrices()

  val sequences = prices.map { secret ->
    generateSequence(secret) { it.calculateSecretPrice() }.take(2001)
      .zipWithNext { a, b -> (b % 10).toInt() to ((b % 10) - (a % 10)).toInt() }.toList()
  }

  data class Partition(val a: Int, val b: Int, val c: Int, val d: Int)

  val partitionMaps = sequences.map { sequence ->
    val seen = mutableSetOf<Partition>()
    sequence.windowed(size = 4, step = 1, partialWindows = false)
      .map { (a, b, c, d) -> Partition(a.second, b.second, c.second, d.second) to d.first }
      .filter { (partition, _) -> seen.add(partition) }
      .toMap().withDefault { 0 }
  }

  return partitionMaps.asSequence().flatMap { it.keys }.distinct().maxOf { partition ->
    partitionMaps.sumOf { it.getValue(partition) }
  }
}
