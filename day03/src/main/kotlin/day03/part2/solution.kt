package day03.part2

import java.io.BufferedReader
import java.math.BigInteger

/**
 * @author verwoerd
 * @since 03/12/2024
 */
fun day03Part2(input: BufferedReader): Any {
  val regex = Regex("(mul)\\((\\d{1,3}),(\\d{1,3})\\)|(do)\\(\\)|(don)\'t\\(\\)")
  val memory = input.readLines()
  return memory.flatMap { regex.findAll(it) }.fold(BigInteger.ZERO to true) { (sum, state), current ->
    val (mul, a, b, does, doNot) = current.destructured
    when {
      does == "do" -> sum to true
      doNot == "don" -> sum to false
      mul == "mul" -> if (state) {
        sum + a.toBigInteger() * b.toBigInteger() to true
      } else sum to false

      else -> error("wrong stuff: ${current.destructured.toList()}")
    }
  }.first
}
