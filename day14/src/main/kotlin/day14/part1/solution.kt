package day14.part1

import Coordinate
import java.io.BufferedReader
import java.lang.Math.floorMod

/**
 * @author verwoerd
 * @since 14/12/2024
 */
fun day14Part1(input: BufferedReader, width: Int = 101, height: Int = 103): Any {
  val robots = input.readRobots()
  val map = robots.map { it.location(100, width, height) }.groupBy { it }
  val q1 = map.filter { it.key.x in (0..<width / 2) && it.key.y in (0..<height / 2) }.map { it.value.size }.sum()
  val q2 = map.filter { it.key.x in (width / 2 + 1..width) && it.key.y in (0..<height / 2) }.map { it.value.size }.sum()
  val q3 =
    map.filter { it.key.x in (0..<width / 2) && it.key.y in (height / 2 + 1..height) }.map { it.value.size }.sum()
  val q4 =
    map.filter { it.key.x in (width / 2 + 1..width) && it.key.y in (height / 2 + 1..height) }.map { it.value.size }
      .sum()
  return q1 * q2 * q3 * q4
}

val regex = Regex("""p=(\d+),(\d+) v=(-?\d+),(-?\d+)""")

data class Robot(
  val startX: Int,
  val startY: Int,
  val velocityX: Int,
  val velocityY: Int,
) {
  fun location(steps: Int, width: Int, height: Int): Coordinate {
    return Coordinate(floorMod(startX + velocityX * steps, width), floorMod(startY + velocityY * steps, height))
  }

}

fun BufferedReader.readRobots() = lineSequence().map { regex.matchEntire(it)!!.destructured }
  .map { (x, y, vx, vy) -> Robot(x.toInt(), y.toInt(), vx.toInt(), vy.toInt()) }.toList()
