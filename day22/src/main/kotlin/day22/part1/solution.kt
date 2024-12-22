package day22.part1

import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 22/12/2024
 */
fun day22Part1(input: BufferedReader): Any {
  val prices = input.readStartPrices()
  return prices.sumOf { secret ->
    generateSequence(secret) { it.calculateSecretPrice() }.take(2001).last()
  }
}

val cache = mutableMapOf<Long, Long>()
fun Long.calculateSecretPrice() = cache.getOrPut(this) {
  var secret = this
  var next = this shl 6
  secret = next.mix(secret).prune()
  next = secret shr 5
  secret = next.mix(secret).prune()
  next = secret shl 11
  next.mix(secret).prune()
}

fun Long.mix(secret: Long) = (this xor secret)
fun Long.prune() = this % 16777216

fun BufferedReader.readStartPrices() = lineSequence().map { it.toLong() }.toList()
