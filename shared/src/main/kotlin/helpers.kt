@file:Suppress("unused")

import java.util.*
import kotlin.math.ceil

/**
 * @author verwoerd
 * @since 09-11-20
 */
fun Boolean.toInt() = when {
  this -> 1
  else -> 0
}

fun Boolean.toLong() = when {
  this -> 1L
  else -> 0L
}

infix fun Long.ceilDivision(b: Number) = ceil(this / b.toDouble()).toLong()
infix fun Int.ceilDivision(b: Number) = ceil(this / b.toDouble()).toInt()

fun <T> priorityQueueOf(vararg args: T): PriorityQueue<T> {
  val queue = PriorityQueue<T>()
  queue.addAll(args)
  return queue
}

fun <T> priorityQueueOf(comparator: Comparator<T>, vararg args: T): PriorityQueue<T> {
  val queue = PriorityQueue<T>(comparator)
  queue.addAll(args)
  return queue
}

fun Char.toIntValue() = code - '0'.code

fun <T> MutableList<T>.swap(index1: Int, index2: Int) {
  val toMove = get(index2)
  set(index2, get(index1))
  set(index1, toMove)
}
