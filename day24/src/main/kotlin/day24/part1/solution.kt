package day24.part1

import toLong
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 24/12/2024
 */
fun day24Part1(input: BufferedReader): Any {
  val (assignments, formulas) = input.parseInput()
  val current = assignments.toMutableMap()

  fun getValue(key: String): Boolean = current.getOrPut(key) {
    if (key in assignments) assignments[key]!!
    val formula = formulas.getValue(key)
    formula.operation.doOperation(getValue(formula.left), getValue(formula.right))
  }

  val zValues = formulas.filter { it.key.startsWith("z") }
    .map { it.key to getValue(it.key) }
    .map { (key, value) ->
      val index = key.drop(1).toInt()
      value.toLong() shl index
    }.fold(0L) { acc, value -> acc or value }
  return zValues
}


val REGEX = Regex("""(.+): (\d)""")
val REGEX2 = Regex("""(.+) (AND|XOR|OR) (.+) -> (.+)""")

enum class Operation {
  AND, OR, XOR;

  fun doOperation(left: Boolean, right: Boolean): Boolean {
    return when (this) {
      AND -> left && right
      OR -> left || right
      XOR -> left xor right
    }
  }

}

data class Formula(val left: String, val right: String, val operation: Operation)

fun BufferedReader.parseInput(): Pair<Map<String, Boolean>, Map<String, Formula>> {
  val lines = readLines()
  val assignments = lines.takeWhile { it.isNotBlank() }
    .associate { line ->
      val (l, r) = REGEX.find(line)!!.destructured
      l to (r.toInt() == 1)
    }

  val formulas = lines.dropWhile { it.isNotBlank() }.drop(1)
    .associate {
      val (l1, op, l2, r, _) = REGEX2.find(it)!!.destructured
      r to Formula(l1, l2, Operation.valueOf(op.uppercase()))
    }
  return assignments to formulas
}
