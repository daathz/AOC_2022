object Day05 : DayXX() {

    private data class Move(val size: Int, val from: Int, val to: Int)

    override fun part1() {
        val (stacks, moves) = createStacksAndMoves(readInput("day05"))

        doRearrangements(stacks, moves)
    }

    override fun part2() {
        val (stacks, moves) = createStacksAndMoves(readInput("day05"))

        doRearrangements(stacks, moves, true)
    }

    private fun createStacksAndMoves(inputs: List<String>): Pair<ArrayList<ArrayDeque<Char>>, List<Move>> {
        val crates = inputs.takeWhile { it != "" }.reversed().drop(1)
            .map { replaceEverySecondCharacter(it, true) } // removes brackets
            .map { replaceEverySecondCharacter(it, false) } // removes whitespaces
        val rearrangements = inputs.takeLastWhile { it != "" }

        val stacks = ArrayList<ArrayDeque<Char>>()
        for (column in 0 until crates[0].length) {
            stacks.add(ArrayDeque())
        }

        crates.forEach {
            for ((index, column) in it.withIndex()) {
                if (column != ' ') {
                    stacks[index].add(column)
                }
            }
        }

        val moves = rearrangements.map {
            val tokens = it.split(" ").map(Integer::parseInt)
            Move(tokens[1], tokens[3], tokens[5])
        }

        return Pair(stacks, moves)
    }

    private fun doRearrangements(stacks: ArrayList<ArrayDeque<Char>>, moves: List<Move>, batch: Boolean = false) {
        moves.forEach {
            val temp = ArrayDeque<Char>()
            for (i in 0 until it.size) {
                val last = stacks[it.from - 1].last()
                stacks[it.from - 1].removeLast()
                temp.add(last)
            }
            stacks[it.to - 1].addAll(
                if (batch) temp.reversed() else temp
            )
        }

        stacks.forEach { print(it.last()) }
        println()
    }

    private fun replaceEverySecondCharacter(input: String, removeEvens: Boolean): String {
        val sb = StringBuilder()
        for ((index, char) in input.withIndex()) {
            if (removeEvens && index % 2 != 0) {
                sb.append(char)
            } else if (!removeEvens && index % 2 == 0) {
                sb.append(char)
            }
        }
        return sb.toString()
    }
}

fun main() {
    Day05.solve()
}
