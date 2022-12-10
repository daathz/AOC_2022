import kotlin.math.abs

object Day10 : DayXX() {

    private enum class CommandType { NOOP, ADDX }

    private data class Command(val type: CommandType, val value: Int = 0)


    override fun part1() {
        val cycles = createCycles(getCommands(readInput("day10")))

        val sum = cycles.withIndex()
            .filter {
                val myIndex = it.index + 1
                myIndex == 20 || (myIndex - 20) % 40 == 0
            }.sumOf { (it.index + 1) * it.value }

        println(sum)
    }

    override fun part2() {
        val cycles = createCycles(getCommands(readInput("day10")))

        cycles.chunked(40) {
                it.mapIndexed { idx, cycle ->
                    when (abs((idx - cycle))) {
                        in 0..1 -> '█'
                        else -> ' '
                    }
                }
                    .fold("") { row, c -> row + c }
            }
            .forEach(::println)
    }

    private fun getCommands(input: List<String>): List<Command> = input
        .map { it.split(" ") }
        .map {
            when (it[0]) {
                "noop" -> Command(CommandType.NOOP)
                else -> Command(CommandType.ADDX, it[1].toInt())
            }
        }

    private fun createCycles(commands: List<Command>): List<Int> {
        val cycles = mutableListOf<Int>()
        var xValue = 1

        commands.forEach { command ->
            when (command.type) {
                CommandType.NOOP -> cycles.add(xValue)
                CommandType.ADDX -> {
                    cycles.add(xValue)
                    cycles.add(xValue)

                    xValue += command.value
                }
            }
        }

        return cycles
    }
}

fun main() {
    Day10.solve()
}