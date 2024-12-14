package day14.part2

import day14.part1.readRobots
import printMap
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 14/12/2024
 */
fun day14Part2(input: BufferedReader, width: Int = 101, height: Int = 103): Any {
  val robots = input.readRobots()
  repeat(width * width * height * height) { time ->
    val map = robots.map { it.location(time, width, height) }.groupBy { it }
    if (map.size == robots.size) {
      map.printMap { x, _ -> x?.let { "*" } ?: " " }
      return time
    }
  }
  error("not found")
}
