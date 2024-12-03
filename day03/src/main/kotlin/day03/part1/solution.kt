package day03.part1

import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 03/12/2024
 */
fun day03Part1(input: BufferedReader): Any {
  val regex = Regex("mul\\((\\d{1,3}),(\\d{1,3})\\)")
  val memory = input.readLines()
  return memory.flatMap { regex.findAll(it) }.sumOf {
    val (a, b) = it.destructured
    a.toBigInteger() * b.toBigInteger()
  }
}
