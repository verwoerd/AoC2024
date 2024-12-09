package day09.part2

import day09.part1.calculateHash
import swap
import toIntValue
import java.io.BufferedReader
import java.util.*

/**
 * @author verwoerd
 * @since 09/12/2024
 */
fun day09Part2(input: BufferedReader): Any {
  val line = input.readLine()
  val (raw, blocks) = line.expandDisk()
  raw.blockDefragment(blocks)
  return raw.calculateHash()
}

fun MutableList<Long>.blockDefragment(blocks: List<DiskPart>) {
  val freeSpace = mutableMapOf<Int, SortedSet<DiskPart>>()
  (1..9).forEach { space ->
    freeSpace[space] =
      blocks.filter { it.free }.filter { it.size >= space }.sortedBy { it.index }.toSortedSet(compareBy { it.index })
  }
  val movable = blocks.filter { !it.free }.sortedBy { -it.index }
  movable.forEach { toMove ->
    val freeSpaceBlock = freeSpace[toMove.size]!!.firstOrNull { it.index < toMove.index }
    if (freeSpaceBlock == null) return@forEach
    (1..freeSpaceBlock.size).forEach { freeSpace[it]?.remove(freeSpaceBlock) }
    repeat(toMove.size) { swap(toMove.index + it, freeSpaceBlock.index + it) }
    if (freeSpaceBlock.size > toMove.size) {
      val newFreeSpace = DiskPart(freeSpaceBlock.size - toMove.size, true, freeSpaceBlock.index + toMove.size)
      (1..newFreeSpace.size).forEach {
        freeSpace[it]!!.add(newFreeSpace)
      }
    }
  }
}

data class DiskPart(
  val size: Int, val free: Boolean, val index: Int
)

fun String.expandDisk() =
  fold(Triple(mutableListOf<Long>() to mutableListOf<DiskPart>(), true, 0L)) { (current, file, id), c: Char ->
    val (raw, parts) = current
    val size = c.toIntValue()
    val toAdd = if (file) id else -id
    val index = raw.size
    repeat(size) { raw.add(toAdd) }
    parts.add(DiskPart(size, !file, index))
    Triple(raw to parts, !file, if (file) id + 1 else id)
  }.first
