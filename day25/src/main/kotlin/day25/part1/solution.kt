package day25.part1

import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 25/12/2024
 */
fun day25Part1(input: BufferedReader): Any {
  val (locks, keys) = input.readInput()
  return locks.sumOf { lock ->
    keys.count { key ->
      lock.a + key.a <= 7 && lock.b + key.b <= 7 && lock.c + key.c <= 7 && lock.d + key.d <= 7 && lock.e + key.e <= 7
    }
  }
}


data class Quintet(val a: Int = 0, val b: Int = 0, val c: Int = 0, val d: Int = 0, val e: Int = 0)

fun BufferedReader.readInput(): Pair<MutableList<Quintet>, MutableList<Quintet>> {
  var current = Quintet()
  val keys = mutableListOf<Quintet>()
  val lock = mutableListOf<Quintet>()
  var isLock = false
  var row = 0
  lines().forEach { line ->
    if (line.isBlank()) {
      if (isLock) lock.add(current) else keys.add(current)
      current = Quintet()
      isLock = false
      row = 0
      return@forEach
    }
    if (row == 0) isLock = line == "#####"
    line.forEachIndexed { index, c ->
      if (c == '#') {
        when (index) {
          0 -> current = current.copy(a = current.a + 1)
          1 -> current = current.copy(b = current.b + 1)
          2 -> current = current.copy(c = current.c + 1)
          3 -> current = current.copy(d = current.d + 1)
          4 -> current = current.copy(e = current.e + 1)
        }
      }
    }
    row++
  }
  if (isLock) lock.add(current) else keys.add(current)
  return lock to keys
}
