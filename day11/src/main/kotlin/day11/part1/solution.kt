package day11.part1

import spaceSeperatedLongs
import java.io.BufferedReader
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.log10
import kotlin.math.pow

/**
 * @author verwoerd
 * @since 11/12/2024
 */
fun day11Part1(input: BufferedReader): Any {
  var stone = input.readLine().spaceSeperatedLongs(" ")
  repeat(25) {
    stone = stone.blink()
  }
  return stone.size
}

fun List<Long>.blink() = flatMap { it.blink() }

fun Long.blink() = when {
  this == 0L -> listOf(1L)
  floor(log10(this.toDouble())) % 2 == 1.0 -> {
    val factor = 10.0.pow(ceil(log10(this + 0.1)) / 2.0).toLong()
    listOf(this / factor, this % factor)
  }

  else -> listOf(this * 2024)
}

