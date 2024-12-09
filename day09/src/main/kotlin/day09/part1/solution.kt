package day09.part1

import swap
import toIntValue
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 09/12/2024
 */
fun day09Part1(input: BufferedReader): Any {
  val line = input.readLine()
  val expanded = line.expandDisk().toMutableList()
  expanded.defragment()
  return expanded.calculateHash()
}

fun String.expandDisk() = fold(Triple(mutableListOf<Long>(), true, 0L)) { (current, file, id), c: Char ->
  val count = c.toIntValue()
  val toAdd = if (file) id else -id
  repeat(count) { current.add(toAdd) }
  Triple(current, !file, if (file) id + 1 else id)
}.first.dropLastWhile { it < 0 }

fun MutableList<Long>.defragment() {
  var backIndex = indices.last()
  var frontIndex = indices.first
  while (backIndex > frontIndex) {
    while (get(backIndex) < 0) backIndex--
    while (get(frontIndex) >= 0) frontIndex++
    if (backIndex > frontIndex) swap(frontIndex, backIndex)
  }
}

fun List<Long>.calculateHash() = foldIndexed(0L) { index, acc, value -> if (value < 0) acc else acc + value * index }

