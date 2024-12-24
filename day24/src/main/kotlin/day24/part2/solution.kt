package day24.part2

import day24.part1.Formula
import day24.part1.Operation
import day24.part1.parseInput
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 24/12/2024
 */
fun day24Part2(input: BufferedReader): Any {
  val (_, formulas) = input.parseInput()
  val formulaLookup = formulas.entries.flatMap { (key, value) ->
    listOf(value to key, value.copy(left = value.right, right = value.left) to key)
  }.toMap()
  val swapped = mutableListOf<String>()
  var carry = ""
  var cright: String?
  var nextCarry = ""
  var sum: String
  fun swap(a: String, b: String): Pair<String, String> {
    swapped.add(a)
    swapped.add(b)
    return a to b
  }

  // reconstructing the full adder to map the sub formula names (https://www.geeksforgeeks.org/full-adder-in-digital-logic/)
  // every digit consists of the following formula
  // a xor b = s1
  // s1 xor carry = sum
  // s1 and carry = cleft
  // a and b  = clright
  // cleft or cright = carry
  // carry is used every next iteration
  // it turns out, the swaps are only in the full adder, and only when sub the subformula contains the z-output
  (0..<45).forEach { num ->
    val suffix = num.toString().padStart(2, '0')
    var s1 = formulaLookup[Formula("x$suffix", "y$suffix", Operation.XOR)] ?: error("No s1 for $num")
    var cleft = formulaLookup[Formula("x$suffix", "y$suffix", Operation.AND)] ?: error("No cleft for $num")
    if (carry.isNotBlank()) {
      cright = formulaLookup[Formula(carry, s1, Operation.AND)]
      if (cright == null) {
        swap(s1, cleft).also { (a, b) ->
          cleft = a
          s1 = b
        }
        cright = formulaLookup[Formula(carry, s1, Operation.AND)] ?: error("No cright for $num")
      }
      sum = formulaLookup[Formula(s1, carry, Operation.XOR)] ?: error("No sum for $num")

      if (s1.startsWith("z")) {
        // s1 should feed in sum, so swap around
        swap(s1, sum).also { (a, b) ->
          sum = a
          s1 = b
        }
      }
      if (cleft.startsWith("z")) {
        swap(cleft, sum).also { (a, b) ->
          sum = a
          cleft = b
        }
      }
      if (cright!!.startsWith("z")) {
        swap(cright!!, sum).also { (a, b) ->
          sum = a
          cright = b
        }
      }
      nextCarry = formulaLookup[Formula(cright!!, cleft, Operation.OR)] ?: error("No nextCarry for $num")
      if (nextCarry.startsWith("z") && nextCarry != "z45") {
        swap(nextCarry, sum).also { (a, b) ->
          sum = a
          nextCarry = b
        }
      }
    }
    carry = if (carry.isBlank()) cleft else nextCarry
  }
  return swapped.sorted().joinToString(",")
}
