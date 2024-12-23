package day23.part2

import combinations
import day23.part1.readLanMap
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 23/12/2024
 */
fun day23Part2(input: BufferedReader): Any {
  val lanMap = input.readLanMap()
  val seen = mutableSetOf<Set<String>>()
  val seen2 = mutableSetOf<Set<String>>()
  var best = 3
  val a = lanMap.keys.flatMap { current ->
    val currentNodes = lanMap[current]!! + setOf(current)
    if (!seen2.add(currentNodes)) return@flatMap emptyList<Set<String>>()
    (best..currentNodes.indices.last).flatMap { size ->
      val combinations = combinations(size, currentNodes)
      val sets = combinations.map { it.toSet() }.filter { seen.add(it) }.filter { graph ->
        graph.all { node -> lanMap[node]!!.containsAll(graph.filter { it != node }) }
      }
      if (sets.isNotEmpty() && size > best) best = size
      sets
    }
  }
  return a.maxByOrNull { it.size }!!.sorted().joinToString(",")
}
