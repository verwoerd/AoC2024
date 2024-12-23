package day23.part1

import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 23/12/2024
 */
fun day23Part1(input: BufferedReader): Any {
  val lanMap = input.readLanMap()
  val result = lanMap.keys.filter { it.startsWith("t") }
    .flatMap { base ->
      lanMap[base]!!.flatMap { other ->
        lanMap[other]!!.filter { it != base }
          .filter { it in lanMap[base]!! }
          .map { setOf(base, other, it) }
      }
    }.distinct()
  return result.size
}

fun BufferedReader.readLanMap(): Map<String, Set<String>> {
  val map = mutableMapOf<String, MutableSet<String>>().withDefault { mutableSetOf() }
  lineSequence().forEach { line ->
    val (l, r) = line.split("-")
    map[l] = map.getValue(l).also { it.add(r) }
    map[r] = map.getValue(r).also { it.add(l) }
  }
  return map
}
