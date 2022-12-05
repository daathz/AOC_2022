import java.util.*
import kotlin.collections.ArrayList

object Day05 : DayXX() {

    private data class Move(val size: Int, val from: Int, val to: Int)

    override fun part1() {
        val inputs = readInput("day05")

        val crates = inputs.takeWhile { it != "" }.reversed()
        val rearrangements = inputs.takeLastWhile { it != "" }

        val stacks = ArrayList<ArrayDeque<Char>>()
        for (column in 0 until crates[0].length) {
            stacks.add(ArrayDeque())
        }

        for (line in crates) {
            for ((index, column) in line.withIndex()) {
                if (column != ' ') {
                    stacks[index].add(column)
                }
            }
        }

        val moves = rearrangements.map {
            val tokens = it.split(" ")
            Move(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[3]), Integer.parseInt(tokens[5]))
        }

        for (move in moves) {
            for (i in 0 until move.size) {
                val last = stacks[move.from - 1].last()
                stacks[move.from - 1].removeLast()
                stacks[move.to - 1].add(last)
            }
        }

        for (stack in stacks) {
            print(stack.last)
        }
        println()
    }

    override fun part2() {

    }
}

fun main() {
    Day05.solve()
}