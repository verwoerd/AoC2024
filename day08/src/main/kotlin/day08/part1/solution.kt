package day08.part1

import Coordinate
import combinations
import toCoordinateMap
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 08/12/2024
 */
fun day08Part1(input: BufferedReader): Any {
  val map = input.toCoordinateMap()
  val antennas = map.filter { it.value != '.' }.map { it.key to it.value }.groupBy({ it.second }, { it.first })
  return antennas.generateAntiNodes(::generateAntiNodes).filter { it in map.keys }.size
}

fun Map<Char, List<Coordinate>>.generateAntiNodes(generator: (Coordinate, Coordinate, Coordinate) -> List<Coordinate>): Set<Coordinate> =
  keys.flatMap { antenna ->
    val coordinates = get(antenna)!!
    val combinations = combinations(2, coordinates)
    combinations.flatMap { (left, right) ->
      val diff = (left - right)
      generator(left, right, diff)
    }
  }.toSet()

fun generateAntiNodes(left: Coordinate, right: Coordinate, diff: Coordinate): List<Coordinate> =
  listOf(left + diff, right - diff)
