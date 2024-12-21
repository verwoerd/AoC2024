package day21.part2

import day21.part1.findComplexity
import day21.part1.readInput
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 21/12/2024
 */
fun day21Part2(input: BufferedReader): Any {
  val codes = input.readInput()
  return codes.sumOf { (code, value) ->
    code.findComplexity(depth = 25) * value
  }
}


// 41902944907567344 to high
