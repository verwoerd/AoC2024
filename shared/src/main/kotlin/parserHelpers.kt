@file:Suppress("unused")

import java.io.BufferedReader

fun Sequence<String>.extractLongSequence() = map { it.spaceSeperatedLongs() }

fun String.spaceSeperatedLongs(deliminator: String = " ") = split(deliminator).map { it.toLong() }
fun String.splitSeperatedInts(deliminator: String = " ") = split(deliminator).map { it.toInt() }

fun <T> BufferedReader.toSpecializedCoordinateMap(converter: (Char) -> T): Map<Coordinate, T> =
  lineSequence().toSpecializedCoordinateMap(converter)


fun <T> Sequence<String>.toSpecializedCoordinateMap(converter: (Char) -> T): Map<Coordinate, T> =
  flatMapIndexed { y, line ->
    line.mapIndexed { x, char -> Coordinate(x, y) to converter(char) }
  }.toMap()

fun BufferedReader.toCoordinateMap() = toSpecializedCoordinateMap { char -> char }

fun Sequence<String>.toCoordinateMap() = toSpecializedCoordinateMap { char -> char }

fun BufferedReader.rawMaze() = lineSequence().toList()
