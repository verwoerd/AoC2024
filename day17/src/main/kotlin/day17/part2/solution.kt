package day17.part2

import day17.part1.readDebuggerState
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 17/12/2024
 */
fun day17Part2(input: BufferedReader): Any {
  val initial = input.readDebuggerState()
  return initial.program.findA(0, initial.program.size - 1)
}

fun List<Int>.findA(last: Long, index: Int): Long {
  if (index < 0) return last
  val aBase = last * 8
  (aBase..aBase + 7).forEach { a ->
    val output = calculateForA(a)
    if (this[index] == output) {
      val final = findA(a, index - 1)
      if (final > 0) return final
    }
  }
  return -1
}

fun calculateForA(a: Long): Int {
  var b = a % 8
  b = b xor 3
  val c = a / (1 shl b.toInt())
  b = b xor c
  b = b xor 5
  return (b % 8).toInt()
}
