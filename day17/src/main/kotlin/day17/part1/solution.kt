package day17.part1

import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 17/12/2024
 */
fun day17Part1(input: BufferedReader): Any {
  val debugger = input.readDebuggerState()
  debugger.execute()
  return debugger.output.joinToString(separator = ",")
}

data class Debugger(
  var a: Long,
  var b: Long,
  var c: Long,
  val program: List<Int>,
  val output: MutableList<Int> = mutableListOf(),
  var pc: Int = 0,
  var debug: Boolean = false,
  var stopAtOutput: Boolean = false
) {

  fun getComboOperand(literal: Int): Long = when (literal) {
    0, 1, 2, 3 -> literal.toLong()
    4 -> a
    5 -> b
    6 -> c
    else -> error("Invalid operand")
  }

  fun runCurrentInstruction() {
    val operand = program[pc]
    val literal = program[pc + 1]

    when (operand) {
      0 -> {
        val combo = getComboOperand(literal)
        val result = a / (1 shl combo.toInt())
        if (debug) println("[$pc]adv $literal($combo): $a/(2^$combo) -> $result")
        a = result
        pc += 2
      }

      1 -> {
        val result = b xor literal.toLong()
        if (debug) println("[$pc]bxl $literal: $b xor $literal -> $result")
        b = result
        pc += 2
      }

      2 -> {
        val combo = getComboOperand(literal)
        val result = combo % 8
        if (debug) println("[$pc]bst $literal($combo): $combo % 8 -> $result")
        b = result
        pc += 2
      }

      3 -> {
        val result = if (a == 0L) pc + 2 else literal
        if (debug) println("[$pc]jnz $literal: $a != 0 -> $result")
        pc = result
      }

      4 -> {
        val result = b xor c
        if (debug) println("[$pc]bxc $literal: $b xor $c -> $result")
        b = result
        pc += 2
      }

      5 -> {
        val combo = getComboOperand(literal)
        val result = combo % 8
        if (debug) println("[$pc]out $literal($combo): $combo % 8 -> $result")
        output += result.toInt()
        pc += 2
      }

      6 -> {
        val combo = getComboOperand(literal)
        val result = a / (1 shl combo.toInt())
        if (debug) println("[$pc]bdv $literal($combo): $a/(2^$combo) -> $result")
        b = result
        pc += 2
      }

      7 -> {
        val combo = getComboOperand(literal)
        val result = a / (1 shl combo.toInt())
        if (debug) println("[$pc]cdv $literal($combo): $a/(2^$combo) -> $result")
        c = result
        pc += 2
      }
    }
  }

  fun execute() {
    val outputSize = output.size
    while (pc + 1 < program.size) {
      runCurrentInstruction()
      if (stopAtOutput && outputSize != output.size) return
      if (debug) println(
        "[$pc]a: $a, b: $b, c: $c, output: ${output.joinToString(separator = ",")}"
      )
    }
  }
}

fun BufferedReader.readDebuggerState(): Debugger {
  val a = readLine().split(": ").last().toLong()
  val b = readLine().split(": ").last().toLong()
  val c = readLine().split(": ").last().toLong()
  readLine()
  val program = readLine().split(": ").last().split(",").map { it.toInt() }
  return Debugger(a, b, c, program)
}

